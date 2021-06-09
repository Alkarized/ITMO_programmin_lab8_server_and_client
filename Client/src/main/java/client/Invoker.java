package client;

import commands.Command;
import message.MessageColor;
import message.Messages;
import utils.SerializableAnswerToClient;

import java.util.HashMap;
import java.util.Scanner;

public class Invoker {

    private final HashMap<String, Command> hashMap = new HashMap<>();

    public SerializableAnswerToClient executeCommand(Scanner scanner, String[] args) {
        if (args.length > 0) {
            Command command = hashMap.get(args[0]);
            if (command == null) {
                Messages.normalMessageOutput("Неправильный ввод команды, попробуйте написать help и посмотреть" +
                        " доступные команды", MessageColor.ANSI_RED);
            } else {
                if (scanner == null) {
                    return command.execute(args);
                } else {
                   return command.execute(args, scanner);
                }
            }
        }
        return null;
    }

    public void registerNewCommand(String commandName, Command command) {
        hashMap.put(commandName, command);
    }

    public HashMap<String, Command> getHashMap() {
        return hashMap;
    }

}
