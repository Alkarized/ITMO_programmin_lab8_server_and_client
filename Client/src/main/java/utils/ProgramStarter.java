package utils;


import client.Connection;
import client.Invoker;
import client.Receiver;
import commands.*;
import lombok.Getter;
import lombok.Setter;
import message.MessageColor;
import message.Messages;

import java.util.Scanner;


public class ProgramStarter {

    @Getter
    private final Invoker invoker;
    @Getter
    private final Receiver receiver;
    private final Connection connection;
    @Getter @Setter
    private final AuthRegisterUser authRegisterUser;

    public ProgramStarter(String host, int port) {
        invoker = new Invoker();
        Messages.normalMessageOutput("Подключаемся к серверу...", MessageColor.ANSI_CYAN);
        connection = new Connection(host, port);
        //connection.startConnection(host, port, null);
        receiver = new Receiver(invoker, connection);
        authRegisterUser = new AuthRegisterUser(connection, receiver);
        registerAllCommands();
    }

    public void start() {
        /*LineReader lineReader = new LineReader();
        lineReader.readLine(new Scanner(System.in), invoker, true);*/
    }

    private void registerAllCommands() {
        invoker.registerNewCommand("add", new AddCommand(receiver));
        invoker.registerNewCommand("clear", new ClearCommand(receiver));
        invoker.registerNewCommand("count_less_than_number_of_rooms", new CountLessCommand(receiver));
        invoker.registerNewCommand("execute_script", new ExecuteScriptCommand(receiver));
        invoker.registerNewCommand("exit", new ExitCommand(receiver));
        invoker.registerNewCommand("head", new HeadCommand(receiver));
        invoker.registerNewCommand("help", new HelpCommand(receiver));
        invoker.registerNewCommand("info", new InfoCommand(receiver));
        invoker.registerNewCommand("min_by_coordinates", new MinByCoordinatesCommand(receiver));
        invoker.registerNewCommand("print_field_descending_number_of_rooms", new PrintFieldNumberOfRoomsCommand(receiver));
        invoker.registerNewCommand("remove_by_id", new RemoveByIdCommand(receiver));
        invoker.registerNewCommand("remove_first", new RemoveFirstCommand(receiver));
        invoker.registerNewCommand("remove_lower", new RemoveLowerCommand(receiver));
        invoker.registerNewCommand("show", new ShowCommand(receiver));
        invoker.registerNewCommand("update", new UpdateByIdCommand(receiver));
        invoker.registerNewCommand("getCollection", new GetCollectionCommand(receiver));
    }
}

