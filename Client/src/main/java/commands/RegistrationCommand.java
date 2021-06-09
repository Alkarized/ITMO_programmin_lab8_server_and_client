package commands;

import client.Receiver;
import utils.SerializableAnswerToClient;

import java.io.Serializable;
import java.util.Scanner;

public class RegistrationCommand extends Command implements Serializable {
    private static final long serialVersionUID = 75;


    @Override
    public SerializableAnswerToClient execute(String[] args) {
        return null;
    }

    @Override
    public SerializableAnswerToClient execute(String[] args, Scanner scanner) {
        return null;
    }

    public RegistrationCommand(Receiver receiver) {
        super(receiver);
    }
}
