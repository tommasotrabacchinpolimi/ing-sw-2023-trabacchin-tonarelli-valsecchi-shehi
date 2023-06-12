package it.polimi.ingsw.view.gui.maininterface;

import it.polimi.ingsw.view.gui.MyShelfieApplication;
import javafx.stage.Stage;

public class MainInterface extends MyShelfieApplication {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        setupScene("maininterface/main-interface.fxml");

        setupFullScreenStage(stage);

        stage.show();
    }
}
