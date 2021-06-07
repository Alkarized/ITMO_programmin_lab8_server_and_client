package client;

import commands.serializable_commands.SerializableCommandStandard;
import message.MessageColor;
import message.Messages;
import utils.SerializableAnswerToClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

public class Connection {
    private Socket socket;
    private final String host;
    private final int port;
    private boolean isConnected;

    public Connection(String host, int port) {
        this.host = host;
        this.port = port;
        isConnected = false;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public Object getAnsFromServer() throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        return objectInputStream.readObject();

    }

    public SerializableAnswerToClient getStringAnsFromServer() throws IOException, ClassNotFoundException {
        return (SerializableAnswerToClient) getAnsFromServer();
    }

    public boolean sendSerializableCommand(SerializableCommandStandard serializableCommandStandard) throws IOException {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(serializableCommandStandard);
            objectOutputStream.flush();
            Messages.normalMessageOutput("Отправка данных на сервер!", MessageColor.ANSI_CYAN);
            return true;
        } catch (SocketException e) {
            socket.close();
            isConnected = false;
            Messages.normalMessageOutput("В данный момент сервер недоступен, попытаемся еще раз!", MessageColor.ANSI_RED);
            //startConnection(host, port, serializableCommandStandard);
            return false;
        }
    }

    public void endConnection() throws IOException {
        socket.close();
    }

    /*public boolean startConnection(String host, int port, SerializableCommandStandard serializableCommandStandard) {
        while (true) {
            try {
                socket = new Socket(InetAddress.getByName(host), port);
                System.out.println("Соединение открыто - " + socket.getInetAddress().getHostAddress() + ":" + socket.getPort());
                socket.setSoTimeout(1000 * 20);
                if (serializableCommandStandard != null) {
                    if (sendSerializableCommand(serializableCommandStandard)) {
                        SerializableAnswerToClient ans = getStringAnsFromServer();
                        Messages.normalMessageOutput(ans.getAns(), ans.getColor());
                        return ans.getBool();
                    }
                }
                break;
            } catch (IOException | ClassNotFoundException e) {
                Messages.normalMessageOutput("Нет возможности подключиться, попробуем еще раз!", MessageColor.ANSI_RED);
            }
        }
        return false;
    }*/

    public boolean tryConnect(){
        if (!isConnected){
            try {
                socket = new Socket(InetAddress.getByName(host), port);
                System.out.println("Соединение открыто - " + socket.getInetAddress().getHostAddress() + ":" + socket.getPort());
                socket.setSoTimeout(10*1000);
                isConnected = true;
                return true;
            } catch (IOException e){
                return false;
            }
        }
        return true;
    }

}
