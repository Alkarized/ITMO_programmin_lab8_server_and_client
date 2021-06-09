package commands;

import collection.Receiver;
import fields.Flat;
import fields.User;
import utils.SerializableAnswerToClient;

import java.io.Serializable;

public class GetCollectionCommand extends Command implements Serializable{
    private static final long serialVersionUID = 541;
    @Override
    public SerializableAnswerToClient execute(Receiver receiver, Flat flat, String[] args, User user) {
        SerializableAnswerToClient serializableAnswerToClient = receiver.getCollection();
        System.out.println(serializableAnswerToClient.getQueue());
        return serializableAnswerToClient;
    }
}
