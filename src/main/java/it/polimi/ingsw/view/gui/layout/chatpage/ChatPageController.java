package it.polimi.ingsw.view.gui.layout.chatpage;


import it.polimi.ingsw.view.gui.MyShelfieController;
import it.polimi.ingsw.view.gui.customcomponents.MyShelfieChoiceBox;
import it.polimi.ingsw.view.gui.customcomponents.guitoolkit.MyShelfieAlertCreator;
import it.polimi.ingsw.view.gui.customcomponents.messageview.ChatViewBox;
import it.polimi.ingsw.view.gui.customcomponents.messageview.SingleMessageViewPrivacyType;
import it.polimi.ingsw.view.gui.customcomponents.messageview.SingleMessageViewType;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
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
import java.util.List;
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

            if(receiverChoiceBox.getValue().equalsIgnoreCase("all") || getOpponentPlayers().size() == 1) {
                getLogicController().sentMessage(inputMessage.getText().substring(0, inputMessage.getText().length() - 1),
                        getOpponentPlayers().toArray(String[]::new));
            } else {
                getLogicController().sentMessage(inputMessage.getText().substring(0, inputMessage.getText().length() - 1),
                        receiverChoiceBox.getValue().lines().toArray(String[]::new));
            }

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

    public void displayReceivedMessage(SingleMessageViewPrivacyType messageViewPrivacyType, String senderNickName,
                                       String messageContent) {
        chatViewBox.addMessage(SingleMessageViewType.RECEIVED, messageViewPrivacyType, senderNickName ,messageContent);
    }

    public void displayReceivedMessage(SingleMessageViewPrivacyType messageViewPrivacyType, String senderNickName,
                                       String messageContent, String receiver) {
        chatViewBox.addMessage(SingleMessageViewType.RECEIVED, messageViewPrivacyType, senderNickName ,messageContent, receiver);
    }

    public void displaySentMessage(List<String> receiver, String messageContent) {

        if(receiver.size() == getOpponentPlayers().size())
            chatViewBox.addMessage(SingleMessageViewType.SENT, SingleMessageViewPrivacyType.PUBLIC, messageContent);
        else if(receiver.size() == 1)
            chatViewBox.addMessage(SingleMessageViewType.SENT, receiver.get(0), messageContent);
    }

    public void addReceiveChoices(String... receivers) {

        if(receivers.length == 1) {
            receiverChoiceBox = new MyShelfieChoiceBox(receivers[0]);
            receiverChoiceBox.setMouseTransparent(true);
        } else {
            receiverChoiceBox = new MyShelfieChoiceBox("All");
            addReceivers(receivers);
        }

        chatPageBoxDisplacer.add(receiverChoiceBox, 0, 1);

        GridPane.setValignment(receiverChoiceBox, VPos.CENTER);

        GridPane.setHalignment(receiverChoiceBox, HPos.LEFT);

        receiverChoiceBox.setMaxWidth(Double.POSITIVE_INFINITY);
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