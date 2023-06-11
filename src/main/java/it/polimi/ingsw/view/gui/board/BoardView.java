package it.polimi.ingsw.view.gui.board;

import it.polimi.ingsw.view.gui.MyShelfieApplication;
import javafx.stage.Stage;

public class BoardView extends MyShelfieApplication {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        setupSceneWithPane("board/board-view.fxml");

        setupStage(stage);

        stage.show();
    }
}
