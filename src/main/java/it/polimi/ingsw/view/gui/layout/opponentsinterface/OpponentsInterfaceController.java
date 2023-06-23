package it.polimi.ingsw.view.gui.layout.opponentsinterface;

import it.polimi.ingsw.model.TileSubject;
import it.polimi.ingsw.view.gui.MyShelfieController;
import it.polimi.ingsw.view.gui.customcomponents.guitoolkit.MyShelfieAlertCreator;
import it.polimi.ingsw.view.gui.customcomponents.tileview.TileSubjectView;
import it.polimi.ingsw.view.gui.layout.bookshelf.SingleOpponentBookShelfController;
import it.polimi.ingsw.view.gui.layout.chatpage.ChatPageController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.*;
import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class OpponentsInterfaceController extends MyShelfieController {

    private static final String OPPONENTS_LAYOUT = "/it.polimi.ingsw/gui/layout/bookshelf/single-opponent-bookshelf.fxml";

    private static final String OPPONENT_BOOKSHELF_ROOT_PREFIX = "OpponentInfoBox";

    @FXML
    private StackPane opponentsChatInterfaceContainer;

    @FXML
    private GridPane opponentsRootPane;

    @FXML
    private HBox opponentsInfoDisplacer;

    @FXML
    private AnchorPane opponentsChatInterface;

    private ChatPageController opponentsChatInterfaceController;

    private final List<Pair<VBox, SingleOpponentBookShelfController>> opponentsBookshelfViewController = new ArrayList<>();

    @Override
    public void onGameStateChangedNotified() {

    }

    @Override
    public void onExceptionNotified() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addOpponentBookshelf("Adem");
        addOpponentBookshelf("Melanie");
        addOpponentBookshelf("Tommy");
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

    private SingleOpponentBookShelfController getSingleOpponentBookshelfController(String playerName) throws NoSuchElementException {
        return opponentsBookshelfViewController.stream()
                .filter(pair -> pair.getKey().getId().equals(playerName + OPPONENT_BOOKSHELF_ROOT_PREFIX))
                .map(Pair::getValue)
                .findFirst()
                .orElseThrow();
    }

    /**
     * Updates the bookshelf of all opponent players in the game
     *
     * @param updatedPlayerBookshelves the updated opponent players
     *                          bookshelf
     * @param takenTiles
     */
    public void updateOpponentBookshelf(@NotNull Map<String, TileSubject[][]> updatedPlayerBookshelves,
                                        @NotNull List<TileSubjectView> takenTiles) {
        updatedPlayerBookshelves.forEach((playerName, bookshelf) -> {
            try {
                getSingleOpponentBookshelfController(playerName).insertTilesInOpponentBookshelf(bookshelf, takenTiles);
            } catch (IllegalArgumentException e) {
                MyShelfieAlertCreator.displayErrorAlert(e);
            }
        });
    }

    public Pane getFreeOpponentPointCell(String opponentName) {
        return getSingleOpponentBookshelfController(opponentName).getEndGameTokenPointCell();
    }

    //For testing
    public TileSubject[][] getBookshelfFromName(String name) {
        return getSingleOpponentBookshelfController(name).getOpponentBookshelf();
    }

    public void receivedMessageHandler(String senderNickName, String messageContent) {
        opponentsChatInterfaceController.addReceivedMessage(senderNickName, messageContent);
    }
}
