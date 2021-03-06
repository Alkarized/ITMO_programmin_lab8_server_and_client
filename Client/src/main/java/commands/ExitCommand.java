package commands;

import client.Receiver;
import message.MessageColor;
import message.Messages;
import utils.SerializableAnswerToClient;

import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;

public class ExitCommand extends Command implements Serializable {

    public ExitCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public SerializableAnswerToClient execute(String[] args) {
        try {
            if (args.length == 1)
                receiver.exit();
            else
                Messages.normalMessageOutput("Неправильно введены аргументы", MessageColor.ANSI_RED);
        } catch (IOException ignored) {
        }
        return null;
    }


    @Override
    public SerializableAnswerToClient execute(String[] args, Scanner scanner) {
        return execute(args);
    }

}
