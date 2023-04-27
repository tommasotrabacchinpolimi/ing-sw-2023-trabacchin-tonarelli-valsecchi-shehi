package it.polimi.ingsw.view.loginpage;

import it.polimi.ingsw.view.ImageRoundCornersClipper;
import it.polimi.ingsw.view.WrappedImageView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

import java.io.IOException;
import java.util.Objects;

public class LoginPageController {

    public GridPane rootPane;
    public GridPane infoContainer;
    public Label infoText;
    public AnchorPane infoTextWrapper;
    public GridPane infoBox;
    public TextField nicknameInput;
    public AnchorPane infoContainerBox;
    public WrappedImageView myShelfieTitle;
    public AnchorPane myShelfieTitleWrapper;

    @FXML
    void initialize() throws IOException {

        Font.loadFont(Objects.requireNonNull(LoginPageController.class.getResource("/it.polimi.ingsw/graphical.resources/font/SpecialElite-Regular.ttf")).openStream(), Font.getDefault().getSize());

        ImageRoundCornersClipper.roundClipper(infoContainer, 10);
        ImageRoundCornersClipper.roundClipper(infoContainerBox, 10);
    }

    @FXML
    public void textFieldKeyPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ESCAPE) {
            rootPane.requestFocus();
        }

        if(keyEvent.getCode() == KeyCode.ENTER) {
            System.out.println("Nickname: " + nicknameInput.getText());
            nicknameInput.setText("");
        }
    }

    public void setAllNonFocused(MouseEvent mouseEvent) {
        rootPane.requestFocus();
    }
}
