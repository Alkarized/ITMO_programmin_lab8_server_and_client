import javafx.application.Application;
import javafx.stage.Stage;
import utils.MainLocale;
import utils.ProgramStarter;
import windows.JavaFXWorker;
import windows.RegisterAlertBoxController;

import java.awt.*;
import java.io.IOException;
import java.util.Locale;


public class MainClientClass extends Application {
    public static void main(String[] args) throws IOException {
        /*ProgramStarter programStarter = new ProgramStarter("localhost", 7879);
        programStarter.start();*/
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ProgramStarter programStarter = new ProgramStarter("localhost", 7879);
        JavaFXWorker javaFXWorker = new JavaFXWorker();
        //Init LogInOrRegisterWindow
        javaFXWorker.initializeWindow(primaryStage, programStarter);

        //MainLocale.setLocale(new Locale("ru","RU"));
        //System.out.println(MainLocale.getResourceBundle().getString("test"));
        //javaFXWorker.setMainWindow();
        //RegisterAlertBoxController.display();
    }
}
