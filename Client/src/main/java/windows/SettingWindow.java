package windows;

import java_fx_controllers.SettingsController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import utils.MainLocale;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class SettingWindow {
    private Stage stage;

    @SneakyThrows
    public void display() {
        stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Windows/settingsWindow.fxml"));
        BorderPane root = loader.load();
        SettingsController settingsController = loader.getController();
        settingsController.getLanguageMenuButton().getItems().forEach((item) -> item.setOnAction((e) -> {
                    try {
                        stage.setTitle(new String(MainLocale.getResourceBundle().getString("setting_title").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251"));
                        settingsController.getLanguageMenuButton().setText(new String(MainLocale.getResourceBundle().getString("setting_language").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251"));
                        settingsController.getSettings().setText(new String(MainLocale.getResourceBundle().getString("setting_language").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251"));
                    } catch (UnsupportedEncodingException unsupportedEncodingException) {
                        unsupportedEncodingException.printStackTrace();
                    }
                })
        );
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(new String(MainLocale.getResourceBundle().getString("setting_title").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251"));
        stage.setScene(new Scene(root, 450, 215));
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        stage.centerOnScreen();
    }
}
