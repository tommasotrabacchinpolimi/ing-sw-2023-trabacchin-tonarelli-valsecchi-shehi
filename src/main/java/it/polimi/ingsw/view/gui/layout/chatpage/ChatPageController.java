package it.polimi.ingsw.view.gui.layout.chatpage;


import it.polimi.ingsw.view.gui.MyShelfieController;
import it.polimi.ingsw.view.gui.customcomponents.MyShelfieAlertCreator;
import it.polimi.ingsw.view.gui.customcomponents.messageView.ChatViewBox;
import it.polimi.ingsw.view.gui.customcomponents.messageView.SingleMessageViewType;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.ResourceBundle;

public class ChatPageController extends MyShelfieController {

    private static final int MAX_CHARS_MESSAGE = 400;

    @FXML
    private ScrollPane scrollingChatPane;

    @FXML
    private TextArea inputMessage;

    @FXML
    private AnchorPane chatPageRootPane;

    @FXML
    private GridPane chatPageBoxDisplacer;

    private final KeyCodeCombination keyCombinationNewLineOnMessage = new KeyCodeCombination(KeyCode.ENTER, KeyCombination.SHIFT_DOWN);

    private final ChatViewBox chatViewBox = new ChatViewBox();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        chatViewBox.addMessage(SingleMessageViewType.SENT, "Adem", "My Shelfie Ã¨ un gioco di merda e Melanie lo pensa pure");

        chatViewBox.addMessage(SingleMessageViewType.RECEIVED, "Ema", "Dai Adem non dire cosÃ¬ Ã¨ almeno un 30L assicurato");

        setupScrollingChatPane();

        chatViewBox.heightProperty().addListener((observableValue, oldValue, newValue) -> {
            scrollingChatPane.setVvalue(1.0);
        });

        inputMessage.setTextFormatter(new TextFormatter<String>(change ->
                change.getControlNewText().length() <= MAX_CHARS_MESSAGE ? change : null));
    }

    public void sendMessage(@NotNull KeyEvent keyEvent) {
        if (keyCombinationNewLineOnMessage.match(keyEvent)) {
            inputMessage.setText(inputMessage.getText() + "\n");

            inputMessage.end();

        } else if (keyEvent.getCode() == KeyCode.ENTER &&
                inputMessage.getText() != null &&
                !inputMessage.getText().equals("")) {

            chatViewBox.addMessage(SingleMessageViewType.SENT, "Adem", inputMessage.getText());

            inputMessage.clear();
        }
    }

    private void setupScrollingChatPane() {
        scrollingChatPane.setContent(chatViewBox);

        scrollingChatPane.layoutBoundsProperty().addListener((observableValue, oldValue, newValue) -> {
            scrollingChatPane.setPrefViewportHeight(newValue.getHeight());
            scrollingChatPane.setPrefViewportWidth(newValue.getWidth());

            chatViewBox.setPrefSize(newValue.getWidth(), newValue.getHeight());
        });
    }

    @Override
    public void onGameStateChangedNotified() {

    }

    @Override
    public void onExceptionNotified() {

    }
}