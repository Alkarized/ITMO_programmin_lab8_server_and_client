package windows;

import fields.*;
import java_fx_controllers.FlatCreationController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import utils.MainLocale;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FlatCreationWindow {

    private FlatCreationController flatCreationController;

    public Flat displayBox(String title, String topic, Flat flat) throws IOException {
        Stage window = new Stage();
        window.setTitle(title);//todo
        window.initModality(Modality.APPLICATION_MODAL);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Windows/flatCreationWindow.fxml"));
        Parent root = loader.load();
        flatCreationController = loader.getController();

        flatCreationController.getTopic().setText(topic); //todo
        flatCreationController.getSendButton().setText(new String(MainLocale.getResourceBundle().getString("flat_send").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251")
        );
        flatCreationController.setStage(window);

        if (flat != null){
            flatCreationController.setData(flat);
        }

        Scene scene = new Scene(root);
        window.setScene(scene);
        window.setResizable(false);
        window.showAndWait();
        window.centerOnScreen();
        flatCreationController.getSendButton().setOnAction((e) -> System.out.println("test"));
        return tryToCreateFlat();
    }

    public Flat tryToCreateFlat() {
        Flat flat = new Flat();
        House house = new House();
        Coordinates coordinates = new Coordinates();
        try {
            if (flat.setView(View.valueOf(String.valueOf(flatCreationController.getView().getValue()))) &&
                    flat.setTransport(Transport.valueOf(String.valueOf(flatCreationController.getTransport().getValue()))) &&
                    flat.setFurnish(Furnish.valueOf(String.valueOf(flatCreationController.getFurnish().getValue()))) &&
                    flat.setName(String.valueOf(flatCreationController.getName().getText())) &&
                    flat.setArea(Long.valueOf(flatCreationController.getArea().getText())) &&
                    flat.setNumberOfRooms(Integer.valueOf(flatCreationController.getNumberOfRooms().getText())) &&
                    house.setYear(Long.valueOf(flatCreationController.getHouse_year().getText())) &&
                    house.setName(flatCreationController.getName().getText()) &&
                    house.setNumberOfFlatsOnFloor(Long.valueOf(flatCreationController.getHouse_number_of_flats().getText())) &&
                    coordinates.setX(Integer.parseInt(flatCreationController.getCoords_x().getText())) &&
                    coordinates.setY(Float.valueOf(flatCreationController.getCoords_y().getText()))) {
                flat.setHouse(house);
                flat.setCoordinates(coordinates);
            } else {
                return null;
            }
            return flat;
        } catch (Exception e) {
            return null;
        }
    }

}
