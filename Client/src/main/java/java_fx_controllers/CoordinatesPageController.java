package java_fx_controllers;

import fields.Flat;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;
import windows.JavaFXWorker;

import java.io.*;
import java.util.ArrayList;
import java.util.Observable;


public class CoordinatesPageController {
    @FXML @Getter @Setter
    private Button avatarChangeButton;

    @FXML @Getter @Setter
    private Label userName;

    @FXML @Getter @Setter
    private ImageView userLogo;

    @FXML @Getter @Setter
    private AnchorPane coordinatesPane;

    @FXML @Getter @Setter
    private Label coordinatesText;

    @FXML @Getter @Setter
    private AnchorPane mainPane;

    @FXML @Getter @Setter
    private Group coordinatesGroup;

    @Getter @Setter
    private static ObservableList<Flat> list;

    @FXML
    void initialize() {

        coordinatesPane.setMaxSize(1501, 726);

        avatarChangeButton.setOnAction(event -> {
            FileChooser chooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg", "*.png", "*.bmp");
            chooser.getExtensionFilters().add(extFilter);
            Stage stage1 = (Stage) ((Node) event.getSource()).getScene().getWindow();
            File file = chooser.showOpenDialog(stage1);
            ImageView logoImage = this.getImage(file);
            if (file != null) {
                userLogo.setImage(logoImage.getImage());
            }
        });
    }

    private ImageView getImage(File file) {
        InputStream input = null;
        Image image = null;
        try {
            input = new FileInputStream(file);
            image = new Image(input);
        } catch (FileNotFoundException | NullPointerException e) {}
        return new ImageView(image);
    }

}
