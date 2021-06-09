package java_fx_controllers;

import client.Invoker;
import fields.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import utils.FlatGetter;
import utils.HashFields;
import utils.SerializableAnswerToClient;
import windows.AlertQuestionOfNumberOfRooms;
import windows.FiltrationWindow;
import windows.JavaFXWorker;

import java.io.IOException;
import java.util.Date;
import java.util.PriorityQueue;


public class MainWindowController {

    @FXML
    public Button coordinatesButton;

    private HashFields hashFields;
    @FXML
    @Getter
    @Setter
    private Button filtration;
    @FXML
    @Getter
    @Setter
    private Text userNameText;

    @FXML
    @Getter
    @Setter
    private TextArea textArea;

    @FXML
    @Getter
    @Setter
    private ImageView avatarIcon;

    @FXML
    @Getter
    @Setter
    private TableView<Flat> table;

    @Getter
    @Setter
    private Invoker invoker;

    @Getter
    @Setter
    private JavaFXWorker javaFXWorker;

    @Setter
    private String userName;

    @FXML
    private Button settingsButton;

    private ObservableList<Flat> listOfFlatsForAnim;

    private final String unknownErrText = "Ошибка получения данных, советую обратиться к админам сервера, их контактных данных я не дам, ищите сами, кехф";

    public void initALlColumns() {
        hashFields = new HashFields();
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
        TableColumn<Flat, Long> houseYearColumn = new TableColumn<>("year");
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
        /*Flat flat1 = new Flat(
                new User("1", "1"), "name1", new Coordinates(10, 20f),
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
        table.setItems(getList(priorityQueue));*/

        hashFields.registerNewField("ID", idColumn);
        hashFields.registerNewField("Username", userNameColumn);
        hashFields.registerNewField("Creation Date", dateColumn);
        hashFields.registerNewField("Name", nameColumn);
        hashFields.registerNewField("Area", areaColumn);
        hashFields.registerNewField("Number Of Rooms", numberOfRoomsColumn);
        hashFields.registerNewField("Furnish", furnishColumn);
        hashFields.registerNewField("View",viewColumn);
        hashFields.registerNewField("Transport",transportColumn);
        hashFields.registerNewField("Coordinates_X", xCoordColumn);
        hashFields.registerNewField("Coordinates_Y", yCoordColumn);
        hashFields.registerNewField("House_Name", nameOfHouseColumn);
        hashFields.registerNewField("House_Year", houseYearColumn);
        hashFields.registerNewField("House_NumberOfFlats", numberOfFlatsOfHouseColumn);

        listOfFlatsForAnim = getList(new PriorityQueue<>());
    }

    public void setTextToColumns() {
        String[] args = new String[1];
        args[0] = "getCollection";
        SerializableAnswerToClient answer = invoker.executeCommand(null, args);
        if (answer != null) {
            table.setItems(getList(answer.getQueue()));
            listOfFlatsForAnim.retainAll(getList(answer.getQueue()));
            answer.getQueue().stream().filter((e)->!listOfFlatsForAnim.contains(e)).forEach((e)->listOfFlatsForAnim.add(e));
        } else {
            textArea.setText("Ощабка подключения");
        }

    }

    public void setName(String user_name) {
        this.userName = new String(user_name);
        if (user_name.length() > 15) {
            userNameText.setText(user_name.substring(0, 12) + "...");
        } else {
            userNameText.setText(user_name);
        }
    }

    public ObservableList<Flat> getList(PriorityQueue<Flat> flats) {
        return FXCollections.observableArrayList(flats);
    }

    public void exit(MouseEvent mouseEvent) throws IOException {
        javaFXWorker.initializeWindow(javaFXWorker.getWindow(), javaFXWorker.getProgramStarter());
    }

