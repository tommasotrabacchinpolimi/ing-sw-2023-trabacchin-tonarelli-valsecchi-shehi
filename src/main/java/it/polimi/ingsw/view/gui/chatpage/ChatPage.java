
package it.polimi.ingsw.view.gui.chatpage;

import it.polimi.ingsw.view.gui.MyShelfieApplication;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ChatPage extends MyShelfieApplication {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        setUpScene("chat/chat-page.fxml");
        setUpStage(stage);

        stage.show();
    }
}

