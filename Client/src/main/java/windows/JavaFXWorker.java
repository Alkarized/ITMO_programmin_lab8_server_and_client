package windows;

import java_fx_controllers.MainWindowController;
import java_fx_controllers.RegistrationWindowController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.io.FileInputStream;
import java.io.IOException;

public class JavaFXWorker {
    @Getter
    @Setter
    private Stage window;

    @Getter
    @Setter
    private RegistrationWindowController registrationWindowController;

    @Getter
    @Setter
    private MainWindowController mainWindowController;

    public void initializeWindow(Stage stage) throws IOException {
        window = stage;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Windows/registrationWindow.fxml"));
        Parent root = loader.load();
        registrationWindowController = loader.getController();
        Scene scene = new Scene(root);
        window.setScene(scene);
        window.setResizable(false);
        window.setTitle("Авторизация"); //todo
        window.getIcons().add(new Image(new FileInputStream("Client/src/main/resources/Pics/vt2021.png")));
        setAllLocalTextForRegisterWindow();
        window.show();
    }

    private void setAllLocalTextForRegisterWindow() { //todo
        registrationWindowController.getWelcomeText().setText("WelCUMe!");
    }

    public void setMainWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Windows/mainWindow.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        window.setTitle("УЛЬТРАМЕГАВЫХУХОЛЬПОИМЕНИУТКА");//todo
        mainWindowController = loader.getController();
        mainWindowController.initALlColumns();
        //mainWindowController.initImage();
        window.setScene(scene);
        window.centerOnScreen();
    }
}
