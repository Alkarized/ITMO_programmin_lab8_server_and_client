package commands;


import client.Receiver;
import message.MessageColor;
import message.Messages;
import utils.SerializableAnswerToClient;

import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;


public class ClearCommand extends Command implements Serializable {
    private static final long serialVersionUID = 51;

    public ClearCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public SerializableAnswerToClient execute(String[] args) {

        try {
            if (args.length == 1)
                return receiver.clear();
            else
                Messages.normalMessageOutput("Неправильно введены аргументы", MessageColor.ANSI_RED);
        } catch (IOException | ClassNotFoundException e1) {
            Messages.normalMessageOutput("Что-то пошло не так..." + e1.toString(), MessageColor.ANSI_RED);
        }
        return null;
    }

    @Override
    public SerializableAnswerToClient execute(String[] args, Scanner scanner) {
        return execute(args);
    }

}