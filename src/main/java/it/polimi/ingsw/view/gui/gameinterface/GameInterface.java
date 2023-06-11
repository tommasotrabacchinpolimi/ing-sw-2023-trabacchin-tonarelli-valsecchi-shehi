package it.polimi.ingsw.view.gui.gameinterface;

import it.polimi.ingsw.view.gui.MyShelfieApplication;
import javafx.stage.Stage;

public class GameInterface extends MyShelfieApplication {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        setupScene("gameinterface/game-interface.fxml");

        setupStage(stage);

        stage.show();
    }
}
