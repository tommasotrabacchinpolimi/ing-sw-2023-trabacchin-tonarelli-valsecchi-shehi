package it.polimi.ingsw.view.gui.layout.opponentsinterface;

import it.polimi.ingsw.model.TileSubject;
import it.polimi.ingsw.utils.Coordinate;
import it.polimi.ingsw.utils.Triple;
import it.polimi.ingsw.view.gui.MyShelfieApplication;
import it.polimi.ingsw.view.gui.MyShelfieController;
import it.polimi.ingsw.view.gui.customcomponents.FirstPlayerSeatView;
import it.polimi.ingsw.view.gui.customcomponents.guitoolkit.MyShelfieAlertCreator;
import it.polimi.ingsw.view.gui.customcomponents.messageview.SingleMessageViewPrivacyType;
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

    @FXML
    private ChatPageController opponentsChatInterfaceController;

    private final List<Pair<VBox, SingleOpponentBookShelfController>> opponentsBookshelfViewController = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void setupBasicInformation(MyShelfieApplication myShelfieApplicationLauncher) {
        setMyShelfieApplicationLauncher(myShelfieApplicationLauncher);

        opponentsChatInterfaceController.setMyShelfieApplicationLauncher(getMyShelfieApplicationLauncher());

        getOpponentPlayers().forEach(this::addOpponentBookshelf);
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

    /**
     *
     * @param playerName
     *
     * @return {@code empty optional} if the opponent player bookshelf is not found
     * otherwise an {@code optional} with the reference of the bookshelf is retrieved
     */
    @NotNull
    private Optional<SingleOpponentBookShelfController> getSingleOpponentBookshelfController(String playerName){
        return opponentsBookshelfViewController.stream()
                .filter(pair -> pair.getKey().getId().equals(playerName + OPPONENT_BOOKSHELF_ROOT_PREFIX))
                .map(Pair::getValue)
                .findFirst();
    }

    /**
     * Updates the bookshelf of all opponent players in the game
     *
     * @param updatedOpponentBookshelves the updated opponent players
     *                                   bookshelf
     * @param takenTiles
     */
    public void updateOpponentBookshelf(@NotNull Map<String, TileSubject[][]> updatedOpponentBookshelves,
                                        @NotNull List<TileSubjectView> takenTiles) {
        updatedOpponentBookshelves.forEach((playerName, bookshelf) -> {
            try {
                getSingleOpponentBookshelfController(playerName).ifPresent(
                        singleOpponentBookShelfController ->
                                singleOpponentBookShelfController.insertTilesInOpponentBookshelf(bookshelf, takenTiles)
                );
            } catch (IllegalArgumentException e) {
                MyShelfieAlertCreator.displayErrorAlert(e);
            } catch (NoSuchElementException ignored) {

            }
        });
    }

    /**
     *
     * @param opponentName
     * @return
     * @throws NoSuchElementException if the reference to the graphical data
     * connected with the {@code opponent} is not found
     */
    public Pane getOpponentFirstPlayerSeatCell(String opponentName) throws NoSuchElementException{
        return getSingleOpponentBookshelfController(opponentName).orElseThrow().getSingleOpponentFirstPlayerSeatCell();
    }

    public Pane getOpponentEndTokenCell(String opponentName) throws NoSuchElementException{
        return getSingleOpponentBookshelfController(opponentName).orElseThrow().getSingleOpponentEndTokenCell();
    }

    public Pane getOpponentFirstScoringTokenCell(String opponentName) throws NoSuchElementException{
        return getSingleOpponentBookshelfController(opponentName).orElseThrow().getSingleOpponentFirstScoringTokenCell();
    }

    public Pane getOpponentSecondScoringTokenCell(String opponentName) throws NoSuchElementException{
        return getSingleOpponentBookshelfController(opponentName).orElseThrow().getSingleOpponentSecondScoringTokenCell();
    }

    public List<Map<Coordinate, TileSubjectView>> getAllOpponentBookshelf() {
        List<Map<Coordinate, TileSubjectView>> allOpponentBookshelf = new ArrayList<>();

        if(getGUILauncher() == null)
            return allOpponentBookshelf;

        getGUILauncher().getGUIModel().getPlayers()
                .stream()
                .filter(nickName -> !nickName.equals(getGUILauncher().getGUIModel().getThisPlayer()))
                .forEach(nickName -> {
                    allOpponentBookshelf.add(getSingleOpponentBookshelfController(nickName).orElseThrow().getBookshelfState());
                });

        return allOpponentBookshelf;
    }

    //For testing
    public TileSubject[][] getBookshelfFromName(String name) {
        return getSingleOpponentBookshelfController(name).orElseThrow().getOpponentBookshelf();
    }

    public void manageReceivedMessage(@NotNull Triple<String, List<String>, String> lastMessage) {

        if (lastMessage.getSecond().size() > 1 || getOpponentPlayers().size() == 1)
            opponentsChatInterfaceController
                    .displayReceivedMessage(SingleMessageViewPrivacyType.PUBLIC, lastMessage.getFirst(),
                            lastMessage.getThird());
        else if (lastMessage.getSecond().size() == 1)
            opponentsChatInterfaceController
                    .displayReceivedMessage(SingleMessageViewPrivacyType.PRIVATE, lastMessage.getFirst(),
                            lastMessage.getThird(), lastMessage.getSecond().get(0));
    }

    public void addReceiverInChat(String... receivers) {
        opponentsChatInterfaceController.addReceiveChoices(receivers);
    }

    public void manageSentMessage(List<String> receivers, String messageContent) {
        opponentsChatInterfaceController.displaySentMessage(receivers, messageContent);
    }

    public void fillBookshelf(String nickName, TileSubject[][] newBookshelf) {
        getSingleOpponentBookshelfController(nickName).ifPresent(singleOpponentBookShelfController -> {
            for(int i = 0; i < newBookshelf.length; ++i) {
                for(int j = 0; j < newBookshelf[i].length; ++j) {
                    if(newBookshelf[i][j] != null)
                        singleOpponentBookShelfController.forcedInsertionInBookshelf(newBookshelf[i][j], i, j);
                }
            }
        });
    }

    public void addFirstPlayerSeatToOpponent(String playerNick) {
        getOpponentFirstPlayerSeatCell(playerNick).getChildren().add(new FirstPlayerSeatView());
    }

    public void forceCommonGoalScoreAssignment(String nickName) {
        getSingleOpponentBookshelfController(nickName).ifPresent(
                singleOpponentBookShelfController -> singleOpponentBookShelfController.forceDisplayCommonGoalScore(getGUILauncher().getGUIModel().getPlayersPointsByNickname(nickName))
        );
    }
}
