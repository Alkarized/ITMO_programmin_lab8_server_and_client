import javafx.application.Application;
import javafx.stage.Stage;
import windows.JavaFXWorker;

import java.io.IOException;


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
        javaFXWorker.setMainWindow();
        //RegisterAlertBoxController.display();
    }
}
