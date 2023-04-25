package it.polimi.ingsw.view.loginpage;

import it.polimi.ingsw.view.ImageRoundCornersClipper;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

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

    @FXML
    void initialize() throws IOException {
        ImageRoundCornersClipper.roundClipper(infoContainer, 10);
        ImageRoundCornersClipper.roundClipper(infoContainerBox, 10);
    }
}
