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

    public void getInfoAboutAllCommands() throws IOException, ClassNotFoundException {

        if (connection.sendSerializableCommand(new SerializableCommandStandard(new HelpCommand(this), user))) {
            SerializableAnswerToClient ans = connection.getStringAnsFromServer();
            Messages.normalMessageOutput(ans.getAns(), ans.getColor());
        }

    }

    public void getInfoAboutCollection() throws IOException, ClassNotFoundException {
        if (connection.sendSerializableCommand(new SerializableCommandStandard(new InfoCommand(this), user))) {
            SerializableAnswerToClient ans = connection.getStringAnsFromServer();
            Messages.normalMessageOutput(ans.getAns(), ans.getColor());
        }
    }

    public void clear() throws IOException, ClassNotFoundException {
        if (connection.sendSerializableCommand(new SerializableCommandStandard(new ClearCommand(this), user))) {
            SerializableAnswerToClient ans = connection.getStringAnsFromServer();
            Messages.normalMessageOutput(ans.getAns(), ans.getColor());
        }
    }

    public void removeFirst() throws IOException, ClassNotFoundException {
        if (connection.sendSerializableCommand(new SerializableCommandStandard(new RemoveFirstCommand(this), user))) {
            SerializableAnswerToClient ans = connection.getStringAnsFromServer();
            Messages.normalMessageOutput(ans.getAns(), ans.getColor());
        }
    }

    public void printFieldDescendingNumberOfRooms() throws IOException, ClassNotFoundException {
        if (connection.sendSerializableCommand(new SerializableCommandStandard(new PrintFieldNumberOfRoomsCommand(this), user))) {
            SerializableAnswerToClient ans = connection.getStringAnsFromServer();
            Messages.normalMessageOutput(ans.getAns(), ans.getColor());
        }
    }

    public void getHead() throws IOException, ClassNotFoundException {
        if (connection.sendSerializableCommand(new SerializableCommandStandard(new HeadCommand(this), user))) {
            SerializableAnswerToClient ans = connection.getStringAnsFromServer();
            Messages.normalMessageOutput(ans.getAns(), ans.getColor());
        }
    }

    public void countLessNumberOfRooms(String[] args) throws IOException, ClassNotFoundException {
        if (connection.sendSerializableCommand(new SerializableCommandWithArgs(new CountLessCommand(this), user, args))) {
            SerializableAnswerToClient ans = connection.getStringAnsFromServer();
            Messages.normalMessageOutput(ans.getAns(), ans.getColor());
        }
    }

    public void printElementWithMinCoordinates() throws IOException, ClassNotFoundException {
        if (connection.sendSerializableCommand(new SerializableCommandStandard(new MinByCoordinatesCommand(this), user))) {
            SerializableAnswerToClient ans = connection.getStringAnsFromServer();
            Messages.normalMessageOutput(ans.getAns(), ans.getColor());
        }
    }

    public void removeById(String[] args) throws IOException, ClassNotFoundException {
        if (connection.sendSerializableCommand(new SerializableCommandWithArgs(new RemoveByIdCommand(this), user, args))) {
            SerializableAnswerToClient ans = connection.getStringAnsFromServer();
            Messages.normalMessageOutput(ans.getAns(), ans.getColor());
        }
    }

    public void printAllElements() throws IOException, ClassNotFoundException {
        if (connection.sendSerializableCommand(new SerializableCommandStandard(new ShowCommand(this), user))) {
            SerializableAnswerToClient ans = connection.getStringAnsFromServer();
            Messages.normalMessageOutput(ans.getAns(), ans.getColor());
        }
    }

    public void addElement(Flat flat) throws IOException, ClassNotFoundException {
        if (connection.sendSerializableCommand(new SerializableCommandWithObject(new AddCommand(this), user, flat))) {
            SerializableAnswerToClient ans = connection.getStringAnsFromServer();
            Messages.normalMessageOutput(ans.getAns(), ans.getColor());
        }
    }

    public void updateElementById(Flat flat, String[] args) throws IOException, ClassNotFoundException {
        if (connection.sendSerializableCommand(new SerializableCommandWithObjectAndArgs(new UpdateByIdCommand(this),
                user, flat, args))) {
            SerializableAnswerToClient ans = connection.getStringAnsFromServer();
            Messages.normalMessageOutput(ans.getAns(), ans.getColor());
        }
    }

    public void removeLowerElements(Flat flat) throws IOException, ClassNotFoundException {
        if (connection.sendSerializableCommand(new SerializableCommandWithObject(new RemoveLowerCommand(this), user, flat))) {
            SerializableAnswerToClient ans = connection.getStringAnsFromServer();
            Messages.normalMessageOutput(ans.getAns(), ans.getColor());
        }
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
