package server;

import collection.Receiver;
import message.MessageColor;
import message.Messages;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Connection {
    private InetSocketAddress inetSocketAddress;
    private ServerSocketChannel serverSocketChannel;
    private Selector selector;
    private Receiver receiver;
    private ExecutorService cachedPool;
    private ExecutorService fixedPool;
    private ReadWriteLock lock;

    public Connection(int port) {
        this.inetSocketAddress = new InetSocketAddress(port);
        cachedPool = Executors.newCachedThreadPool();
        fixedPool = Executors.newFixedThreadPool(10);
        lock = new ReentrantReadWriteLock();
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    public void start() throws CancelledKeyException {
        try {
            connectServer();

        } catch (Exception e) {
            Messages.normalMessageOutput(e.toString(), MessageColor.ANSI_RED);
        }

        while (true) {
            try {
                selector.select();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();

            while (iterator.hasNext()) {
                SelectionKey selectedKey = iterator.next();
                iterator.remove();

                if (selectedKey.isValid()) {
                    if (selectedKey.isAcceptable()) {
                        acceptClient(selectedKey);
                        continue;
                    }

                    if (selectedKey.isReadable()) {
                        selectedKey.interestOps(SelectionKey.OP_CONNECT);
                        cachedPool.submit(() -> {
                            Messages.getLogger().info("start cachedPool");
                            lock.readLock().lock();
                            Object object = readObjectFromClient(selectedKey);
                            if (object != null) {
                                fixedPool.submit(() -> {
                                    Messages.getLogger().info("start fixedPool");
                                    lock.writeLock().lock();
                                    Messages.normalMessageOutput("Read info from client", MessageColor.ANSI_CYAN);
                                    selectedKey.attach(new ObjectParser().parseObjectToByteBuffer(object, receiver));
                                    selectedKey.interestOps(SelectionKey.OP_WRITE);
                                    Messages.getLogger().info("end fixedPool");
                                    lock.writeLock().unlock();
                                });

                                Messages.getLogger().info("end cachedPool");

                            } else {
                                Messages.normalMessageOutput("Client just died", MessageColor.ANSI_RED);
                            }
                            lock.readLock().unlock();
                        });

                        continue;
                    }

                    if (selectedKey.isWritable()) {
                        selectedKey.interestOps(SelectionKey.OP_CONNECT);
                        Messages.getLogger().info("sit in writing");

                        new Thread(() -> {
                            Messages.getLogger().info("start normal thread");
                            //lock.writeLock().lock();
                            writeAns(selectedKey);
                            //lock.writeLock().unlock();
                            Messages.getLogger().info("end normal thread");
                        }).start();
                    }
                }
            }

        }


    }

    private void readFromConsole(ByteBuffer byteBuffer) throws IOException {

    }

    private void connectServer() {
        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.bind(inetSocketAddress);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            Messages.normalMessageOutput("Start and open server", MessageColor.ANSI_GREEN);
        } catch (IOException e) {
            Messages.normalMessageOutput(e.toString(), MessageColor.ANSI_RED);

        }
    }

    private void acceptClient(SelectionKey selectionKey) {
        ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
        try {
            SocketChannel client = server.accept();

            client.configureBlocking(false);
            client.register(selector, SelectionKey.OP_READ);
            Messages.normalMessageOutput("Accept new Client" + client.getRemoteAddress(), MessageColor.ANSI_BLUE);
        } catch (IOException e) {
            Messages.normalMessageOutput(e.toString(), MessageColor.ANSI_RED);

        }
        Messages.normalMessageOutput("Connected", MessageColor.ANSI_GREEN);
    }

    private Object readObjectFromClient(SelectionKey selectionKey) {

        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(2048);
        Object request = null;
        ObjectInputStream objectInputStream;
        while (true) {
            try {
                socketChannel.read(byteBuffer);
                objectInputStream = new ObjectInputStream(new ByteArrayInputStream(byteBuffer.array()));
                request = objectInputStream.readObject();
                break;
            } catch (StreamCorruptedException | ClassNotFoundException ignored) {
            } catch (IOException ioException) {
                try {
                    socketChannel.close();
                } catch (IOException ignored) {
                }
                break;
            }
        }
        //System.out.println("end reading");
        return request;
    }

    public void writeAns(SelectionKey selectionKey) {
        SocketChannel client = null;
        try {
            Messages.getLogger().info("Начало записи");
            client = (SocketChannel) selectionKey.channel();
            ByteBuffer ans = (ByteBuffer) selectionKey.attachment();

            ans.flip();
            client.write(ans);

            Messages.getLogger().info("Команда выполнена");
            Messages.normalMessageOutput("Written answer for client", MessageColor.ANSI_GREEN);

            selectionKey.interestOps(SelectionKey.OP_READ);
        } catch (ClosedChannelException e) {
            Messages.normalMessageOutput("Client disconnected from the server", MessageColor.ANSI_RED);
            try {
                client.close();
            } catch (IOException ignored) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void endConnection() {
        try {
            serverSocketChannel.close();
            selector.close();
        } catch (Exception e) {
            Messages.normalMessageOutput(e.toString(), MessageColor.ANSI_RED);
        }


    }

}
