package windows;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterAlertBoxController {
    public static boolean display() throws IOException {
        Stage window = new Stage();
        window.setTitle("Регистрация");
        window.initModality(Modality.APPLICATION_MODAL);

        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10));
        vBox.setPrefWidth(300);
        vBox.setPrefHeight(200);
        vBox.setAlignment(Pos.CENTER);

        TextField mailField = new TextField();
        mailField.setPromptText("e-mail");

        TextField nameField = new TextField();
        nameField.setPromptText("nickname");

        Label label = new Label("Введите свою почту, куда отправятся логин и пароль");
        FlowPane flowPane = new FlowPane(label);
        Text text = new Text("высеры");
        Button button = new Button("Зарегаться");

        vBox.getChildren().addAll(label, mailField, nameField, text, button);

        Scene scene = new Scene(vBox);
        window.setScene(scene);
        window.setResizable(false);
        window.showAndWait();
        window.centerOnScreen();

        return true;
    }


}
