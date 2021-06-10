package java_fx_controllers;

import client.Invoker;
import fields.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import utils.FlatGetter;
import utils.HashFields;
import utils.MainLocale;
import utils.SerializableAnswerToClient;
import windows.*;
import java.io.*;
import java.util.Date;
import java.util.PriorityQueue;
import java.util.Timer;
import java.util.TimerTask;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;


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

    private String unknownErrText;

    {
        try {
            unknownErrText = new String(MainLocale.getResourceBundle().getString("main_err1").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private Timeline timeline;

    private CoordinatesPageWindow coordinatesPageWindow;

    public void initALlColumns() {
        hashFields = new HashFields();
        TableColumn<Flat, Long> idColumn = new TableColumn<>("id");
        idColumn.setPrefWidth(120);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Flat, SimpleDateFormat> dateColumn = new TableColumn<>("creation date");
        dateColumn.setPrefWidth(120);
        dateColumn.setCellValueFactory(Flat -> {
            SimpleObjectProperty property = new SimpleObjectProperty();
            property.setValue(new SimpleDateFormat("HH:mm:ss.SSS dd-MM-yyyy", Locale.getDefault()).format(Flat.getValue().getCreationDateClear()));
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
        hashFields.registerNewField("View", viewColumn);
        hashFields.registerNewField("Transport", transportColumn);
        hashFields.registerNewField("Coordinates_X", xCoordColumn);
        hashFields.registerNewField("Coordinates_Y", yCoordColumn);
        hashFields.registerNewField("House_Name", nameOfHouseColumn);
        hashFields.registerNewField("House_Year", houseYearColumn);
        hashFields.registerNewField("House_NumberOfFlats", numberOfFlatsOfHouseColumn);

        listOfFlatsForAnim = getList(new PriorityQueue<>());

        textArea.appendText("");
    }

    @SneakyThrows
    public void setTextToColumns() {
        String[] args = new String[1];
        args[0] = "getCollection";
        SerializableAnswerToClient answer = invoker.executeCommand(null, args);
        if (answer != null) {
            table.setItems(getList(answer.getQueue()));
            //listOfFlatsForAnim.retainAll(getList(answer.getQueue()));
            listOfFlatsForAnim.setAll(answer.getQueue());
            if (coordinatesPageWindow != null){
                coordinatesPageWindow.draw(listOfFlatsForAnim);
            }
            //answer.getQueue().stream().filter((e) -> !listOfFlatsForAnim.contains(e)).forEach((e) -> listOfFlatsForAnim.add(e));
        } else {
            textArea.appendText(new String(MainLocale.getResourceBundle().getString("conn_error").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251"));
            textArea.appendText("\n");
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
        timeline.stop();
        javaFXWorker.initializeWindow(javaFXWorker.getWindow(), javaFXWorker.getProgramStarter());
    }

    @SneakyThrows
    public void countLess(MouseEvent mouseEvent) {
        String[] args = new String[2];
        args[0] = "count_less_than_number_of_rooms";
        AlertQuestionOfNumberOfRooms alertQuestionOfNumberOfRooms = new AlertQuestionOfNumberOfRooms();
        Integer number = alertQuestionOfNumberOfRooms.display(new String(MainLocale.getResourceBundle().getString("count_title").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251")); //todo
        if (number != null) {
            args[1] = String.valueOf(number);
            SerializableAnswerToClient answer = invoker.executeCommand(null, args);
            if (answer != null) {
                if (!answer.getAns().equals("-1")) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle(new String(MainLocale.getResourceBundle().getString("count_title2").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251"));//todo
                    alert.setHeaderText(new String(MainLocale.getResourceBundle().getString("count_header").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251"));
                    alert.setContentText(new String(MainLocale.getResourceBundle().getString("count_cont").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251") + answer.getAns());
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle(new String(MainLocale.getResourceBundle().getString("update_error").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251"));//todo
                    alert.setHeaderText(new String(MainLocale.getResourceBundle().getString("count_header_error").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251"));
                    alert.setContentText(new String(MainLocale.getResourceBundle().getString("count_cont_error").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251"));
                    alert.showAndWait();
                }
            } else {
                textArea.appendText(unknownErrText);
                textArea.appendText("\n");
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(new String(MainLocale.getResourceBundle().getString("update_error").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251"));//todo
            alert.setHeaderText(new String(MainLocale.getResourceBundle().getString("cout_comp").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251"));
            alert.setContentText(new String(MainLocale.getResourceBundle().getString("cout_comp2").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251"));
            alert.showAndWait();
        }

    }

    @SneakyThrows
    public void minByCoords(MouseEvent mouseEvent) {
        String[] args = new String[1];
        args[0] = "min_by_coordinates";
        SerializableAnswerToClient answer = invoker.executeCommand(null, args);
        if (answer != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(new String(MainLocale.getResourceBundle().getString("elem_title").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251"));//todo
            alert.setHeaderText(new String(MainLocale.getResourceBundle().getString("min_header").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251"));
            if (answer.getAns() != null) {
                alert.setContentText(answer.getAns());
            } else {
                alert.setContentText(new String(MainLocale.getResourceBundle().getString("min_out").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251"));
            }
            alert.showAndWait();
        } else {
            textArea.appendText(unknownErrText);
            textArea.appendText("\n");
        }
    }

    @SneakyThrows
    public void removeLowerFlat(MouseEvent mouseEvent) {
        String[] args = new String[1];
        args[0] = "remove_lower";
        SerializableAnswerToClient answer = invoker.executeCommand(null, args);
        if (answer != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(new String(MainLocale.getResourceBundle().getString("elem_title").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251"));//todo
            alert.setHeaderText(new String(MainLocale.getResourceBundle().getString("remlo_header").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251"));
            alert.setContentText(answer.getAns());
            if (answer.getQueue() != null) {
                table.getItems().clear();
                table.getItems().addAll(answer.getQueue());
            }
            alert.showAndWait();
        } else {
            textArea.appendText(unknownErrText);
            textArea.appendText("\n");
        }
    }

    @SneakyThrows
    public void getHeadFlat(MouseEvent mouseEvent) {
        String[] args = new String[1];
        args[0] = "head";
        SerializableAnswerToClient answer = invoker.executeCommand(null, args);
        if (answer != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(new String(MainLocale.getResourceBundle().getString("elem_title").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251"));//todo
            alert.setHeaderText(new String(MainLocale.getResourceBundle().getString("head_header").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251"));
            if (answer.getAns() != null) {
                alert.setContentText(answer.getAns());
            } else {
                alert.setContentText(new String(MainLocale.getResourceBundle().getString("head_error").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251"));
            }
            alert.showAndWait();
        } else {
            textArea.appendText(unknownErrText);

            textArea.appendText("\n");
        }
    }

    @SneakyThrows
    public void removeFirstFlat(MouseEvent mouseEvent) {
        String[] args = new String[1];
        args[0] = "remove_first";
        SerializableAnswerToClient answer = invoker.executeCommand(null, args);
        if (answer != null) {
            if (answer.getFlat() != null) {
                Flat flatDelete = table.getItems().stream().filter((e) -> e.getUser().getUsername().equals(answer.getFlat().getUser().getUsername())).findFirst().get();
                table.getItems().remove(flatDelete);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(new String(MainLocale.getResourceBundle().getString("remfi_title").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251"));//todo
                alert.setHeaderText(new String(MainLocale.getResourceBundle().getString("remfi_header").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251"));
                alert.setContentText(new String(MainLocale.getResourceBundle().getString("remfi_content").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251"));
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(new String(MainLocale.getResourceBundle().getString("remfi_error_title").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251"));//todo
                alert.setHeaderText(new String(MainLocale.getResourceBundle().getString("remfi_error_header").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251"));
                alert.setContentText(new String(MainLocale.getResourceBundle().getString("remfi_error_content").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251"));
                alert.showAndWait();
            }
        } else {
            textArea.appendText(unknownErrText);
            textArea.appendText("\n");

        }
    }

    public void executeScripts(MouseEvent mouseEvent) {
        String[] args = new String[2];
        args[0] = "execute_script";
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(javaFXWorker.getWindow());
        args[1] = file.getAbsolutePath();
        invoker.executeCommand(null, args);

    }

    @SneakyThrows
    public void clearFlats(MouseEvent mouseEvent) {
        String[] args = new String[1];
        args[0] = "clear";
        SerializableAnswerToClient answer = invoker.executeCommand(null, args);
        if (answer != null) {
            ObservableList<Flat> list = table.getItems();
            table.getItems().removeAll(list.filtered((e) -> e.getUser().getUsername().equals(userName)));
            if (Integer.parseInt(answer.getAns()) != 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(new String(MainLocale.getResourceBundle().getString("remfi_error_title").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251"));//todo
                alert.setHeaderText(new String(MainLocale.getResourceBundle().getString("clear_header").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251"));
                alert.setContentText(new String(MainLocale.getResourceBundle().getString("clear_content1").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251")
                        + answer.getAns() +
                        new String(MainLocale.getResourceBundle().getString("clear_content2").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251"));
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle(new String(MainLocale.getResourceBundle().getString("remfi_error_title").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251"));//todo
                alert.setHeaderText(new String(MainLocale.getResourceBundle().getString("clear_error_header").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251"));
                alert.setContentText(new String(MainLocale.getResourceBundle().getString("clear_error_content").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251"));
                alert.showAndWait();
            }
        } else {
          textArea.appendText(unknownErrText);
          textArea.appendText("\n");
        }
    }

    @SneakyThrows
    public void removeFlat(MouseEvent mouseEvent) {
        String[] args = new String[2];
        args[0] = "remove_by_id";
        Flat selectedFlat = table.getSelectionModel().getSelectedItem();
        if (selectedFlat == null) {
            textArea.appendText(new String(MainLocale.getResourceBundle().getString("alert_no_choice").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251"));
            textArea.appendText("\n");
        } else {
            args[1] = String.valueOf(selectedFlat.getId());
            SerializableAnswerToClient answer = invoker.executeCommand(null, args);
            if (answer != null) {
                if (answer.getFlat() != null) {
                    table.getItems().remove(selectedFlat);
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle(new String(MainLocale.getResourceBundle().getString("update_error").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251"));//todo
                    alert.setHeaderText(new String(MainLocale.getResourceBundle().getString("rem_error_header").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251"));
                    alert.setContentText(new String(MainLocale.getResourceBundle().getString("rem_error_content").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251"));
                    alert.showAndWait();
                }
            } else {
                textArea.appendText(unknownErrText);
                textArea.appendText("\n");
            }
        }

    }

    @SneakyThrows
    public void updateFlat(MouseEvent mouseEvent) {
        String[] args = new String[2];
        args[0] = "update";
        Flat selectedFlat = table.getSelectionModel().getSelectedItem();
        FlatGetter.setFlat(selectedFlat);
        if (selectedFlat == null) {
            textArea.appendText(new String(MainLocale.getResourceBundle().getString("alert_no_choice").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251"));
            textArea.appendText("\n");
        } else {
            args[1] = String.valueOf(selectedFlat.getId());
            SerializableAnswerToClient answer = invoker.executeCommand(null, args);
            if (answer != null) {
                if (answer.getFlat() != null) {
                    table.getItems().remove(selectedFlat);
                    table.getItems().add(answer.getFlat());
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle(new String(MainLocale.getResourceBundle().getString("update_error").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251"));//todo
                    alert.setHeaderText(new String(MainLocale.getResourceBundle().getString("update_header_error").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251"));
                    alert.setContentText(new String(MainLocale.getResourceBundle().getString("update_context_error").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251"));
                    alert.showAndWait();
                }
            } else {
              textArea.appendText(unknownErrText);
              textArea.appendText("\n");

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
            textArea.appendText(unknownErrText);
            textArea.appendText("\n");
        }

    }

    @SneakyThrows
    public void getInfo(MouseEvent mouseEvent) {
        String[] args = new String[1];
        args[0] = "info";
        SerializableAnswerToClient answer = invoker.executeCommand(null, args);
        if (answer != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(new String(MainLocale.getResourceBundle().getString("info_title").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251"));//todo
            alert.setHeaderText(new String(MainLocale.getResourceBundle().getString("info_text").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251"));
            alert.setContentText(answer.getAns());
            alert.showAndWait();
        } else {

            textArea.appendText(unknownErrText);
            textArea.appendText("\n");
        }

    }

    public void getHelp(MouseEvent mouseEvent) {
        String[] args = new String[1];
        args[0] = "help";
        SerializableAnswerToClient answer = invoker.executeCommand(null, args);
        if (answer != null) {
            textArea.appendText(answer.getAns());
            textArea.setScrollTop(Double.MAX_VALUE);
        }else {
            textArea.appendText(unknownErrText);
        }

        textArea.appendText("\n");
    }

    public void printFields(MouseEvent mouseEvent) {
        String[] args = new String[1];
        args[0] = "print_field_descending_number_of_rooms";
        SerializableAnswerToClient answer = invoker.executeCommand(null, args);
        if (answer != null) {
            textArea.appendText(answer.getAns());
        } else {
            textArea.appendText(unknownErrText);

        }
        textArea.appendText("\n");
    }

    public void openFilters(MouseEvent mouseEvent) {
        FiltrationWindow window = new FiltrationWindow(table, hashFields);
        window.display();
    }

    @SneakyThrows
    public void openSettingsWindow(MouseEvent mouseEvent) {
        SettingWindow settingWindow = new SettingWindow();
        settingWindow.display();
        javaFXWorker.setMainWindow();
    }

    public void openCoordinatesWindow(MouseEvent mouseEvent) {
        timeline.close();
        coordinatesPageWindow = new CoordinatesPageWindow();
        coordinatesPageWindow.display(listOfFlatsForAnim, userName, avatarIcon.getImage());
    }

    @FXML
    public void initialize() {
        textArea.textProperty().addListener((e) -> {
            textArea.setScrollTop(Double.MIN_VALUE);
        });
        getterOfCollection();
    }

    private void getterOfCollection() {
        /*timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

            }
        }, 5000, 5000);*/
        timeline = new Timeline(new KeyFrame(Duration.seconds(5), ev -> {
                setTextToColumns();
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void setAvatarIcon(MouseEvent mouseEvent) {
        FileChooser chooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg", "*.png", "*.bmp");
        chooser.getExtensionFilters().add(extFilter);
        Stage stage1 = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        File file = chooser.showOpenDialog(stage1);
        ImageView logoImage = this.getImage(file);
        if (file != null) {
            avatarIcon.setImage(logoImage.getImage());
        }
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

