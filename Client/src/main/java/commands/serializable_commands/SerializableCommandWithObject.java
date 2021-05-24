package commands.serializable_commands;

import commands.Command;
import fields.Flat;
import fields.User;

public class SerializableCommandWithObject extends SerializableCommandStandard {
    private static final long serialVersionUID = 101;

    private Flat flat;

    public SerializableCommandWithObject(Command command, User user, Flat flat) {
        super(command, user);
        this.flat = flat;
    }

    public Flat getFlat() {
        return flat;
    }
}
