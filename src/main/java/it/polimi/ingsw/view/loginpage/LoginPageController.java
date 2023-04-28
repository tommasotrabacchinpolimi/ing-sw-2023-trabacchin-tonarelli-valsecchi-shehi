package it.polimi.ingsw.view.loginpage;

import it.polimi.ingsw.view.ImageRoundCornersClipper;
import it.polimi.ingsw.view.WrappedImageView;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class LoginPageController {

    public GridPane rootPane;
    public GridPane infoContainer;
    public Label infoText;
    public AnchorPane infoTextWrapper;
    public GridPane infoBox;
    public TextField nicknameInput;
    public AnchorPane infoContainerBox;
    public WrappedImageView myShelfieTitle;

    @FXML
    void initialize() throws IOException {

        ImageRoundCornersClipper.roundClipper(infoContainer, 10);
        ImageRoundCornersClipper.roundClipper(infoContainerBox, 10);

        checkNickname();
    }

    @FXML
    public void textFieldKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ESCAPE) {
            rootPane.requestFocus();
        }

        if (keyEvent.getCode() == KeyCode.ENTER) {
            System.out.println("Nickname: " + nicknameInput.getText());
            nicknameInput.setText("");
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
}
