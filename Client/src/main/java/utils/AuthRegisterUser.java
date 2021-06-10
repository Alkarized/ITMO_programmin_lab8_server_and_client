package utils;

import client.Connection;
import client.Receiver;
import commands.AuthorizationCommand;
import commands.RegistrationCommand;
import commands.serializable_commands.SerializableCommandStandard;
import fields.User;
import lombok.Getter;
import lombok.SneakyThrows;
import message.MessageColor;
import message.Messages;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class AuthRegisterUser {
    @Getter
    private Connection connection;
    @Getter
    private Receiver receiver;

    @SneakyThrows
    public String registerNewUser(User user) {
        if (connection.tryConnect()) {
            try {
                if (connection.sendSerializableCommand(new SerializableCommandStandard(new RegistrationCommand(receiver), user))) {
                    SerializableAnswerToClient ans = connection.getStringAnsFromServer();
                    //Messages.normalMessageOutput(ans.getAns(), ans.getColor());
                    if (ans.getBool()) {
                        receiver.setUser(user);
                        return "";
                    } else {
                        return new String(MainLocale.getResourceBundle().getString("reg_err2_login").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251");
                    }
                } else {
                    return new String(MainLocale.getResourceBundle().getString("reg_err3").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251");
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                return new String(MainLocale.getResourceBundle().getString("reg_err4").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251");
            }
        } else {
            return new String(MainLocale.getResourceBundle().getString("reg_err5").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251");
        }
    }

    @SneakyThrows
    public String authorizeUser(User user) {
        if (connection.tryConnect()) {
            try {
                if (connection.sendSerializableCommand(new SerializableCommandStandard(new AuthorizationCommand(receiver), user))) {
                    SerializableAnswerToClient ans = connection.getStringAnsFromServer();
                    //Messages.normalMessageOutput(ans.getAns(), ans.getColor());
                    if (ans.getBool()) {
                        receiver.setUser(user);
                        return "";
                    } else {
                        return new String(MainLocale.getResourceBundle().getString("reg_err2_reg").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251");
                    }
                } else {
                    return new String(MainLocale.getResourceBundle().getString("reg_err3").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251");
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                return new String(MainLocale.getResourceBundle().getString("reg_err4").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251");
            }
        } else {
            return new String(MainLocale.getResourceBundle().getString("reg_err5").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251");
        }
    }

    public AuthRegisterUser(Connection connection, Receiver receiver) {
        this.connection = connection;
        this.receiver = receiver;
    }

}
