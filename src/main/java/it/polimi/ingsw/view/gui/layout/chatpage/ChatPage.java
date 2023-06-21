
package it.polimi.ingsw.view.gui.layout.chatpage;

import it.polimi.ingsw.view.gui.MyShelfieApplication;
import javafx.stage.Stage;

public class ChatPage extends MyShelfieApplication {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        setupScene("chat/chat-page.fxml");
        setupStage(stage);

        stage.show();
    }
}

