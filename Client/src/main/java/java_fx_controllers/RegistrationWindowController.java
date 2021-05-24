package java_fx_controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import lombok.Getter;
import lombok.Setter;

public class RegistrationWindowController {

    @FXML @Getter @Setter
    public PasswordField passwordField;

    @FXML @Getter @Setter
    public Text errorText;

    @FXML @Getter @Setter
    public Button loginButton;

    @FXML @Getter @Setter
    public Button registerButton;

    @FXML @Getter @Setter
    private Text welcomeText;

    @FXML @Getter @Setter
    private Text registerOrAuthText;

    /*@FXML @Getter @Setter
    private Hyperlink forgetPwdLink;*/

    @FXML @Getter @Setter
    public TextField loginField;
    
    
}
