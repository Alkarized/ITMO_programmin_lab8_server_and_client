package commands;

import client.Receiver;
import message.MessageColor;
import message.Messages;
import utils.SerializableAnswerToClient;

import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;

public class InfoCommand extends Command implements Serializable {
    private static final long serialVersionUID = 56;


    public InfoCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public SerializableAnswerToClient execute(String[] args) {
        try {
            if (args.length == 1)
                return receiver.getInfoAboutCollection();
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
