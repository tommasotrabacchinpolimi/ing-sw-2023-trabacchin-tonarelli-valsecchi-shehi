package it.polimi.ingsw.view.gui.loginpage;

import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.view.gui.MyShelfieApplication;
import it.polimi.ingsw.view.gui.MyShelfieButton;
import it.polimi.ingsw.view.gui.MyShelfieController;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.css.PseudoClass;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import static java.util.Map.entry;

public class LoginPageController extends MyShelfieController {

    private static final Duration animationDuration = new Duration(400);

    private static final PseudoClass errorClass = PseudoClass.getPseudoClass("error");

    @FXML
    private AnchorPane rootPane;

    @FXML
    private GridPane interfaceGrid;

    @FXML
    private GridPane infoContainer;

    @FXML
    private Label welcomeText;

    @FXML
    private TextField nicknameInput;

    @FXML
    private StackPane infoBoxContainer;

    @FXML
    private Pane myShelfieTitleImageView;

    @FXML
    private MyShelfieButton joinButton;

    @FXML
    private Pane cranioCreationLogo;

    @FXML
    private MyShelfieButton createButton;

    @FXML
    private MyShelfieButton playerNumberButton;

    @FXML
    private TextField playerNumberInput;

    @FXML
    private Label playerNumberText;

    private String nickName;

    private int playersNumber;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Load page with the focus on the pane and not on input field or button
        Platform.runLater( () -> rootPane.requestFocus() );

