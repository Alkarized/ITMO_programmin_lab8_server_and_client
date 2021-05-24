package commands;

import client.Receiver;

import java.io.Serializable;
import java.util.Scanner;

public class RegistrationCommand extends Command implements Serializable {
    private static final long serialVersionUID = 75;


    @Override
    public void execute(String[] args) {

    }

    @Override
    public void execute(String[] args, Scanner scanner) {

    }

    public RegistrationCommand(Receiver receiver) {
        super(receiver);
    }
}
