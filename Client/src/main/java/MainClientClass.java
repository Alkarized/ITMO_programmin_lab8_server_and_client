import javafx.application.Application;
import javafx.stage.Stage;
import utils.MainLocale;
import windows.JavaFXWorker;

import java.awt.*;
import java.io.IOException;
import java.util.Locale;


public class MainClientClass extends Application {
    public static void main(String[] args) throws IOException {
        /*ProgramStarter programStarter = new ProgramStarter("localhost", 7777);
        programStarter.start();*/
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        JavaFXWorker javaFXWorker = new JavaFXWorker();
        javaFXWorker.initializeWindow(primaryStage);
        //MainLocale.setLocale(new Locale("ru","RU"));
        //System.out.println(MainLocale.getResourceBundle().getString("test"));
        //javaFXWorker.setMainWindow();
        //RegisterAlertBoxController.display();
    }
}
