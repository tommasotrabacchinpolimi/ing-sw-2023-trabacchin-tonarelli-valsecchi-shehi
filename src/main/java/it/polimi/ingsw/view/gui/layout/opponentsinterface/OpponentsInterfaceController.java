package it.polimi.ingsw.view.gui.layout.opponentsinterface;

import it.polimi.ingsw.view.gui.MyShelfieController;
import it.polimi.ingsw.view.gui.customcomponents.MyShelfieAlertCreator;
import it.polimi.ingsw.view.gui.layout.bookshelf.SingleOpponentBookShelfController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class OpponentsInterfaceController extends MyShelfieController {

    private static final String OPPONENTS_LAYOUT = "/it.polimi.ingsw/gui/layout/bookshelf/single-opponent-bookshelf.fxml";

    private static final String OPPONENT_BOOKSHELF_ROOT_PREFIX = "OpponentInfoBox";

    @FXML
    private GridPane opponentsRootPane;

    @FXML
    private HBox opponentsInfoDisplacer;
    private final List<Pair<VBox, SingleOpponentBookShelfController>> opponentsBookshelfViewController = new ArrayList<>();

    @Override
    public void onGameStateChangedNotified() {

    }

    @Override
    public void onExceptionNotified() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addOpponentBookshelf("Wizzo");
        addOpponentBookshelf("Nicco");
        addOpponentBookshelf("Vito");
    }

    private void addOpponentBookshelf(String playerName) {

        try {
            FXMLLoader opponentBookshelfLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(OPPONENTS_LAYOUT)));

            VBox opponentBookshelfRootPane = opponentBookshelfLoader.load();

            opponentBookshelfRootPane.setId(playerName + OPPONENT_BOOKSHELF_ROOT_PREFIX);

            opponentsBookshelfViewController.add(new Pair<>(opponentBookshelfRootPane, opponentBookshelfLoader.getController()));

            opponentsInfoDisplacer.getChildren().add(opponentBookshelfRootPane);

            getLastOpponentBookshelfController().setOpponentBookshelfName(playerName);
        } catch (IOException | NullPointerException e) {
            MyShelfieAlertCreator.displayErrorAlert(
                    "Can't display opponents bookshelf and information, " +
                            "you have probably compromised the application structure",
                    "Error in displaying opponents bookshelf");
        }
    }

    private SingleOpponentBookShelfController getLastOpponentBookshelfController() {
        return opponentsBookshelfViewController.get((opponentsBookshelfViewController.size() - 1)).getValue();
    }
}
