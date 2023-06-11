package it.polimi.ingsw.view.gui.connectioninterface;

import it.polimi.ingsw.view.gui.MyShelfieApplication;
import javafx.stage.Stage;

public class ConnectionPage extends MyShelfieApplication {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        setupScene("connection/connection-page.fxml");

        setupStage(stage);

        stage.show();
    }
}
