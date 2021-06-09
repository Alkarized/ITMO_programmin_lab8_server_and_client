package windows;

import client.Connection;
import java_fx_controllers.MainWindowController;
import java_fx_controllers.RegistrationWindowController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import utils.ProgramStarter;

import java.io.FileInputStream;
import java.io.IOException;

public class JavaFXWorker {
    @Getter
    private ProgramStarter programStarter;

    @Getter
    @Setter
    private Stage window;

    @Getter
    @Setter
    private RegistrationWindowController registrationWindowController;

    @Getter
    @Setter
    private MainWindowController mainWindowController;

    //init LogInOrRegisterWindow
    public void initializeWindow(Stage stage, ProgramStarter programStarter) throws IOException {
        this.programStarter = programStarter;
        //load LogInOrRegisterWindow
        window = stage;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Windows/registrationWindow.fxml"));
        Parent root = loader.load();
        //get LogInOrRegisterController
        registrationWindowController = loader.getController();
        registrationWindowController.setAuthRegisterUser(programStarter.getAuthRegisterUser());
        registrationWindowController.setJavaFXWorker(this);
        //set Locale Text
        registrationWindowController.setText();

        //set LogInOrRegisterWindow as main Window
        Scene scene = new Scene(root);
        window.setScene(scene);
        window.setResizable(false);
        window.getIcons().add(new Image(new FileInputStream("Client/src/main/resources/Pics/vt2021.png")));
        //set Title name of Window
        window.setTitle("Авторизация"); //todo

        //init all stuff
        window.show();
        window.centerOnScreen();

    }

    //Init and set mainWindow
    public void setMainWindow() throws IOException { //todo set it Private
        //get mainWindow from resources
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Windows/mainWindow.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        //Load controller
        mainWindowController = loader.getController();
        mainWindowController.setName(programStarter.getReceiver().getUser().getUsername());
        mainWindowController.setInvoker(programStarter.getInvoker());
        mainWindowController.setJavaFXWorker(this);
        //init table
        mainWindowController.initALlColumns(); //todo
        mainWindowController.setTextToColumns();
        //mainWindowController.getTextArea().textProperty().addListener((observable, oldValue, newValue) -> mainWindowController.getTextArea().setScrollTop(Double.MAX_VALUE));
        //set title name of Window
        window.setTitle("УЛЬТРАМЕГАВЫХУХОЛЬПОИМЕНИУТКА");//todo
        //init mainWindow
        window.setScene(scene);
        window.centerOnScreen();
    }


}
