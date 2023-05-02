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
    public Button joinSubmit;
    public ImageView cranioCreationLogo;
    public GridPane inputContainer;
    public Button createSubmit;
    private static final PseudoClass errorClass = PseudoClass.getPseudoClass("error");

    @FXML
    void initialize() {

        ImageRoundCornersClipper.roundClipper(infoContainer, 10);
        ImageRoundCornersClipper.roundClipper(infoBoxContainer, 10);
        ImageRoundCornersClipper.roundClipper(joinSubmit, 30);
        ImageRoundCornersClipper.roundClipper(createSubmit, 30);

        nicknameInput.textProperty().addListener(event -> {
                joinSubmit.setDisable(needToActivateButton());
                createSubmit.setDisable(needToActivateButton());
        });

        checkNickname();
    }

    @FXML
    public void textFieldKeyPressed(@NotNull KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ESCAPE) {
            rootPane.requestFocus();
        }

        if (keyEvent.getCode() == KeyCode.ENTER) {
            joinGame(null);
        }

        joinSubmit.setDisable(needToActivateButton());
        createSubmit.setDisable(needToActivateButton());
    }

    @FXML
    public void setAllNonFocused(MouseEvent mouseEvent) {
        sceneBackground.requestFocus();
    }

    private void checkNickname() {
        nicknameInput.textProperty().addListener(event -> {
            nicknameInput.pseudoClassStateChanged(errorClass, errorInNicknameInput());

            joinSubmit.setDisable(needToActivateButton());
            createSubmit.setDisable(needToActivateButton());
        });
    }

    private boolean errorInNicknameInput() {
        return !nicknameInput.getText().isEmpty() && nicknameInput.getText().contains("Errata");
    }

    @FXML
    public void joinGameButtonEntered(MouseEvent mouseEvent) {
        joinSubmit.setEffect(new Bloom());
        joinSubmit.setStyle("-fx-font-size: " + (joinSubmit.getFont().getSize() + 1) + "px");
    }

    @FXML
    public void joinGameButtonExited(MouseEvent mouseEvent) {
        joinSubmit.setEffect(null);
        joinSubmit.setStyle("-fx-font-size: " + (joinSubmit.getFont().getSize() - 1) + "px");
    }

    @FXML
    public void joinGame(MouseEvent mouseEvent) {

        System.out.println("Nickname: " + nicknameInput.getText() + "joined a game");

        clearNicknameInput();
    }

    @FXML
    public void createGame(MouseEvent mouseEvent) {
        System.out.println("Nickname: " + nicknameInput.getText() + "create a game");

        clearNicknameInput();
    }

    private void clearNicknameInput() {
        nicknameInput.clear();
        joinSubmit.setDisable(true);
        createSubmit.setDisable(true);
    }

    @FXML
    public void createGameButtonEntered(MouseEvent mouseEvent) {
        createSubmit.setEffect(new Bloom());
        createSubmit.setStyle("-fx-font-size: " + (joinSubmit.getFont().getSize() + 1) + "px");
    }

    @FXML
    public void createGameButtonExited(MouseEvent mouseEvent) {
        createSubmit.setEffect(null);
        createSubmit.setStyle("-fx-font-size: " + (joinSubmit.getFont().getSize() - 1) + "px");
    }

    private boolean needToActivateButton(){
        return (nicknameInput.getText().isEmpty() ||
                nicknameInput.getText().equals("") ||
                nicknameInput.getText().contains("Errata")) || errorInNicknameInput();
    }
}
