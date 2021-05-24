package commands.serializable_commands;

import commands.Command;
import fields.User;

import java.io.Serializable;

public class SerializableCommandStandard implements Serializable {
    private static final long serialVersionUID = 100;

    private User user;

    public SerializableCommandStandard() {

    }

    private Command command;

    public SerializableCommandStandard(Command command, User user) {
        this.command = command;
        this.user = user;
    }

    public Command getCommand() {
        return command;
    }

    public User getUser() {
        return user;
    }
}
