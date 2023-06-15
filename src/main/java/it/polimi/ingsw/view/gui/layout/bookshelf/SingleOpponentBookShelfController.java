package it.polimi.ingsw.view.gui.layout.bookshelf;

import it.polimi.ingsw.view.gui.MyShelfieController;
import it.polimi.ingsw.view.gui.customcomponents.bookshelf.OpponentBookshelfView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

import static it.polimi.ingsw.utils.color.MyShelfieColor.BONE;

public class SingleOpponentBookShelfController extends MyShelfieController {

    @FXML
    private VBox singleOpponentRootPane;

    @FXML
    private GridPane transparentGridPaneContainer;

    @FXML
    private Label opponentBookshelfName;

    @FXML
    private OpponentBookshelfView opponentBookshelfView;

    @FXML
    private StackPane leftPane;

    @FXML
    private StackPane midLeftPane;

    @FXML
    private StackPane midPane;

    @FXML
    private StackPane midRightPane;

    @FXML
    private StackPane rightPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setOpponentBookshelfName(String playerName) {
        opponentBookshelfName.setText(playerName);
        opponentBookshelfName.setStyle("-fx-background-color: " + BONE.getLightenRGBAStyleSheet(0.10) + ";" +
                "-fx-border-radius: 0.3em;" +
                "-fx-font-family: 'Special Elite Regular';" +
                "-fx-wrap-text: true;" +
                "-fx-text-alignment: center;" +
                "-fx-font-size: 1.7em;" +
                "-fx-text-fill: " + BONE.getRGBAStyleSheet(0.93) +";" +
                "-fx-padding: 0.5em 0.1em 0.7em 0.1em;");
    }

    @Override
    public void onGameStateChangedNotified() {

    }

    @Override
    public void onExceptionNotified() {

    }
}