        setNicknameInputState();
        setPlayerNumberInputState();
    }

    @FXML
    private void setAllNonFocused(MouseEvent mouseEvent) {
        rootPane.requestFocus();
    }

    @FXML
    private void textFieldKeyPressed(@NotNull KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ESCAPE) {
            interfaceGrid.requestFocus();

            if(nicknameInput.isVisible() && !nicknameInput.isDisabled() && nicknameInput.getText().isEmpty())
                nicknameInput.pseudoClassStateChanged(errorClass, false);

            if(playerNumberInput.isVisible() && !playerNumberInput.isDisabled() && playerNumberInput.getText().isEmpty())
                playerNumberInput.pseudoClassStateChanged(errorClass, false);
        }

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (nicknameInput.isVisible() && !nicknameInput.isDisabled() && !verifyNickname()) {
                //show an alert to decide if the player wants to join or to create a Game
            }

            if (playerNumberInput.isVisible() && !playerNumberInput.isDisabled() && !verifyPlayerNumber()) {
                executePlayerNumberSubmitted();
            }
        }
    }

    @FXML
    private void joinGameSubmitted(InputEvent inputEvent) {
        if(isButtonActionCalled(inputEvent)){
            getNickNameFromField();

            getLogicController().joinGame(nickName);
        }
    }

    @FXML
    private void createGameSubmitted(InputEvent inputEvent) {
        if(isButtonActionCalled(inputEvent)){
            getNickNameFromField();

            gameCreationUI();
        }
    }

    @FXML
    private void playerNumberSubmitted(InputEvent inputEvent) {
        if(isButtonActionCalled(inputEvent)){
            executePlayerNumberSubmitted();
        }
    }

    private void executePlayerNumberSubmitted() {
        getPlayersNumberFromField();

        getLogicController().createGame(this.nickName, this.playersNumber);
    }

    private boolean isButtonActionCalled(InputEvent inputEvent) {
        return inputEvent.getEventType() == MouseEvent.MOUSE_PRESSED ||
                (inputEvent.getEventType() == KeyEvent.KEY_PRESSED &&
                        ((KeyEvent) inputEvent).getCode() == KeyCode.ENTER);
    }

    private void getNickNameFromField() {
        nickName = nicknameInput.getText();
        clearNicknameInput();
    }

    private void getPlayersNumberFromField() {
        playersNumber = Integer.parseInt(playerNumberInput.getText());
        clearPlayerNumberInput();
    }

    private void clearNicknameInput() {
        nicknameInput.clear();
        nicknameInput.pseudoClassStateChanged(errorClass, false);
    }

    private void clearPlayerNumberInput() {
        playerNumberInput.clear();
        playerNumberInput.pseudoClassStateChanged(errorClass, false);
    }

    private void setNicknameInputState() {
        nicknameInput.textProperty().addListener(event -> {
            nicknameInput.pseudoClassStateChanged(errorClass, verifyNickname());

            joinButton.setDisable(verifyNickname());
            createButton.setDisable(verifyNickname());
        });
    }

    private void setPlayerNumberInputState() {
        playerNumberInput.textProperty().addListener(event -> {
            playerNumberInput.pseudoClassStateChanged(errorClass, verifyPlayerNumber());

            setDisableAfterTransition(playerNumberButton, verifyPlayerNumber());
        });
    }

    /**
     * @return true if the input is not valid
     */
    private boolean verifyNickname() {
        return nicknameInput.getText().isEmpty() || nicknameInput.getText().contains("Errata");
    }

    /**
     * @return true if the number is not valid
     */
    private boolean verifyPlayerNumber() {
        return playerNumberInput.getText().isEmpty() ||
                invalidNumberInput() ||
                playerNumberInBounds();
    }

    /**
     * @return false if the string inserted is a valid number
     */
    private boolean invalidNumberInput() {
        if(playerNumberText.getText().isEmpty())
            return true;

        try {
            Integer.parseInt(playerNumberInput.getText());
            return false;
        } catch (NumberFormatException nfe) {
            return true;
        }
    }

    /**
     * @return true if the button needs to be disabled
     */
    private boolean playerNumberInBounds() {
        if (!invalidNumberInput())
            return Integer.parseInt(playerNumberInput.getText()) <= 1 || Integer.parseInt(playerNumberInput.getText()) > 4;

        return false;
    }

    private void gameCreationUI() {
        disappearingElements(List.of(joinButton, createButton, nicknameInput, welcomeText));

        showElements(
                Map.ofEntries(entry(playerNumberInput, 1.0),
                        entry(playerNumberText, 1.0),
                        entry(playerNumberButton, 0.5))
        );

        rootPane.requestFocus();
    }

    private void disappearingElements(List<Node> nodes) {
        nodes.forEach(node -> {
            fadeElement(node, 1.0, 0.0);
        });
    }

    private void showElements(Map<Node, Double> elementsOpacity) {
        elementsOpacity.forEach((element, opacity) -> {
            fadeElement(element, 0.0, opacity);
            element.setVisible(true);
            element.setDisable(opacity != 1.0);
        });
    }

    private void fadeElement(Node node, double fromValue, double toValue) {
        FadeTransition fadeTransition = new FadeTransition(animationDuration);
        fadeTransition.setNode(node);
        fadeTransition.setFromValue(fromValue);
        fadeTransition.setToValue(toValue);
        fadeTransition.setCycleCount(1);
        fadeTransition.setAutoReverse(false);

        fadeTransition.playFromStart();
    }

    public void setDisableAfterTransition(Node node, boolean disable) {
        node.setDisable(disable);

        if (disable) {
            node.setOpacity(0.5); // set the opacity to a value that appears grayed out
        } else {
            node.setOpacity(1.0);
        }
    }

    private void displayGameInterface() {
        Stage primaryStage = ((Stage) rootPane.getScene().getWindow());

        primaryStage.close();

        MyShelfieApplication myShelfieApplication = getMyShelfieApplicationLauncher();

        Scene newScene = myShelfieApplication.setUpSceneWithPane("board/board-view.fxml");

        primaryStage.setScene(newScene);

        primaryStage.sizeToScene();

        primaryStage.centerOnScreen();

        primaryStage.show();
    }

    @Override
    public void onGameStateChangedNotified() {
        if(MyShelfieApplication.getUi().getModel().getGameState().equals(GameState.INIT.toString())){
            Platform.runLater(this::displayGameInterface);
        }
    }

    @Override
    public void onExceptionNotified() {
        //alert display
    }
}
