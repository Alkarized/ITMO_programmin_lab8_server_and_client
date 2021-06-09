package client;

import commands.*;
import commands.serializable_commands.SerializableCommandStandard;
import commands.serializable_commands.SerializableCommandWithArgs;
import commands.serializable_commands.SerializableCommandWithObject;
import commands.serializable_commands.SerializableCommandWithObjectAndArgs;
import fields.Flat;
import fields.User;
import message.MessageColor;
import message.Messages;
import utils.LineReader;
import utils.SerializableAnswerToClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Receiver {
    private final Invoker invoker;
    private final Connection connection;
    private User user;

    public Receiver(Invoker invoker, Connection connection) {
        this.invoker = invoker;
        this.connection = connection;
    }

    public void exit() throws IOException {
        //connection.endConnection();
        System.exit(0);
    }

    public SerializableAnswerToClient getInfoAboutAllCommands() throws IOException, ClassNotFoundException {

        if (connection.sendSerializableCommand(new SerializableCommandStandard(new HelpCommand(this), user))) {
            return connection.getStringAnsFromServer();
        }
        return null;

    }

    public SerializableAnswerToClient getInfoAboutCollection() throws IOException, ClassNotFoundException {
        if (connection.sendSerializableCommand(new SerializableCommandStandard(new InfoCommand(this), user))) {
            return connection.getStringAnsFromServer();
        }
        return null;
    }

    public SerializableAnswerToClient clear() throws IOException, ClassNotFoundException {
        if (connection.sendSerializableCommand(new SerializableCommandStandard(new ClearCommand(this), user))) {
            return connection.getStringAnsFromServer();
        }
        return null;
    }

    public SerializableAnswerToClient removeFirst() throws IOException, ClassNotFoundException {
        if (connection.sendSerializableCommand(new SerializableCommandStandard(new RemoveFirstCommand(this), user))) {
            return connection.getStringAnsFromServer();
        }
        return null;
    }

    public SerializableAnswerToClient printFieldDescendingNumberOfRooms() throws IOException, ClassNotFoundException {
        if (connection.sendSerializableCommand(new SerializableCommandStandard(new PrintFieldNumberOfRoomsCommand(this), user))) {
            return connection.getStringAnsFromServer();
        }
        return null;
    }

    public SerializableAnswerToClient getHead() throws IOException, ClassNotFoundException {
        if (connection.sendSerializableCommand(new SerializableCommandStandard(new HeadCommand(this), user))) {
            return connection.getStringAnsFromServer();
        }
        return null;
    }

    public SerializableAnswerToClient countLessNumberOfRooms(String[] args) throws IOException, ClassNotFoundException {
        if (connection.sendSerializableCommand(new SerializableCommandWithArgs(new CountLessCommand(this), user, args))) {
            return connection.getStringAnsFromServer();
        }
        return null;
    }

    public SerializableAnswerToClient printElementWithMinCoordinates() throws IOException, ClassNotFoundException {
        if (connection.sendSerializableCommand(new SerializableCommandStandard(new MinByCoordinatesCommand(this), user))) {
            return connection.getStringAnsFromServer();
        }
        return null;
    }

    public SerializableAnswerToClient removeById(String[] args) throws IOException, ClassNotFoundException {
        if (connection.sendSerializableCommand(new SerializableCommandWithArgs(new RemoveByIdCommand(this), user, args))) {
            return connection.getStringAnsFromServer();
        }
        return null;
    }

    public SerializableAnswerToClient printAllElements() throws IOException, ClassNotFoundException {
        if (connection.sendSerializableCommand(new SerializableCommandStandard(new ShowCommand(this), user))) {
            return connection.getStringAnsFromServer();
        }
        return null;
    }

    public SerializableAnswerToClient addElement(Flat flat) throws IOException, ClassNotFoundException {
        if (connection.sendSerializableCommand(new SerializableCommandWithObject(new AddCommand(this), user, flat))) {
            return connection.getStringAnsFromServer();
        }
        return null;
    }

    public SerializableAnswerToClient updateElementById(Flat flat, String[] args) throws IOException, ClassNotFoundException {
        if (connection.sendSerializableCommand(new SerializableCommandWithObjectAndArgs(new UpdateByIdCommand(this),
                user, flat, args))) {
            return connection.getStringAnsFromServer();
        }
        return null;
    }

    public SerializableAnswerToClient removeLowerElements(Flat flat) throws IOException, ClassNotFoundException {
        if (connection.sendSerializableCommand(new SerializableCommandWithObject(new RemoveLowerCommand(this), user, flat))) {
            return connection.getStringAnsFromServer();
        }
        return null;
    }

    public SerializableAnswerToClient getAllCollection() throws IOException, ClassNotFoundException {
        if (connection.sendSerializableCommand(new SerializableCommandStandard(new GetCollectionCommand(this), user))) {
            return connection.getStringAnsFromServer();
        }
        return null;
    }

    public boolean executeScript(String args) {
        LineReader lineReader = new LineReader();
        File file = null;
        try {
            file = new File(args);
            if (!file.exists() || !file.canRead() || !file.canWrite()) {
                throw new IllegalAccessError();
            }
            lineReader.readLine(new Scanner(file), invoker, false);
        } catch (IllegalAccessError | FileNotFoundException e) {
            Messages.normalMessageOutput("Невозможно работать с данным файлом, попробуйте еще раз", MessageColor.ANSI_RED);
            return false;
        } catch (StackOverflowError | OutOfMemoryError e) {
            Messages.normalMessageOutput("ЭЭЭЭЭ, куда, рекурся зло, вышел и зашел обратно!", MessageColor.ANSI_RED);
            return false;
        }
        Messages.normalMessageOutput("Закончилось выполнение скрипта из файла", MessageColor.ANSI_GREEN);
        return true;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
