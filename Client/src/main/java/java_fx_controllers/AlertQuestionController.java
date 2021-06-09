package java_fx_controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

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

    public void localize(){
        textQuest.setText("Введите значение для сравнения: ");
        ansButton.setText("Подсчитать кол-во");
    }
}
