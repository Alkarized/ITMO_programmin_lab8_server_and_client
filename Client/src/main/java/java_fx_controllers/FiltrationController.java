package java_fx_controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import lombok.Getter;
import lombok.Setter;
import windows.FiltrationWindow;

public class FiltrationController {

    @FXML @Getter @Setter
    private Text value;
    @FXML @Getter @Setter
    private Text textFieldo;
    @FXML @Getter @Setter
    private Text errorText;
    @FXML @Setter @Getter
    private Button useFilter;
    @FXML @Getter @Setter
    private Button clearFilter;
    @FXML @Getter @Setter
    private TextField textField;
    @FXML @Getter @Setter
    private ChoiceBox choiceBox;
    @FXML @Getter @Setter
    private RadioButton radioContains;
    @FXML
    @Getter @Setter
    private RadioButton radioNotContains;

    @FXML
    public void initialize(){
        ToggleGroup toggleGroup = new ToggleGroup();
        toggleGroup.getToggles().addAll(radioNotContains, radioContains);
        radioContains.setSelected(true);
        radioNotContains.setSelected(false);
    }

}
