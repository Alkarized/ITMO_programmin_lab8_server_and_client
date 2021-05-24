package utils;

import collection.CollectionManager;
import collection.Receiver;
import server.Connection;

public class ProgramStarter {
    private final Receiver receiver;
    private final Connection connection;
    private final CollectionManager collectionManager;

    public ProgramStarter(int port) {
        connection = new Connection(port);
        collectionManager = new CollectionManager();
        receiver = new Receiver(connection, collectionManager);
    }

    public void startProgram() {
        connection.start();
    }

}
