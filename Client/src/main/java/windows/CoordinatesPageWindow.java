package windows;

import fields.Flat;
import java_fx_controllers.CoordinatesPageController;
import javafx.animation.ScaleTransition;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.SneakyThrows;

import javax.swing.text.html.ImageView;
import java.util.*;

public class CoordinatesPageWindow {

    private CoordinatesPageController coordinatesPageController;

    private ObservableList<Flat> list;

    private static HashMap hashMap = new HashMap();

    private static Iterator colorIterator = null;

    private static Set<Color> colors = new HashSet<Color>();
    static {
        colors.add(Color.GREEN);
        colors.add(Color.RED);
        colors.add(Color.GRAY);
        colors.add(Color.BLUE);
        colors.add(Color.BLACK);
        colors.add(Color.ORANGE);
        colors.add(Color.GOLD);
        colorIterator = colors.iterator();
    }




    @SneakyThrows
    public void display(ObservableList<Flat> list, String userName, Image avatarIcon) {
        this.list = list;
        Stage window = new Stage();
        window.setTitle("coords");//todo
        window.initModality(Modality.APPLICATION_MODAL);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Windows/coordinatesWindow.fxml"));
        Parent root = loader.load();

        coordinatesPageController = loader.getController();

        draw(list);
        coordinatesPageController.setName(userName);
        coordinatesPageController.setAvatarIcon(avatarIcon);
        Scene scene = new Scene(root, 1600, 950);
        window.setScene(scene);
        window.setMinWidth(1600);
        window.setMinHeight(960);
        window.showAndWait();
        window.centerOnScreen();

    }

    public void draw(ObservableList<? extends Flat> list) {
        ArrayList<Circle> arrlist = new ArrayList();

        list.forEach((flat) -> {
            Circle circle = null;
            if (hashMap.containsKey(flat.getUser().getUsername())) {
                circle = new Circle(12, (Color) hashMap.get(flat.getUser().getUsername()));
            } else {
                Color c = (Color) colorIterator.next();
                hashMap.put(flat.getUser().getUsername(), c);
                circle = new Circle(12, c);
            }

            if (flat.getCoordinates().getX() <= 650 && flat.getCoordinates().getX() >= -650) {
                circle.setCenterX(flat.getCoordinates().getX());
            } else {
                if (flat.getCoordinates().getX() < -650) { //тк координаты перевернуты
                    circle.setCenterX(-650);
                } else {
                    circle.setCenterX(650);
                }
            }

            if (flat.getCoordinates().getY() <= 300 && flat.getCoordinates().getY() >= -300) {
                circle.setCenterY(-flat.getCoordinates().getY());
            } else {
                if (flat.getCoordinates().getY() < -300) { //тк координаты перевернуты
                    circle.setCenterY(300);
                } else {
                    circle.setCenterY(-300);
                }
            }

            if (flat.getHouse().getYear() <= 40) {
                circle.setRadius(10 + flat.getHouse().getYear());
            } else {
                circle.setRadius(55);
            }



            circle.setStrokeWidth(20);
            ScaleTransition scaleTransition = new ScaleTransition();
            scaleTransition.setDuration(Duration.millis(1000));
            scaleTransition.setNode(circle);
            scaleTransition.setByY(0.3);
            scaleTransition.setByX(0.3);
            scaleTransition.setCycleCount(50);
            scaleTransition.setAutoReverse(true);
            scaleTransition.play();

            circle.setCursor(Cursor.HAND);
            circle.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Элем");
                alert.setHeaderText("Вот вам кликнутый элемент");
                alert.setContentText(flat.printInfoAboutElement());
                alert.showAndWait();
            });
            arrlist.add(circle);
        });
        coordinatesPageController.getCoordinatesGroup().getChildren().clear();
        for (Circle c : arrlist) {
            coordinatesPageController.getCoordinatesGroup().getChildren().add(c);
        }
    }

}