    public void countLess(MouseEvent mouseEvent) {
        String[] args = new String[2];
        args[0] = "count_less_than_number_of_rooms";
        AlertQuestionOfNumberOfRooms alertQuestionOfNumberOfRooms = new AlertQuestionOfNumberOfRooms();
        Integer number = alertQuestionOfNumberOfRooms.display("Получение значения"); //todo
        if (number != null) {
            args[1] = String.valueOf(number);
            SerializableAnswerToClient answer = invoker.executeCommand(null, args);
            if (answer != null) {
                if (!answer.getAns().equals("-1")) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Подсчит");//todo
                    alert.setHeaderText("Подсчит элементов, где поле меньше заданного");
                    alert.setContentText("Кол-во таких элемоентов: " + answer.getAns());
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Ошябка");//todo
                    alert.setHeaderText("Ощябка кол-ва элементов");
                    alert.setContentText("В коллекции нет элементов");
                    alert.showAndWait();
                }
            } else {
                textArea.setText(unknownErrText);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошябка");//todo
            alert.setHeaderText("Введение поля для сравнения");
            alert.setContentText("введено не число");
            alert.showAndWait();
        }

    }

    public void minByCoords(MouseEvent mouseEvent) {
        String[] args = new String[1];
        args[0] = "min_by_coordinates";
        SerializableAnswerToClient answer = invoker.executeCommand(null, args);
        if (answer != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Элемент коллекции: ");//todo
            alert.setHeaderText("Вот вам и элемент c минимальными координатами)");
            if (answer.getAns() != null) {
                alert.setContentText(answer.getAns());
            } else {
                alert.setContentText("А элементов нет, какой вам элемент!?");
            }
            alert.showAndWait();
        } else {
            textArea.setText(unknownErrText);
        }
    }

    public void removeLowerFlat(MouseEvent mouseEvent) {
        String[] args = new String[1];
        args[0] = "remove_lower";
        SerializableAnswerToClient answer = invoker.executeCommand(null, args);
        if (answer != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Элемент коллекции: ");//todo
            alert.setHeaderText("Удалить или не удалить вот в чем ворос?");
            alert.setContentText(answer.getAns());
            if (answer.getQueue() != null) {
                table.getItems().clear();
                table.getItems().addAll(answer.getQueue());
            }
            alert.showAndWait();
        } else {
            textArea.setText(unknownErrText);
        }
    }

    public void getHeadFlat(MouseEvent mouseEvent) {
        String[] args = new String[1];
        args[0] = "head";
        SerializableAnswerToClient answer = invoker.executeCommand(null, args);
        if (answer != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Элемент коллекции: ");//todo
            alert.setHeaderText("Вот вам и элемент)");
            if (answer.getAns() != null) {
                alert.setContentText(answer.getAns());
            } else {
                alert.setContentText("А элементов нет, какой вам элемент!?");
            }
            alert.showAndWait();
        } else {
            textArea.setText(unknownErrText);
        }
    }

    public void removeFirstFlat(MouseEvent mouseEvent) {
        String[] args = new String[1];
        args[0] = "remove_first";
        SerializableAnswerToClient answer = invoker.executeCommand(null, args);
        if (answer != null) {
            if (answer.getFlat() != null) {
                Flat flatDelete = table.getItems().stream().filter((e) -> e.getUser().getUsername().equals(answer.getFlat().getUser().getUsername())).findFirst().get();
                table.getItems().remove(flatDelete);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Удоление эдема");//todo
                alert.setHeaderText("Удаление превого объекта коллекции");
                alert.setContentText("Удаление прошло успешно");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Очистка");//todo
                alert.setHeaderText("Ощяьбка удаления первого объекта пользователя");
                alert.setContentText("объект не подвластен вам");
                alert.showAndWait();
            }
        } else {
            textArea.setText(unknownErrText);
        }
    }

    public void executeScripts(MouseEvent mouseEvent) {
    }

    public void clearFlats(MouseEvent mouseEvent) {
        String[] args = new String[1];
        args[0] = "clear";
        SerializableAnswerToClient answer = invoker.executeCommand(null, args);
        if (answer != null) {
            ObservableList<Flat> list = table.getItems();
            table.getItems().removeAll(list.filtered((e) -> e.getUser().getUsername().equals(userName)));
            if (Integer.parseInt(answer.getAns()) != 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Очистка");//todo
                alert.setHeaderText("Удаление объектов пользователя");
                alert.setContentText("Удаление прошло успешно: было удалено - " + answer.getAns() + " элементов коллекции");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Очистка");//todo
                alert.setHeaderText("Ощяьбка удаления объектов пользователя");
                alert.setContentText("Нет объектов для удаленияяяяя");
                alert.showAndWait();
            }
        } else {
            textArea.setText(unknownErrText);
        }
    }

