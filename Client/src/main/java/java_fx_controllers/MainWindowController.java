package java_fx_controllers;

import fields.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import lombok.Getter;
import lombok.Setter;

import java.util.Comparator;
import java.util.Date;
import java.util.PriorityQueue;


public class MainWindowController {
    @FXML
    @Getter
    @Setter
    private ImageView avatarIcon;

    @FXML
    @Getter
    @Setter
    private TableView<Flat> table;

    public void initALlColumns() {
        TableColumn<Flat, Long> idColumn = new TableColumn<>("id");
        idColumn.setPrefWidth(120);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Flat, Date> dateColumn = new TableColumn<>("creation date");
        dateColumn.setPrefWidth(120);
        dateColumn.setCellValueFactory(Flat -> {
            SimpleObjectProperty property = new SimpleObjectProperty();
            property.setValue(Flat.getValue().getCreationDate());
            return property;
        });
        TableColumn<Flat, String> nameColumn = new TableColumn<>("name");
        nameColumn.setPrefWidth(120);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Flat, Long> areaColumn = new TableColumn<>("area");
        areaColumn.setPrefWidth(120);
        areaColumn.setCellValueFactory(new PropertyValueFactory<>("area"));
        TableColumn<Flat, Integer> numberOfRoomsColumn = new TableColumn<>("number of rooms");
        numberOfRoomsColumn.setPrefWidth(120);
        numberOfRoomsColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfRooms"));
        TableColumn<Flat, Furnish> furnishColumn = new TableColumn<>("furnish");
        furnishColumn.setPrefWidth(125);
        furnishColumn.setCellValueFactory(new PropertyValueFactory<>("furnish"));
        TableColumn<Flat, View> viewColumn = new TableColumn<>("view");
        viewColumn.setPrefWidth(125);
        viewColumn.setCellValueFactory(new PropertyValueFactory<>("view"));
        TableColumn<Flat, Transport> transportColumn = new TableColumn<>("transport");
        transportColumn.setPrefWidth(125);
        transportColumn.setCellValueFactory(new PropertyValueFactory<>("transport"));

        TableColumn<Flat, String> userNameColumn = new TableColumn<>("user name");
        userNameColumn.setPrefWidth(133);
        userNameColumn.setCellValueFactory(Flat -> {
            SimpleObjectProperty property = new SimpleObjectProperty();
            property.setValue(Flat.getValue().getUser().getUsername());
            return property;
        });

        TableColumn<Flat, House> houseColumn = new TableColumn<>("house");
        TableColumn<Flat, String> nameOfHouseColumn = new TableColumn<>("name");
        nameOfHouseColumn.setPrefWidth(90);
        nameOfHouseColumn.setCellValueFactory(Flat -> {
            SimpleObjectProperty property = new SimpleObjectProperty();
            property.setValue(Flat.getValue().getHouse().getName());
            return property;
        });
        TableColumn<Flat, Long> houseYearColumn = new TableColumn<>("house");
        houseYearColumn.setPrefWidth(100);
        houseYearColumn.setCellValueFactory(Flat -> {
            SimpleObjectProperty property = new SimpleObjectProperty();
            property.setValue(Flat.getValue().getHouse().getYear());
            return property;
        });
        TableColumn<Flat, Long> numberOfFlatsOfHouseColumn = new TableColumn<>("number of flats");
        numberOfFlatsOfHouseColumn.setPrefWidth(100);
        numberOfFlatsOfHouseColumn.setCellValueFactory(Flat -> {
            SimpleObjectProperty property = new SimpleObjectProperty();
            property.setValue(Flat.getValue().getHouse().getNumberOfFlatsOnFloor());
            return property;
        });
        houseColumn.getColumns().addAll(nameOfHouseColumn, houseYearColumn, numberOfFlatsOfHouseColumn);


        TableColumn<Flat, Coordinates> coordinatesColumn = new TableColumn<>("coordinates");
        TableColumn<Flat, Integer> xCoordColumn = new TableColumn<>("x");
        xCoordColumn.setPrefWidth(100);
        xCoordColumn.setCellValueFactory(Flat -> {
            SimpleObjectProperty property = new SimpleObjectProperty();
            property.setValue(Flat.getValue().getCoordinates().getX());
            return property;
        });
        TableColumn<Flat, Float> yCoordColumn = new TableColumn<>("y");
        yCoordColumn.setPrefWidth(100);
        yCoordColumn.setCellValueFactory(Flat -> {
            SimpleObjectProperty property = new SimpleObjectProperty();
            property.setValue(Flat.getValue().getCoordinates().getY());
            return property;
        });
        coordinatesColumn.getColumns().addAll(xCoordColumn, yCoordColumn);

        table.getColumns().addAll(
                userNameColumn, idColumn, dateColumn, nameColumn, areaColumn, numberOfRoomsColumn,
                furnishColumn, viewColumn, transportColumn, houseColumn, coordinatesColumn
        );

        //todo clearConstrs
        Flat flat1 = new Flat(
                new User("login", "pwd"), "name1", new Coordinates(10, 20f),
                new Date(), 23L, 15, Furnish.BAD, View.NORMAL, Transport.NORMAL, new House("flats", 1L, 2L));
        flat1.setId(102L);
        Flat flat2 = new Flat(
                new User("login", "pwd"), "name2", new Coordinates(10, 20f),
                new Date(), 23L, 15, Furnish.BAD, View.NORMAL, Transport.NORMAL, new House("flats", 1L, 2L));
        flat2.setId(103L);
        PriorityQueue<Flat> priorityQueue = new PriorityQueue<>(new Comparator<Flat>() {
            @Override
            public int compare(Flat o1, Flat o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        priorityQueue.add(flat1);
        priorityQueue.add(flat2);
        table.setItems(getList(priorityQueue));

    }

    public void setTextToColumns() {

    }

    public ObservableList<Flat> getList(PriorityQueue<Flat> flats) {
        return FXCollections.observableArrayList(flats);
    }
}
