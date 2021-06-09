package commands;

import client.Receiver;
import fields.Flat;
import message.MessageColor;
import message.Messages;
import utils.FlatMaker;
import utils.SerializableAnswerToClient;

import java.io.IOException;
import java.io.Serializable;
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
                Flat flat = new FlatMaker().makeFlat(scanner, "адддшка", "добавление", null);//todo
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
