package commands;

import collection.Receiver;
import fields.Flat;
import fields.User;
import message.MessageColor;
import utils.SerializableAnswerToClient;

import java.io.IOException;
import java.io.Serializable;

public class HeadCommand extends Command implements Serializable {
    private static final long serialVersionUID = 54;

    @Override
    public SerializableAnswerToClient execute(Receiver receiver, Flat flat, String[] args, User user) {
        try {
            return receiver.getHead();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new SerializableAnswerToClient(MessageColor.ANSI_RED, "Ошибка соединения");
        }
    }
}
