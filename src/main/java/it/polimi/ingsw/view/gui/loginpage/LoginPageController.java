package it.polimi.ingsw.view.gui.loginpage;

import it.polimi.ingsw.view.gui.MyShelfieApplication;
import it.polimi.ingsw.view.gui.MyShelfieButton;
import it.polimi.ingsw.view.gui.MyShelfieController;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.*;
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

    @FXML
    private void setAllNonFocused(MouseEvent mouseEvent) {
        rootPane.requestFocus();
    }

    @FXML
    public void textFieldKeyPressed(@NotNull KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ESCAPE) {
            interfaceGrid.requestFocus();

            if(nicknameInput.isVisible() && !nicknameInput.isDisabled() && nicknameInput.getText().isEmpty())
                nicknameInput.pseudoClassStateChanged(errorClass, false);

            if(playerNumberInput.isVisible() && !playerNumberInput.isDisabled() && playerNumberInput.getText().isEmpty())
                playerNumberInput.pseudoClassStateChanged(errorClass, false);
        }

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (nicknameInput.isVisible() && !nicknameInput.isDisabled()) {
                //show small message
            }

            if (playerNumberInput.isVisible()) {
                playerNumberSubmitted(null);
            }
        }
    }

    @FXML
    public void joinGameSubmitted(MouseEvent mouseEvent) {
        clearNicknameInput();
    }

    @FXML
    public void createGameSubmitted(MouseEvent mouseEvent) {
        clearNicknameInput();

        gameCreationUI();
    }

    @FXML
    public void playerNumberSubmitted(MouseEvent mouseEvent) {
        clearPlayerNumberInput();

        displayGameInterface();
    }

    protected void displayGameInterface() {
        Stage primaryStage = ((Stage) rootPane.getScene().getWindow());

        primaryStage.close();

        MyShelfieApplication myShelfieApplication = getMyShelfieApplicationLauncher();

        Scene newScene = myShelfieApplication.setUpSceneWithPane("board/board-view.fxml");

        primaryStage.setScene(newScene);

        primaryStage.sizeToScene();

        primaryStage.centerOnScreen();

        primaryStage.show();
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Load page with the focus on the pane and not on input field or button
        Platform.runLater( () -> interfaceGrid.requestFocus() );

        setNicknameInputState();
        setPlayerNumberInputState();
    }
}
