package it.polimi.ingsw.view.gui.loginpage;

import it.polimi.ingsw.view.gui.ImageRoundCornersClipper;
import it.polimi.ingsw.view.gui.WrappedImageView;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlurType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

public class LoginPageController {

    public GridPane rootPane;
    public GridPane infoContainer;
    public Label infoText;
    public AnchorPane infoTextWrapper;
    public GridPane infoBox;
    public TextField nicknameInput;
    public AnchorPane infoContainerBox;
    public WrappedImageView myShelfieTitle;
    public GridPane inputBox;
    public Button nicknameSubmit;

    @FXML
    void initialize() {

        ImageRoundCornersClipper.roundClipper(infoContainer, 10);
        ImageRoundCornersClipper.roundClipper(infoContainerBox, 10);
        ImageRoundCornersClipper.roundClipperDropShadow(nicknameSubmit, 30, BlurType.GAUSSIAN, Color.web("#101B1F",1.0), 30, 30, 30, 30);

        checkNickname();
    }

    @FXML
    public void textFieldKeyPressed(@NotNull KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ESCAPE) {
            rootPane.requestFocus();
        }

        if (keyEvent.getCode() == KeyCode.ENTER) {
            nicknameSubmitted();
        }
    }

    @FXML
    public void setAllNonFocused(MouseEvent mouseEvent) {
        rootPane.requestFocus();
    }

    private void checkNickname() {
        nicknameInput.textProperty().addListener(event -> {
            nicknameInput.pseudoClassStateChanged(
                    PseudoClass.getPseudoClass("error"),
                    !nicknameInput.getText().isEmpty() && nicknameInput.getText().contains("Errata")
            );
        });
    }

    private void setNicknameInputInError() {
        nicknameInput.textProperty().addListener(event -> {
            nicknameInput.pseudoClassStateChanged(
                    PseudoClass.getPseudoClass("error"),
                    nicknameInput.getText().isEmpty()
            );
        });
    }

    public void nicknameSubmitted() {
        if (nicknameInput.getText().isEmpty())
            setNicknameInputInError();

        System.out.println("Nickname: " + nicknameInput.getText());

        nicknameInput.setText("");
    }

    public void nicknameButtonEntered(MouseEvent mouseEvent) {
    }
}
