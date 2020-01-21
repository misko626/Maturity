package JavaFX;

import animatefx.animation.Pulse;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class NewScene {

    public static NewScene i = new NewScene();

    public void openNewScene2(String fxml) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxml));
        Scene scene = new Scene(root);
        Stage oldStage = new Stage();
        oldStage.initStyle(StageStyle.TRANSPARENT);
        oldStage.setScene(scene);
        oldStage.show();
        scene.setFill(Color.TRANSPARENT);
        final double[] xOffset = new double[1];
        final double[] yOffset = new double[1];
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset[0] = event.getSceneX();
                yOffset[0] = event.getSceneY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                oldStage.setX(event.getScreenX() - xOffset[0]);
                oldStage.setY(event.getScreenY() - yOffset[0]);
            }
        });
        new Pulse(root).play();
    }

}
