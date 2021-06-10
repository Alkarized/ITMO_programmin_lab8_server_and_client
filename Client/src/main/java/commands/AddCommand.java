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

public class AddCommand extends Command implements Serializable {
    private static final long serialVersionUID = 50;

    public AddCommand(Receiver receiver) {
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
                        new String(MainLocale.getResourceBundle().getString("flat_add_topic").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251"),
                        new String(MainLocale.getResourceBundle().getString("flat_add_title").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251")
                        , null);//todo
                if (flat != null) {
                    flat.setUser(receiver.getUser());
                    return receiver.addElement(flat);
                }
            } else
                Messages.normalMessageOutput("Неправильно введены аргументы", MessageColor.ANSI_RED);
        } catch (IOException | ClassNotFoundException e) {
            Messages.normalMessageOutput("Что-то пошло не так..." + e.toString(), MessageColor.ANSI_RED);
            e.printStackTrace();
        }
        return null;
    }
}
