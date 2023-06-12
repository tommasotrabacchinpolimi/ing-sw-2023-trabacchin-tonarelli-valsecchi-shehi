package it.polimi.ingsw.view.gui.layout.loginpage;

import it.polimi.ingsw.view.gui.MyShelfieApplication;
import javafx.stage.Stage;

public class LoginPage extends MyShelfieApplication {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage){

        setupScene("login/login-page.fxml");

        setupStage(stage);

        stage.show();
    }
}
