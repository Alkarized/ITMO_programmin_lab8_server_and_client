package commands;

import client.Receiver;
import message.MessageColor;
import message.Messages;
import utils.SerializableAnswerToClient;

import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;

public class RemoveFirstCommand extends Command implements Serializable {
    private static final long serialVersionUID = 60;


    public RemoveFirstCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public SerializableAnswerToClient execute(String[] args) {
        try {
            if (args.length == 1)
                return receiver.removeFirst();
            else
                Messages.normalMessageOutput("Неправильно введены аргументы", MessageColor.ANSI_RED);
        } catch (IOException | ClassNotFoundException e) {
            Messages.normalMessageOutput("Что-то пошло не так..." + e.toString(), MessageColor.ANSI_RED);
        }
        return null;
    }

    @Override
    public SerializableAnswerToClient execute(String[] args, Scanner scanner) {
        return execute(args);
    }

}
