package it.polimi.ingsw.view.gui.layout.chatpage;


import it.polimi.ingsw.view.gui.MyShelfieController;
import it.polimi.ingsw.view.gui.customcomponents.MyShelfieChoiceBox;
import it.polimi.ingsw.view.gui.customcomponents.guitoolkit.MyShelfieAlertCreator;
import it.polimi.ingsw.view.gui.customcomponents.messageView.ChatViewBox;
import it.polimi.ingsw.view.gui.customcomponents.messageView.SingleMessageViewType;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
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

    private MyShelfieChoiceBox receiverChoiceBox;

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

    public void addReceivedMessage(String senderNickName, String messageContent) {
        chatViewBox.addMessage(SingleMessageViewType.RECEIVED, senderNickName ,messageContent);
    }

    public void addReceiveChoices(String... receivers) {
        receiverChoiceBox = new MyShelfieChoiceBox("All");

        addReceivers(receivers);

        chatPageBoxDisplacer.add(receiverChoiceBox, 0, 1);

        GridPane.setValignment(receiverChoiceBox, VPos.CENTER);

        GridPane.setHalignment(receiverChoiceBox, HPos.LEFT);
    }

    private void addReceivers(String... receivers) {
        if(receivers == null || receivers.length == 0 || receiverChoiceBox == null) {
            MyShelfieAlertCreator.displayErrorAlert(
                    "No opponents where found, you can't send any message",
            "No opponent available");

            inputMessage.setPromptText("No OpponentsFound");
            inputMessage.setMouseTransparent(true);
        }else {
            receiverChoiceBox.getItems().addAll(receivers);
        }
    }
}