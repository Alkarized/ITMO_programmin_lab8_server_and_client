package java_fx_controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.text.Text;
import lombok.Getter;
import lombok.Setter;

public class RegistrationWindowController {
    @FXML
    @Getter
    @Setter
    private Text welcomeText;

    @FXML
    @Getter
    @Setter
    private Text registerOrAuthText;

    @FXML
    @Getter
    @Setter
    private Hyperlink forgetPwdLink;

}
