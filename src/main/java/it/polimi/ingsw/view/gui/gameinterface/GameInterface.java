package it.polimi.ingsw.view.gui.gameinterface;

import it.polimi.ingsw.view.gui.MyShelfieApplication;
import javafx.application.Application;
import javafx.stage.Stage;

public class GameInterface extends MyShelfieApplication {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        setUpScene("gameinterface/game-interface.fxml");

        setUpStage(stage);

        stage.show();
    }
}
