package java_fx_controllers;

import animations.Flicker;
import animations.Shake;
import fields.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import utils.AuthRegisterUser;
import windows.JavaFXWorker;

import java.io.IOException;

public class RegistrationWindowController {

    @Getter
    @Setter
    public AuthRegisterUser authRegisterUser;

    @Getter
    @Setter
    public JavaFXWorker javaFXWorker;

    @FXML
    @Getter
    @Setter
    public TextField loginField;

    @FXML
    @Getter
    @Setter
    public PasswordField passwordField;

    @FXML
    @Getter
    @Setter
    public Text errorText;

    @FXML
    @Getter
    @Setter
    public Button loginButton;

    @FXML
    @Getter
    @Setter
    public Button registerButton;

    @FXML
    public ImageView settingsButton;

    @FXML
    @Getter
    @Setter
    private Text welcomeText;

    @FXML
    @Getter
    @Setter
    private Text registerOrAuthText;

    /*@FXML @Getter @Setter
    private Hyperlink forgetPwdLink;*/

    public void setText() {
        passwordField.setPromptText("password");
        loginButton.setText("Log in");
        registerButton.setText("Register");
        welcomeText.setText("WelCUMe!");
        registerOrAuthText.setText("Don't afraid, log in!");
        loginField.setPromptText("login");
    }

    public void tryLogin(MouseEvent mouseEvent) throws IOException {
        if (loginField.getText().equals("") || passwordField.getText().equals("")){
            Shake loginFieldShake = new Shake(loginField);
            Shake passwordFieldShake = new Shake(passwordField);
            Flicker textFlicker = new Flicker(errorText);
            errorText.setText("Не введен логин или пароль");
            textFlicker.flicking();
            loginFieldShake.shaking();
            passwordFieldShake.shaking();
            return;
        }
        String text = authRegisterUser.authorizeUser(new User(loginField.getText(), passwordField.getText()));
        if (text.equals("")){
            javaFXWorker.setMainWindow();
        } else {
            Shake shake = new Shake(errorText);
            errorText.setText(text);
            shake.shaking();
        }
    }

    public void tryRegister(MouseEvent mouseEvent) throws IOException {
        if (loginField.getText().equals("") || passwordField.getText().equals("")){
            Shake loginFieldShake = new Shake(loginField);
            Shake passwordFieldShake = new Shake(passwordField);
            Flicker textFlicker = new Flicker(errorText);
            errorText.setText("Не введен логин или пароль");
            textFlicker.flicking();
            loginFieldShake.shaking();
            passwordFieldShake.shaking();
            return;
        }
        String text = authRegisterUser.registerNewUser(new User(loginField.getText(), passwordField.getText()));
        if (text.equals("")){
            javaFXWorker.setMainWindow();
        } else {
            Shake loginFieldShake = new Shake(loginField);
            Shake passwordFieldShake = new Shake(passwordField);
            Flicker textFlicker = new Flicker(errorText);
            errorText.setText(text);
            textFlicker.flicking();
            loginFieldShake.shaking();
            passwordFieldShake.shaking();
        }
    }

    public void openSettingsWindow(MouseEvent mouseEvent) {
        SettingsController settingsPage = new SettingsController();
        settingsPage.start(new Stage());
    }
}
