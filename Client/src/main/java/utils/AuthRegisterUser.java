package utils;

import client.Connection;
import client.Receiver;
import commands.AuthorizationCommand;
import commands.RegistrationCommand;
import commands.serializable_commands.SerializableCommandStandard;
import fields.User;
import lombok.Getter;
import message.MessageColor;
import message.Messages;

import java.io.IOException;

public class AuthRegisterUser {
    @Getter
    private Connection connection;
    @Getter
    private Receiver receiver;

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
                        return "Не удалось создать новый аккаунт, возможно, такое имя уже используется, попробуйте еще раз";
                    }
                } else {
                    return "сервер недоступен! Попробуйте позже";
                }
            } catch (IOException | ClassNotFoundException e) {
                return "уппс, ашибка!";
            }
        } else {
            return "Ну нефего бубнить, сервер недоступен!";
        }
    }

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
                        return "Не удалось войти в аккаунт, попробуйте занова";
                    }
                } else {
                    return "сервер недоступен! Попробуйте позже";
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                return "уппс, ашибка!";
            }
        } else {
            return "Ну нефего бубнить, сервер недоступен!";
        }
    }

    public AuthRegisterUser(Connection connection, Receiver receiver) {
        this.connection = connection;
        this.receiver = receiver;
    }

}
