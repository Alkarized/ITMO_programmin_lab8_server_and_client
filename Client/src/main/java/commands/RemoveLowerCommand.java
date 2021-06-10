package commands;

import client.Receiver;
import fields.Flat;
import message.MessageColor;
import message.Messages;
import utils.FlatMaker;
import utils.MainLocale;
import utils.SerializableAnswerToClient;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class RemoveLowerCommand extends Command implements Serializable {
    private static final long serialVersionUID = 61;


    public RemoveLowerCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public SerializableAnswerToClient execute(String[] args) {
        return execute(args, null);
    }

    @Override
    public SerializableAnswerToClient execute(String[] args, Scanner scanner) {

        try {
            if (args.length == 1) {
                Flat flat = new FlatMaker().makeFlat(scanner,
                        new String(MainLocale.getResourceBundle().getString("flat_remove_topic").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251"),
                        new String(MainLocale.getResourceBundle().getString("flat_remove_title").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251")
                        , null);
                if (flat != null)
                    return receiver.removeLowerElements(flat);
                else
                    Messages.normalMessageOutput("Неправильно введены аргументы", MessageColor.ANSI_RED);
            } else {
                Messages.normalMessageOutput("Неправильно введены аргументы", MessageColor.ANSI_RED);
            }
        } catch (IOException | ClassNotFoundException e) {
            Messages.normalMessageOutput("Что-то пошло не так..." + e.toString(), MessageColor.ANSI_RED);
        }
        return null;
    }

}
