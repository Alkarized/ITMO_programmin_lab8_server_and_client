package commands.serializable_commands;

import commands.Command;
import fields.User;

public class SerializableCommandWithArgs extends SerializableCommandStandard {
    private static final long serialVersionUID = 102;

    private final String[] args;

    public SerializableCommandWithArgs(Command command, User user, String[] args) {
        super(command, user);
        this.args = args;
    }

    public String[] getArgs() {
        return args;
    }
}
