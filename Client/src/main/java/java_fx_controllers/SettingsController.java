package java_fx_controllers;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import utils.MainLocale;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

public class SettingsController{

    @FXML
    private BorderPane logInBorderPane;

    @FXML
    private AnchorPane settingsPane;

    @FXML @Getter @Setter
    private Label settings;

    @FXML @Getter @Setter
    private MenuButton languageMenuButton;

    @FXML
    private MenuItem englishMenuItem;

    @FXML
    private MenuItem russianMenuItem;

    @FXML
    private MenuItem latvianMenuItem;

    @FXML
    private MenuItem portugesMenuItem;

    @SneakyThrows
    @FXML
    void initialize() {
        languageMenuButton.setText(new String(MainLocale.getResourceBundle().getString("setting_language").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251"));
        settings.setText(new String(MainLocale.getResourceBundle().getString("setting_language").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251"));

        ImageView langGlobe = this.getIcon("/Pics/flags/globe.png");
        ImageView langEn = this.getIcon("/Pics/flags/united_kingdom_round_icon_256.png");
        ImageView langRu = this.getIcon("/Pics/flags/russia_round_icon_256.png");
        ImageView langLa = this.getIcon("/Pics/flags/latvia_round_icon_256.png");
        ImageView langPo = this.getIcon("/Pics/flags/portugal_round_icon_256.png");

        langGlobe.setFitHeight(20.0);
        langEn.setFitHeight(20.0);
        langRu.setFitHeight(20.0);
        langLa.setFitHeight(20.0);
        langPo.setFitHeight(20.0);
        langGlobe.setFitWidth(20.0);
        langEn.setFitWidth(25.0);
        langRu.setFitWidth(25.0);
        langLa.setFitWidth(25.0);
        langPo.setFitWidth(25.0);

        languageMenuButton.setGraphic(langGlobe);
        englishMenuItem.setGraphic(langEn);
        russianMenuItem.setGraphic(langRu);
        latvianMenuItem.setGraphic(langLa);
        portugesMenuItem.setGraphic(langPo);


    }

    private ImageView getIcon(String resourcePath) {
        InputStream input = this.getClass().getResourceAsStream(resourcePath);
        Image image = new Image(input);
        return new ImageView(image);
    }
}