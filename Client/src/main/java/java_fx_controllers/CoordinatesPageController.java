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
    @FXML
    public Button avatarChangeButton;

    @FXML
    public Label userName;

    @FXML
    public ImageView userLogo;

    @FXML
    public AnchorPane coordinatesPane;

    @FXML
    public Label coordinatesText;

    @FXML
    public AnchorPane mainPane;

    @FXML
    public Group coordinatesGroup;

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

    public void start(Stage stage) {
        BorderPane root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/Windows/coordinatesWindow.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("Coordinates");
        assert root != null; //TODO почитать про ассерты
        stage.setScene(new Scene(root, 1600, 950));
        stage.setMinWidth(1600);
        stage.setMinHeight(960);
        stage.show();
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

    public CoordinatesPageController(ObservableList<Flat> list){
        this.list = list;
        //coordinatesGroup.getChildren().setAll();

        list.addListener((ListChangeListener<? super Flat>) (e)->{
            ArrayList<Circle> arrlist = new ArrayList();
            for (Flat flat :
                    list) {
                Circle circle = new Circle(12, Color.RED);
                if (flat.getCoordinates().getX() <= 650 || flat.getCoordinates().getX() >= -650 ){
                    circle.setCenterX(-flat.getCoordinates().getX());
                } else {
                    if (flat.getCoordinates().getX() < 0) { //тк координаты перевернуты
                        circle.setCenterX(650);
                    } else {
                        circle.setCenterX(-650);
                    }
                }

                if (flat.getCoordinates().getY() <= 320 || flat.getCoordinates().getY() >= -320 ){
                    circle.setCenterY(-flat.getCoordinates().getY());
                } else {
                    if (flat.getCoordinates().getY() < 0) { //тк координаты перевернуты
                        circle.setCenterY(320);
                    } else {
                        circle.setCenterY(-320);
                    }
                }

                if (flat.getHouse().getYear() <= 40) {
                    circle.setRadius(10 + flat.getHouse().getYear());
                } else {
                    circle.setRadius(55);
                }

                ScaleTransition scaleTransition = new ScaleTransition();
                scaleTransition.setDuration(Duration.INDEFINITE);
                scaleTransition.setNode(circle);
                scaleTransition.setByY(1.2);
                scaleTransition.setByX(1.2);
                scaleTransition.setCycleCount(1000000000);
                scaleTransition.setAutoReverse(false);
                scaleTransition.play();

                EventHandler<MouseEvent> eventHandler =
                        new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent e) {

                            }
                        };
                circle.setCursor(Cursor.HAND);
                circle.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
                arrlist.add(circle);
            }
            coordinatesGroup.getChildren().clear();
            for (Circle c :
                    arrlist) {
                coordinatesGroup.getChildren().add(c);
            }
        });
    }

    public CoordinatesPageController() {}
}
