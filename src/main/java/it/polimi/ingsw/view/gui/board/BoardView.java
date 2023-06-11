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

        setUpSceneWithPane("board/board-view.fxml");

        setUpStage(stage);

        stage.show();
    }
}
