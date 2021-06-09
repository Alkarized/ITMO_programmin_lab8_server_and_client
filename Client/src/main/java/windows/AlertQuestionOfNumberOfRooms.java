package windows;

import java_fx_controllers.AlertQuestionController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.SneakyThrows;

public class AlertQuestionOfNumberOfRooms {
    private AlertQuestionController alertQuestionController;

    @SneakyThrows
    public Integer display(String title) {
        Stage window = new Stage();
        window.setTitle(title); //todo
        window.initModality(Modality.APPLICATION_MODAL);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Windows/alertQuestion.fxml"));
        Parent root = loader.load();
        alertQuestionController = loader.getController();
        alertQuestionController.localize();
        alertQuestionController.setStage(window);

        Scene scene = new Scene(root);
        window.setScene(scene);
        window.setResizable(false);
        window.showAndWait();
        window.centerOnScreen();

        return getIntValue();
    }

    private Integer getIntValue() {
        Integer ans = null;
        try {
            ans = Integer.parseInt(alertQuestionController.getFiled().getText());
        } catch (Exception ignored) {

        }
        return ans;
    }
}
