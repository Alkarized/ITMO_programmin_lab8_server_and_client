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
import utils.MainLocale;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
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
    private boolean isClickedClear = false;
    @SneakyThrows
    public void display() {
        Stage window = new Stage();
        window.setTitle(new String(MainLocale.getResourceBundle().getString("filter_title").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251")); //todo
        window.initModality(Modality.APPLICATION_MODAL);

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
            if (!isClickedClear){
                tableView.getItems().addAll(eatenItems);
                eatenItems.clear();
                isClickedClear = true;
            }
        });
        setText();
        filtrationController.getUseFilter().setOnMouseClicked((event -> {
            if (filtrationController.getChoiceBox().getValue() == null) {
                try {
                    filtrationController.getErrorText().setText(new String(MainLocale.getResourceBundle().getString("filter_err_1").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251"));//todo
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Shake shake = new Shake(filtrationController.getErrorText());
                shake.shaking();
            } else {
                tableView.getItems().addAll(eatenItems);
                eatenItems.clear();
                TableColumn<Flat, ?> tableColumn = hashFields.getHash().get(filtrationController.getChoiceBox().getValue());
                tableView.getItems().forEach((flat)->{
                    if ((filtrationController.getRadioContains().isSelected() &&
                            !String.valueOf(tableColumn.getCellObservableValue(flat).getValue()).contains(filtrationController.getTextField().getText()))
                            || (filtrationController.getRadioNotContains().isSelected() &&
                            String.valueOf(tableColumn.getCellObservableValue(flat).getValue()).contains(filtrationController.getTextField().getText()))) {
                        eatenItems.add(flat);

                        isClickedClear = false;
                    }
                });
                tableView.getItems().removeAll(eatenItems);
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

    @SneakyThrows
    public void setText(){
        filtrationController.getValue().setText(new String(MainLocale.getResourceBundle().getString("filter_value").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251"));
        filtrationController.getRadioContains().setText(new String(MainLocale.getResourceBundle().getString("filter_cont").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251"));
        filtrationController.getRadioNotContains().setText(new String(MainLocale.getResourceBundle().getString("filter_not_cont").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251"));
        filtrationController.getClearFilter().setText(new String(MainLocale.getResourceBundle().getString("filter_clear_button").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251"));
        filtrationController.getUseFilter().setText(new String(MainLocale.getResourceBundle().getString("filter_use_button").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251"));
        filtrationController.getTextFieldo().setText(new String(MainLocale.getResourceBundle().getString("filter_text").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251"));
    }
}
