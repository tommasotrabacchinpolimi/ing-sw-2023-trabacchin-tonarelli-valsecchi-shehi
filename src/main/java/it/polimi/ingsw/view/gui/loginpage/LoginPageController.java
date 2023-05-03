package it.polimi.ingsw.view.gui.loginpage;

import it.polimi.ingsw.view.gui.ImageRoundCornersClipper;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.Bloom;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import org.jetbrains.annotations.NotNull;


public class LoginPageController {
    public AnchorPane sceneBackground;
    public GridPane rootPane;
    public GridPane infoContainer;
    public Label welcomeText;
    public GridPane infoBoxDivider;
    public TextField nicknameInput;
    public AnchorPane infoBoxContainer;
    public ImageView myShelfieTitle;
    public Button joinButton;
    public ImageView cranioCreationLogo;
    public GridPane inputContainer;
    public Button createButton;
    private static final PseudoClass errorClass = PseudoClass.getPseudoClass("error");

    @FXML
    void initialize() {

        ImageRoundCornersClipper.roundClipper(infoContainer, 10);
        ImageRoundCornersClipper.roundClipper(infoBoxContainer, 10);
        ImageRoundCornersClipper.roundClipper(joinButton, 30);
        ImageRoundCornersClipper.roundClipper(createButton, 30);

        checkNickname();
    }

    @FXML
    public void setAllNonFocused(MouseEvent mouseEvent) {
        sceneBackground.requestFocus();
    }

    @FXML
    public void textFieldKeyPressed(@NotNull KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ESCAPE) {
            rootPane.requestFocus();
        }

        if (keyEvent.getCode() == KeyCode.ENTER) {
            joinGameSubmitted(null);
        }

        joinButton.setDisable(needToActivateButton());
        createButton.setDisable(needToActivateButton());
    }

    @FXML
    public void joinGameSubmitted(MouseEvent mouseEvent) {

        System.out.println("Nickname: " + nicknameInput.getText() + "joined a game");

        clearNicknameInput();
    }

    @FXML
    public void createGameSubmitted(MouseEvent mouseEvent) {
        System.out.println("Nickname: " + nicknameInput.getText() + "create a game");

        clearNicknameInput();
    }

    @FXML
    public void createGameButtonEntered(MouseEvent mouseEvent) {
        enhanceButton(createButton);
    }

    @FXML
    public void createGameButtonExited(MouseEvent mouseEvent) {
        reverseEnhanceButton(createButton);
    }

    @FXML
    public void joinGameButtonEntered(MouseEvent mouseEvent) {
        enhanceButton(joinButton);
    }

    @FXML
    public void joinGameButtonExited(MouseEvent mouseEvent) {
        reverseEnhanceButton(joinButton);
    }

    private void enhanceButton(Button button) {
        button.setEffect(new Bloom());
    }

    private void reverseEnhanceButton(Button button) {
        button.setEffect(null);
    }

    private void clearNicknameInput() {
        nicknameInput.clear();
        joinButton.setDisable(true);
        createButton.setDisable(true);
    }

    private void checkNickname() {
        nicknameInput.textProperty().addListener(event -> {
            nicknameInput.pseudoClassStateChanged(errorClass, errorInNicknameInput());

            joinButton.setDisable(needToActivateButton());
            createButton.setDisable(needToActivateButton());
        });
    }

    private boolean errorInNicknameInput() {
        return !nicknameInput.getText().isEmpty() && nicknameInput.getText().contains("Errata");
    }

    private boolean needToActivateButton(){
        return (nicknameInput.getText().isEmpty() ||
                nicknameInput.getText().equals("") ||
                nicknameInput.getText().contains("Errata")) || errorInNicknameInput();
    }
}
