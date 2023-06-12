package it.polimi.ingsw.view.gui.layout.opponentsinterface;

import it.polimi.ingsw.view.gui.MyShelfieController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class OpponentsInterfaceController extends MyShelfieController {

    @FXML
    private GridPane opponentsInterfaceRootPane;

    @FXML
    private VBox opponentsVBoxContainer;
    @Override
    public void onGameStateChangedNotified() {

    }

    @Override
    public void onExceptionNotified() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addOpponentBookshelf();
        addOpponentBookshelf();
        addOpponentBookshelf();
    }

    private void addOpponentBookshelf() {
        FXMLLoader opponentBookshelfLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/it.polimi.ingsw/gui/layout/bookshelf/opponent-bookshelf.fxml")));

        Pane pane;

        try {
            pane = opponentBookshelfLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        opponentsVBoxContainer.getChildren().add(pane);
    }
}
