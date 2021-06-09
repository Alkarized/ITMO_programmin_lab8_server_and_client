package windows;

import animations.Shake;
import fields.Flat;
import java_fx_controllers.FiltrationController;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import utils.HashFields;

import java.util.ArrayList;


public class FiltrationWindow {
    @Getter
    @Setter
    private FiltrationController filtrationController;
    @Getter
    @Setter
    private TableView<Flat> tableView;
    @Getter @Setter
    private HashFields hashFields;
    private final ArrayList<Flat> eatenItems = new ArrayList<>();

    @SneakyThrows
    public void display() {
        Stage window = new Stage();
        window.setTitle("filter"); //todo
        window.initModality(Modality.NONE);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Windows/filtration.fxml"));
        Parent root = loader.load();

        filtrationController = loader.getController();
        filtrationController.getClearFilter().setOnMouseClicked((e) -> {
            filtrationController.getChoiceBox().setValue(null);
            filtrationController.getRadioContains().setSelected(true);
            filtrationController.getRadioNotContains().setSelected(false);
            filtrationController.getTextField().clear();
            filtrationController.getErrorText().setText("");
            tableView.getItems().addAll(eatenItems);
        });
        filtrationController.getUseFilter().setOnMouseClicked((event -> {
            if (filtrationController.getChoiceBox().getValue() == null) {
                filtrationController.getErrorText().setText("Пожалуйста, выберите поле");//todo
                Shake shake = new Shake(filtrationController.getErrorText());
                shake.shaking();
            } else {
                tableView.getItems().addAll(eatenItems);
                eatenItems.clear();
                TableColumn<Flat, ?> tableColumn = hashFields.getHash().get(filtrationController.getChoiceBox().getValue());
                for (Flat flat : tableView.getItems()) {
                    if (filtrationController.getRadioContains().isSelected() &&
                            String.valueOf(tableColumn.getCellObservableValue(flat).getValue()).contains(filtrationController.getTextField().getText())
                            || filtrationController.getRadioNotContains().isSelected() &&
                            !String.valueOf(tableColumn.getCellObservableValue(flat).getValue()).contains(filtrationController.getTextField().getText())) {
                        eatenItems.add(flat);
                        tableView.getItems().remove(flat);
                    }
                }

            }
        }));


        Scene scene = new Scene(root);
        window.setScene(scene);
        window.setResizable(false);
        window.show();
        window.centerOnScreen();
    }

    public FiltrationWindow(TableView<Flat> tableView, HashFields hashFields) {
        this.tableView = tableView;
        this.hashFields = hashFields;
    }
}
