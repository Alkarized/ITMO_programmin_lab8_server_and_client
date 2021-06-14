package server;

import collection.Receiver;
import commands.serializable_commands.SerializableCommandStandard;
import commands.serializable_commands.SerializableCommandWithArgs;
import commands.serializable_commands.SerializableCommandWithObject;
import commands.serializable_commands.SerializableCommandWithObjectAndArgs;
import utils.MainLocale;
import message.MessageColor;
import message.Messages;
import utils.SerializableAnswerToClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

public class ObjectParser {
    public ByteBuffer parseObjectToByteBuffer(Object object, Receiver receiver) {
        //Messages.normalMessageOutput("sit in parsing object to ByteBuffer", MessageColor.ANSI_BLUE);
        SerializableAnswerToClient serializableAnswerToClient = null;
        if (object instanceof SerializableCommandWithObjectAndArgs) {
            SerializableCommandWithObjectAndArgs command = (SerializableCommandWithObjectAndArgs) object;
            MainLocale.setLocale(command.getLocale());
            serializableAnswerToClient = command.getCommand().execute(receiver, command.getFlat(), command.getArgs(), command.getUser());
        } else if (object instanceof SerializableCommandWithObject) {
            SerializableCommandWithObject command = (SerializableCommandWithObject) object;
            MainLocale.setLocale(command.getLocale());
            serializableAnswerToClient = command.getCommand().execute(receiver, command.getFlat(), null, command.getUser());
        } else if (object instanceof SerializableCommandWithArgs) {
            SerializableCommandWithArgs command = (SerializableCommandWithArgs) object;
            MainLocale.setLocale(command.getLocale());
            serializableAnswerToClient = command.getCommand().execute(receiver, null, command.getArgs(), command.getUser());
        } else if (object instanceof SerializableCommandStandard) {
            SerializableCommandStandard command = (SerializableCommandStandard) object;
            MainLocale.setLocale(command.getLocale());
            serializableAnswerToClient = command.getCommand().execute(receiver, null, null, command.getUser());
        }
        return parseAnswerToByteBuffer(serializableAnswerToClient);
    }

    private ByteBuffer parseAnswerToByteBuffer(SerializableAnswerToClient serializableAnswerToClient) {
        //Messages.normalMessageOutput("sit in parsin answer to ByteBuffer", MessageColor.ANSI_BLUE);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ByteBuffer byteBuffer = ByteBuffer.allocate(10000);
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(serializableAnswerToClient);
            byteBuffer.put(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            Messages.normalMessageOutput(e.toString(), MessageColor.ANSI_RED);
        }
        return byteBuffer;
    }
}
