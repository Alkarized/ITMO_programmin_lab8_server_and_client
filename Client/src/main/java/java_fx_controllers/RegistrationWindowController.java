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
import lombok.SneakyThrows;
import utils.AuthRegisterUser;
import utils.MainLocale;
import windows.JavaFXWorker;
import windows.SettingWindow;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

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

    @SneakyThrows
    public void setText() { //todo\
        //passwordField.setPromptText(MainLocale.getValue("reg_reg_field"));
        passwordField.setPromptText(new String(MainLocale.getResourceBundle().getString("reg_reg_field").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251"));
        loginButton.setText(new String(MainLocale.getResourceBundle().getString("reg_log_button").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251"));
        registerButton.setText(new String(MainLocale.getResourceBundle().getString("reg_reg_button").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251"));
        welcomeText.setText(new String(MainLocale.getResourceBundle().getString("reg_welcome_big").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251"));
        registerOrAuthText.setText(new String(MainLocale.getResourceBundle().getString("reg_welcome_lower").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251"));
        loginField.setPromptText(new String(MainLocale.getResourceBundle().getString("reg_log_field").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251"));
        errorText.setText("");
    }

    public void tryLogin(MouseEvent mouseEvent) throws IOException {
        if (loginField.getText().equals("") || passwordField.getText().equals("")){
            Shake loginFieldShake = new Shake(loginField);
            Shake passwordFieldShake = new Shake(passwordField);
            Flicker textFlicker = new Flicker(errorText);
            errorText.setText(new String(MainLocale.getResourceBundle().getString("reg_err1").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251"));
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
            errorText.setText(new String(MainLocale.getResourceBundle().getString("reg_err1").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251"));
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

    @SneakyThrows
    public void openSettingsWindow(MouseEvent mouseEvent) {
        SettingWindow settingWindow = new SettingWindow();
        settingWindow.display();
        setText();
        javaFXWorker.getWindow().setTitle(new String(MainLocale.getResourceBundle().getString("reg_title").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251"));
    }
}
