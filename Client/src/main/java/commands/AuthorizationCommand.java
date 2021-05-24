package commands;

import client.Receiver;

import java.io.Serializable;
import java.util.Scanner;

public class AuthorizationCommand extends Command implements Serializable {
    private static final long serialVersionUID = 72;


    public AuthorizationCommand(Receiver receiver) {
        super(receiver);
    }

    public AuthorizationCommand() {
    }

    @Override
    public void execute(String[] args) {

    }

    @Override
    public void execute(String[] args, Scanner scanner) {

    }
}
