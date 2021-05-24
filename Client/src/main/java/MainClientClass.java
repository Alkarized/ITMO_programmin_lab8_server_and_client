import utils.ProgramStarter;

import java.io.IOException;


public class MainClientClass {
    public static void main(String[] args) throws IOException {
        ProgramStarter programStarter = new ProgramStarter("localhost", 7777);
        programStarter.start();
    }
}
