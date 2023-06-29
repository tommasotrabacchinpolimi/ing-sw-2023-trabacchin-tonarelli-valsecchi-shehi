package it.polimi.ingsw.view.gui.layout.loginpage;

import it.polimi.ingsw.view.gui.customcomponents.MyShelfieButton;
import it.polimi.ingsw.view.gui.MyShelfieController;
import it.polimi.ingsw.view.gui.customcomponents.guitoolkit.MyShelfieAlertCreator;
import javafx.animation.FadeTransition;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.Node;
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
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import static it.polimi.ingsw.view.gui.customcomponents.guitoolkit.MyShelfieTransitionDurationType.DEF_DURATION;
import static java.util.Map.entry;

public class LoginPageController extends MyShelfieController {

    private final PseudoClass errorClass = PseudoClass.getPseudoClass("error");

    @FXML
    private AnchorPane loginRootPane;

    @FXML
    private GridPane loginInterfaceGrid;

    @FXML
    private GridPane loginInfoContainer;

    @FXML
    private Label loginWelcomeText;

    @FXML
    private TextField loginNicknameInput;

    @FXML
    private StackPane loginInfoBoxContainer;

    @FXML
    private Pane loginTitleImageView;

    @FXML
    private MyShelfieButton loginJoinButton;

    @FXML
    private Pane loginPublisherLogo;

    @FXML
    private MyShelfieButton loginCreateButton;

    @FXML
    private MyShelfieButton loginPlayerNumberButton;

    @FXML
    private TextField loginPlayerNumberInput;

    @FXML
    private Label loginPlayerNumberText;

    private String nickName;

    private int playersNumber;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setNicknameInputState();
        setPlayerNumberInputState();
    }

    @FXML
    private void setAllNonFocused(MouseEvent mouseEvent) {
        loginRootPane.requestFocus();
    }

    @FXML
    private void textFieldKeyPressed(@NotNull KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ESCAPE) {
            loginInterfaceGrid.requestFocus();

            if(loginNicknameInput.isVisible() && !loginNicknameInput.isDisabled() && loginNicknameInput.getText().isEmpty())
                loginNicknameInput.pseudoClassStateChanged(errorClass, false);

            if(loginPlayerNumberInput.isVisible() && !loginPlayerNumberInput.isDisabled() && loginPlayerNumberInput.getText().isEmpty())
                loginPlayerNumberInput.pseudoClassStateChanged(errorClass, false);
        }

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (loginNicknameInput.isVisible() && !loginNicknameInput.isDisabled() && !verifyNickname()) {
                MyShelfieAlertCreator.displayInformationAlert("Please choose an option between \"Create game\" and \"Join game\"");
            }

            if (loginPlayerNumberInput.isVisible() && !loginPlayerNumberInput.isDisabled() && !verifyPlayerNumber()) {
                executePlayerNumberSubmitted();
            }
        }
    }

    @FXML
    private void joinGameSubmitted(InputEvent inputEvent) {
        if(isButtonActionCalled(inputEvent)){
            getNickNameFromField();

            getLogicController().joinGame(nickName);
            getGUILauncher().showWaitForPlayers();
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

        getLogicController().createGame(nickName, playersNumber);
    }

    private boolean isButtonActionCalled(InputEvent inputEvent) {
        return inputEvent.getEventType() == MouseEvent.MOUSE_PRESSED ||
                inputEvent.getEventType() == MouseEvent.MOUSE_CLICKED ||
                (inputEvent.getEventType() == KeyEvent.KEY_PRESSED &&
                        ((KeyEvent) inputEvent).getCode() == KeyCode.ENTER);
    }

    private void getNickNameFromField() {
        nickName = loginNicknameInput.getText();
        clearNicknameInput();
    }

    private void getPlayersNumberFromField() {
        playersNumber = Integer.parseInt(loginPlayerNumberInput.getText());
        clearPlayerNumberInput();
    }

    private void clearNicknameInput() {
        loginNicknameInput.clear();
        loginNicknameInput.pseudoClassStateChanged(errorClass, false);
    }

    private void clearPlayerNumberInput() {
        loginPlayerNumberInput.clear();
        loginPlayerNumberInput.pseudoClassStateChanged(errorClass, false);
    }

    private void setNicknameInputState() {
        loginNicknameInput.textProperty().addListener(event -> {
            loginNicknameInput.pseudoClassStateChanged(errorClass, verifyNickname());

            loginJoinButton.setDisable(verifyNickname());
            loginCreateButton.setDisable(verifyNickname());
        });
    }

    private void setPlayerNumberInputState() {
        loginPlayerNumberInput.textProperty().addListener(event -> {
            loginPlayerNumberInput.pseudoClassStateChanged(errorClass, verifyPlayerNumber());

            setDisableAfterTransition(loginPlayerNumberButton, verifyPlayerNumber());
        });
    }

    /**
     * @return true if the input is not valid
     */
    private boolean verifyNickname() {
        return loginNicknameInput.getText().isEmpty();
    }

    /**
     * @return true if the number is not valid
     */
    private boolean verifyPlayerNumber() {
        return loginPlayerNumberInput.getText().isEmpty() ||
                invalidNumberInput() ||
                playerNumberInBounds();
    }

    /**
     * @return false if the string inserted is a valid number
     */
    private boolean invalidNumberInput() {
        if(loginPlayerNumberText.getText().isEmpty())
            return true;

        try {
            Integer.parseInt(loginPlayerNumberInput.getText());
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
            return Integer.parseInt(loginPlayerNumberInput.getText()) <= 1 || Integer.parseInt(loginPlayerNumberInput.getText()) > 4;

        return false;
    }

    private void gameCreationUI() {
        disappearingElements(List.of(loginJoinButton, loginCreateButton, loginNicknameInput, loginWelcomeText));

        showElements(
                Map.ofEntries(entry(loginPlayerNumberInput, 1.0),
                        entry(loginPlayerNumberText, 1.0),
                        entry(loginPlayerNumberButton, 0.5))
        );

        loginRootPane.requestFocus();
    }

    private void disappearingElements(List<Node> nodes) {
        nodes.forEach(node -> {
            fadeElement(node, 1.0, 0.0);
        });
    }

    private void showElements(@NotNull Map<Node, Double> elementsOpacity) {
        elementsOpacity.forEach((element, opacity) -> {
            fadeElement(element, 0.0, opacity);
            element.setVisible(true);
            element.setDisable(opacity != 1.0);
        });
    }

    private void fadeElement(Node node, double fromValue, double toValue) {
        FadeTransition fadeTransition = new FadeTransition(DEF_DURATION.getDuration());
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
}