    public void removeFlat(MouseEvent mouseEvent) {
        String[] args = new String[2];
        args[0] = "remove_by_id";
        Flat selectedFlat = table.getSelectionModel().getSelectedItem();
        if (selectedFlat == null) {
            textArea.setText("Выбора нет!");
        } else {
            args[1] = String.valueOf(selectedFlat.getId());
            SerializableAnswerToClient answer = invoker.executeCommand(null, args);
            if (answer != null) {
                if (answer.getFlat() != null) {
                    table.getItems().remove(selectedFlat);
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Ошябка");//todo
                    alert.setHeaderText("Ощябка доступа пользователя");
                    alert.setContentText("Вы не могете удалять этот обект");
                    alert.showAndWait();
                }
            } else {
                textArea.setText(unknownErrText);
            }
        }

    }

    public void updateFlat(MouseEvent mouseEvent) {
        String[] args = new String[2];
        args[0] = "update";
        Flat selectedFlat = table.getSelectionModel().getSelectedItem();
        FlatGetter.setFlat(selectedFlat);
        if (selectedFlat == null) {
            textArea.setText("Выбора нет!");
        } else {
            args[1] = String.valueOf(selectedFlat.getId());
            SerializableAnswerToClient answer = invoker.executeCommand(null, args);
            if (answer != null) {
                if (answer.getFlat() != null) {
                    table.getItems().remove(selectedFlat);
                    table.getItems().add(answer.getFlat());
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Ошябка");//todo
                    alert.setHeaderText("Ощябка доступа пользователя");
                    alert.setContentText("Вы не могете обновлять этот обект");
                    alert.showAndWait();
                }
            } else {
                textArea.setText(unknownErrText);
            }
        }
    }

    public void addNewFlat(MouseEvent mouseEvent) {
        String[] args = new String[1];
        args[0] = "add";
        SerializableAnswerToClient answer = invoker.executeCommand(null, args);
        if (answer != null) {
            table.getItems().add(answer.getFlat());
        } else {
            textArea.setText(unknownErrText);
        }

    }

    public void getInfo(MouseEvent mouseEvent) {
        String[] args = new String[1];
        args[0] = "info";
        SerializableAnswerToClient answer = invoker.executeCommand(null, args);
        if (answer != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Информация");//todo
            alert.setHeaderText("Инфа о коллекции");
            alert.setContentText(answer.getAns());
            alert.showAndWait();
        } else {
            textArea.setText(unknownErrText);
        }

    }

    public void getHelp(MouseEvent mouseEvent) {
        String[] args = new String[1];
        args[0] = "help";
        SerializableAnswerToClient answer = invoker.executeCommand(null, args);
        if (answer != null) {
            textArea.setText(answer.getAns());
            textArea.setScrollTop(Double.MIN_VALUE);
            textArea.appendText("");
        }
        if (answer == null) {
            textArea.setText(unknownErrText);
        }
    }

    public void printFields(MouseEvent mouseEvent) {
        String[] args = new String[1];
        args[0] = "print_field_descending_number_of_rooms";
        SerializableAnswerToClient answer = invoker.executeCommand(null, args);
        if (answer != null) {
            textArea.setText(answer.getAns());
        } else {
            textArea.setText(unknownErrText);
        }
    }

    public void openFilters(MouseEvent mouseEvent) {
        FiltrationWindow window = new FiltrationWindow(table, hashFields);
        window.display();
    }

    public void openSettingsWindow(MouseEvent mouseEvent) {
        SettingsController settingsPage = new SettingsController();
        settingsPage.start(new Stage());
    }

    public void openCoordinatesWindow(MouseEvent mouseEvent) {
        CoordinatesPageController coordinatesPageStage = new CoordinatesPageController(listOfFlatsForAnim);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        coordinatesPageStage.start(stage);
    }

    public void start() {
        try {
            javaFXWorker.setMainWindow();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
