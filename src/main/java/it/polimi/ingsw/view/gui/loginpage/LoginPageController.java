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
    public Button nicknameSubmit;
    public ImageView cranioCreationLogo;
    public GridPane inputContainer;

    private static final PseudoClass errorClass = PseudoClass.getPseudoClass("error");

    @FXML
    void initialize() {

        ImageRoundCornersClipper.roundClipper(infoContainer, 10);
        ImageRoundCornersClipper.roundClipper(infoBoxContainer, 10);
        ImageRoundCornersClipper.roundClipper(nicknameSubmit, 30);

        nicknameInput.textProperty().addListener(event -> {
                nicknameSubmit.setDisable(nicknameInput.getText().isEmpty() || nicknameInput.getText().equals(""));
        });

        checkNickname();
    }

    @FXML
    public void textFieldKeyPressed(@NotNull KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ESCAPE) {
            rootPane.requestFocus();
        }

        if (keyEvent.getCode() == KeyCode.ENTER) {
            nicknameSubmitted(null);
        }
    }

    @FXML
    public void setAllNonFocused(MouseEvent mouseEvent) {
        sceneBackground.requestFocus();
    }

    private void checkNickname() {
        nicknameInput.textProperty().addListener(event -> {
            nicknameInput.pseudoClassStateChanged(errorClass, errorInNicknameInput());

            nicknameSubmit.setDisable(errorInNicknameInput());
        });
    }

    private boolean errorInNicknameInput() {
        return !nicknameInput.getText().isEmpty() && nicknameInput.getText().contains("Errata");
    }

    @FXML
    public void nicknameButtonEntered(MouseEvent mouseEvent) {
        nicknameSubmit.setEffect(new Bloom());
        nicknameSubmit.setStyle("-fx-font-size: " + (nicknameSubmit.getFont().getSize() + 1) + "px");
    }

    @FXML
    public void nicknameButtonExited(MouseEvent mouseEvent) {
        nicknameSubmit.setEffect(null);
        nicknameSubmit.setStyle("-fx-font-size: " + (nicknameSubmit.getFont().getSize() - 1) + "px");
    }

    @FXML
    public void nicknameSubmitted(MouseEvent mouseEvent) {

        System.out.println("Nickname: " + nicknameInput.getText());

        nicknameInput.clear();
    }
}
