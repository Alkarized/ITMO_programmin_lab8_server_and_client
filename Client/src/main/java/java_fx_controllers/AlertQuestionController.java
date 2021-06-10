package java_fx_controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import utils.MainLocale;

import java.nio.charset.StandardCharsets;

public class AlertQuestionController {
    @FXML
    @Getter @Setter
    private Button ansButton;

    @FXML
    @Getter @Setter
    private Text textQuest;

    @FXML
    @Getter @Setter
    private TextField filed;

    @Getter @Setter
    private Stage stage;

    public void pressedButton(MouseEvent mouseEvent) {
        stage.close();
    }

    @SneakyThrows
    public void localize(){
        textQuest.setText(new String(MainLocale.getResourceBundle().getString("quest_text").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251"));
        ansButton.setText(new String(MainLocale.getResourceBundle().getString("quest_ans").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251"));
    }
}
