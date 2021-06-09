package commands;

import client.Receiver;
import message.MessageColor;
import message.Messages;
import utils.SerializableAnswerToClient;

import java.io.Serializable;
import java.util.Scanner;

public class ExecuteScriptCommand extends Command implements Serializable {
    public ExecuteScriptCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public SerializableAnswerToClient execute(String[] args) {
        if (args.length == 2) {
            SerializableAnswerToClient serializableAnswerToClient = new SerializableAnswerToClient();
            serializableAnswerToClient.setBool(receiver.executeScript(args[1]));
        }
        else
            Messages.normalMessageOutput("Неправильно введены аргументы", MessageColor.ANSI_RED);
        return null;
    }

    @Override
    public SerializableAnswerToClient execute(String[] args, Scanner scanner) {
        return execute(args);
    }

}
