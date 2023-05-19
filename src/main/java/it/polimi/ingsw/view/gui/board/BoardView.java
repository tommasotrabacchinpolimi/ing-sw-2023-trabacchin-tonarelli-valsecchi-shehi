package it.polimi.ingsw.view.gui.board;

import it.polimi.ingsw.view.gui.MyShelfieApplication;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BoardView extends MyShelfieApplication {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Scene scene = MyShelfieApplication.setUpScene("board/board-view.fxml");

        setUpStage(stage, scene);

        Platform.runLater( () -> setDynamicFontSize(scene));

        stage.centerOnScreen();

        stage.show();
    }
}
