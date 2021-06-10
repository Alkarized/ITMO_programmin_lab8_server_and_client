package commands.serializable_commands;

import commands.Command;
import fields.User;
import java.io.Serializable;
import java.util.Locale;

public class SerializableCommandStandard implements Serializable {
    private static final long serialVersionUID = 100;
    private Locale locale;
    private User user;

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

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
