package animations;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.util.Duration;

public class Flicker {
    private FadeTransition ft;

    public Flicker(Node node) {
        ft = new FadeTransition(Duration.millis(2000), node);
        ft.setFromValue(1);
        ft.setToValue(0.1);
        ft.setCycleCount(Timeline.INDEFINITE);
        ft.setAutoReverse(true);
    }

    public void flicking() {
        ft.play();
    }
}
