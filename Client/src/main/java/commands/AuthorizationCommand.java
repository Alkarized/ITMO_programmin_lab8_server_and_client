package commands;

import client.Receiver;
import utils.SerializableAnswerToClient;

import java.io.Serializable;
import java.util.Scanner;

public class AuthorizationCommand extends Command implements Serializable {
    private static final long serialVersionUID = 72;


    public AuthorizationCommand(Receiver receiver) {
        super(receiver);
    }

    public AuthorizationCommand() {
    }

    @Override
    public SerializableAnswerToClient execute(String[] args) {
        return null;
    }

    @Override
    public SerializableAnswerToClient execute(String[] args, Scanner scanner) {
        return null;

    }
}
