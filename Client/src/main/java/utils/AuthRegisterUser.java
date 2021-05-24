package utils;

import client.Connection;
import client.Receiver;
import commands.AuthorizationCommand;
import commands.RegistrationCommand;
import commands.serializable_commands.SerializableCommandStandard;
import fields.User;
import message.MessageColor;
import message.Messages;

import java.io.Console;
import java.io.IOException;
import java.util.Scanner;

public class AuthRegisterUser {
    private String username;
    private String password;
    private final Scanner scanner = new Scanner(System.in);


    public void authOrRegisterUser(Connection connection, Receiver receiver) {
        User user;
        while (connection.isNeedToAuth()) {
            Messages.normalMessageOutput("Напишите reg, чтобы создать новый аккаунт или in, чтобы войту в уже существущий:", MessageColor.ANSI_BLUE);
            String line = scanner.nextLine();
            String[] args = line.trim().split(" ");
            if (args.length == 1) {
                if ("in".equals(args[0]) || "reg".equals(args[0])) {
                    Console console = System.console();
                    if ("in".equals(args[0])) {
                        Messages.normalMessageOutput("Введите данные для входа", MessageColor.ANSI_BLUE);
                    } else {
                        Messages.normalMessageOutput("Введите данные для регестрации", MessageColor.ANSI_BLUE);
                    }
                    while (true) {
                        System.out.print("Введите логин:");
                        String tempLine = scanner.nextLine();
                        String[] words = tempLine.trim().split(" ");
                        if (words.length == 1) {
                            username = words[0];
                            break;
                        } else {
                            Messages.normalMessageOutput("Неправильный ввод, попробуйте еще раз, нужно 1 слово в виде Логина", MessageColor.ANSI_RED);
                        }
                    }

                    while (true) {
                        System.out.print("Введите пароль:");
                        String tempLine;
                        if (console != null) {
                            tempLine = String.valueOf(console.readPassword());
                        } else {
                            tempLine = scanner.nextLine();
                        }

                        String[] words = tempLine.trim().split(" ");
                        if (words.length == 1) {
                            password = words[0];
                            break;
                        } else {
                            Messages.normalMessageOutput("Неправильный ввод, попробуйте еще раз, нужно 1 слово в виде пароля", MessageColor.ANSI_RED);
                        }
                    }
                    user = new User(username, password);
                    try {
                        if ("in".equals(args[0])) {

                            if (connection.sendSerializableCommand(new SerializableCommandStandard(new AuthorizationCommand(receiver), user))) {
                                SerializableAnswerToClient ans = connection.getStringAnsFromServer();
                                Messages.normalMessageOutput(ans.getAns(), ans.getColor());
                                if (ans.getBool()) {
                                    receiver.setUser(user);
                                    break;
                                } else {
                                    Messages.normalMessageOutput("Не удалось войти в аккаунт, попробуйте занова", MessageColor.ANSI_RED);
                                }
                            }

                        } else {
                            if (connection.sendSerializableCommand(new SerializableCommandStandard(new RegistrationCommand(receiver), user))) {
                                SerializableAnswerToClient ans = connection.getStringAnsFromServer();
                                Messages.normalMessageOutput(ans.getAns(), ans.getColor());
                                if (ans.getBool()) {
                                    receiver.setUser(user);
                                    break;
                                } else {
                                    Messages.normalMessageOutput("Не удалось создать новый аккаунт, возможно, такое имя уже используется, попробуйте еще раз", MessageColor.ANSI_RED);
                                }
                            }
                        }
                    } catch (IOException | ClassNotFoundException e) {
                        Messages.normalMessageOutput("Что-то пошло не так..., попробуйте еще раз.", MessageColor.ANSI_RED);
                    }
                }
            } else {
                Messages.normalMessageOutput("Неправльный ввод, попробуйте еще раз", MessageColor.ANSI_RED);
            }
        }
    }

}
