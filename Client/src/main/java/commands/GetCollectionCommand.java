package commands;

import client.Receiver;
import fields.Flat;
import fields.User;
import utils.SerializableAnswerToClient;

import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;

public class GetCollectionCommand extends Command implements Serializable{
    private static final long serialVersionUID = 541;

    public GetCollectionCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public SerializableAnswerToClient execute(String[] args) {
        try {
            return receiver.getAllCollection();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public SerializableAnswerToClient execute(String[] args, Scanner scanner) {
        return execute(args);
    }
}
