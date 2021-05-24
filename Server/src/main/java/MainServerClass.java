/*import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;*/

import utils.ProgramStarter;

public class MainServerClass {

    public static void main(String[] args) {
        ProgramStarter programStarter = new ProgramStarter(7777);
        programStarter.startProgram();
    }

}