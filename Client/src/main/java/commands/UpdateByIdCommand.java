package commands;

import client.Receiver;
import fields.Flat;
import message.MessageColor;
import message.Messages;
import utils.FlatGetter;
import utils.FlatMaker;
import utils.MainLocale;
import utils.SerializableAnswerToClient;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class UpdateByIdCommand extends Command implements Serializable {
    private static final long serialVersionUID = 63;


    public UpdateByIdCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public SerializableAnswerToClient execute(String[] args) {
         return execute(args, null);
    }

    @Override
    public SerializableAnswerToClient execute(String[] args, Scanner scanner) {
        try {
            if (args.length == 2) {
                Flat flat = new FlatMaker().makeFlat(scanner,
                        new String(MainLocale.getResourceBundle().getString("flat_update_topic").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251"),
                        new String(MainLocale.getResourceBundle().getString("flat_update_title").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251")
                        , FlatGetter.getFlat());
                if (flat != null) {
                    Long.valueOf(args[1]);
                    return receiver.updateElementById(flat, args);
                } else {
                    throw new NumberFormatException();
                }
            } else {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            Messages.normalMessageOutput("Неправильно введены аргументы", MessageColor.ANSI_RED);
        } catch (IOException | ClassNotFoundException e1) {
            Messages.normalMessageOutput("Что-то пошло не так..." + e1.toString(), MessageColor.ANSI_RED);
        }
        return null;
    }
}
