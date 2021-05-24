package commands.serializable_commands;

import commands.Command;
import fields.Flat;
import fields.User;

public class SerializableCommandWithObjectAndArgs extends SerializableCommandWithObject {
    private static final long serialVersionUID = 103;
    private String[] args;

    public SerializableCommandWithObjectAndArgs(Command command, User user, Flat flat, String[] args) {
        super(command, user, flat);
        this.args = args;
    }

    public String[] getArgs() {
        return args;
    }
}
