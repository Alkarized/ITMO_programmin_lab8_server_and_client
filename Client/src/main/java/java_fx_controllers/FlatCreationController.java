package java_fx_controllers;

import fields.Flat;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

public class FlatCreationController {

    @FXML @Getter
    private Button sendButton;
    @FXML @Getter
    private TextField coords_y;
    @FXML @Getter
    private TextField coords_x;
    @FXML @Getter
    private TextField house_number_of_flats;
    @FXML @Getter
    private TextField house_year;
    @FXML @Getter
    private TextField house_name;
    @FXML @Getter
    private ChoiceBox view;
    @FXML @Getter
    private ChoiceBox furnish;
    @FXML @Getter
    private ChoiceBox transport;
    @FXML @Getter
    private TextField numberOfRooms;
    @FXML @Getter
    private TextField area;
    @FXML @Getter
    private TextField name;

    @FXML @Getter
    private Text topic;

    @Getter @Setter
    private Stage stage;

    public FlatCreationController() {
    }

    public void setData(Flat flat){
        coords_x.setText(String.valueOf(flat.getCoordinates().getX()));
        coords_y.setText(String.valueOf(flat.getCoordinates().getY()));
        house_number_of_flats.setText(String.valueOf(flat.getHouse().getNumberOfFlatsOnFloor()));
        house_name.setText(String.valueOf(flat.getHouse().getName()));
        house_year.setText(String.valueOf(flat.getHouse().getYear()));
        view.setValue(flat.getView().name());
        furnish.setValue(flat.getFurnish().name());
        transport.setValue(flat.getTransport().name());
        area.setText(String.valueOf(flat.getArea()));
        name.setText(String.valueOf(flat.getName()));
        numberOfRooms.setText(String.valueOf(flat.getNumberOfRooms()));
    }

    public void closeWindow(MouseEvent mouseEvent) {
        stage.close();
    }
}
