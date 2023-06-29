package it.polimi.ingsw.view.gui.layout.maininterface;

import it.polimi.ingsw.model.TileSubject;
import it.polimi.ingsw.utils.Coordinate;
import it.polimi.ingsw.utils.Triple;
import it.polimi.ingsw.view.gui.MyShelfieController;
import it.polimi.ingsw.view.gui.customcomponents.guitoolkit.MyShelfieAlertCreator;
import it.polimi.ingsw.view.gui.customcomponents.tileview.TileSubjectView;
import it.polimi.ingsw.view.gui.layout.gameinterface.GameInterfaceController;
import it.polimi.ingsw.view.gui.layout.opponentsinterface.OpponentsInterfaceController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.*;

public class MainInterfaceController extends MyShelfieController {
    @FXML
    private AnchorPane mainRootPane;

    @FXML
    private ScrollPane mainScrollPane;

    @FXML
    private VBox scrollContentBox;

    @FXML
    private GridPane gameInterface;

    @FXML
    private GameInterfaceController gameInterfaceController;

    @FXML
    private GridPane opponentsInterface;

    @FXML
    private OpponentsInterfaceController opponentsInterfaceController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void settingUpInterfaceControllers() {
        gameInterfaceController.setupBasicInformation(getMyShelfieApplicationLauncher());
        opponentsInterfaceController.setupBasicInformation(getMyShelfieApplicationLauncher());
    }

    /**
     * Handles all the operations necessary to updates the board and
     * the bookshelf of the opponents
     *
     * @param updatedBoard               the updated board
     * @param updatedOpponentBookshelves the updated opponent player bookshelf
     */
    public void transferTilesToOpponent(TileSubject[][] updatedBoard, Map<String, TileSubject[][]> updatedOpponentBookshelves) {

        List<TileSubjectView> takenTiles = getDifferentTileFromBoard(updatedBoard);

        if (takenTiles.size() > 0 && areUpdatedOpponentBookshelves(updatedOpponentBookshelves)) {
            gameInterfaceController.removeOpponentTakenTiles(takenTiles);

            opponentsInterfaceController.updateOpponentBookshelf(updatedOpponentBookshelves, takenTiles);

            Platform.runLater(this::updatedActiveTilesOnBoardNoneSelectedOperation);
        }
    }

    /**
     * @param updatedOpponentBookshelves
     * @return {@code true} if there is a different between graphical and updated
     * {@code false} otherwise
     */
    private boolean areUpdatedOpponentBookshelves(Map<String, TileSubject[][]> updatedOpponentBookshelves) {
        return updatedOpponentBookshelves
                .entrySet()
                .stream()
                .anyMatch(entry -> areUpdatedOpponentBookshelf(opponentsInterfaceController.getOpponentBookshelfFromName(entry.getKey()),
                        entry.getValue()));
    }

    /**
     * @param updatedOpponentBookshelf
     * @return {@code true} if the {@code updated opponent bookshelf} has an entry that is
     * different from {@code null}, {@code false} otherwise
     */
    private boolean areUpdatedOpponentBookshelf(TileSubject[][] shownOpponentBookshelf, TileSubject[][] updatedOpponentBookshelf) {

        if (shownOpponentBookshelf == null || updatedOpponentBookshelf == null)
            return false;

        for (int i = 0; i < updatedOpponentBookshelf.length; ++i) {
            for (int j = 0; j < updatedOpponentBookshelf[i].length; ++j) {
                if (updatedOpponentBookshelf[i][j] != shownOpponentBookshelf[i][j])
                    return true;
            }
        }

        return false;
    }

    /**
     * Retrieves all the {@linkplain TileSubjectView graphical tile subjects}
     * and their {@linkplain Coordinate position} from the {@code board matrix}
     * passed as parameter that are different from the actual board displayed
     *
     * @param updatedBoard the updated board that is potentially different
     *                     from the one displayed
     * @return every graphical tile displayed with its coordinate that is
     * different from the content of {@code updated board} (parameter)
     */
    private Map<Coordinate, TileSubjectView> getDifferentTileMapFromBoard(TileSubject[][] updatedBoard) {
        return gameInterfaceController.getBoardContent()
                .entrySet()
                .stream()
                .filter(entry -> hasDifferentContent(entry, updatedBoard))
                .collect(HashMap::new, (map, entry) -> map.put(entry.getKey(), entry.getValue()), HashMap::putAll);
    }

    /**
     * Retrieves all the {@linkplain TileSubjectView graphical tile subjects}
     * from the {@code matrix board} passed as parameter that are different
     * from the actual board displayed
     *
     * @param updatedBoard the updated board that is potentially different
     *                     from the one displayed
     * @return every graphical tile displayed with its coordinate that is
     * different from the content of {@code updated board} (parameter)
     */
    private List<TileSubjectView> getDifferentTileFromBoard(TileSubject[][] updatedBoard) {
        return new ArrayList<>(getDifferentTileMapFromBoard(updatedBoard).values());
    }

    /**
     * Retrieves all the coordinate {@linkplain Coordinate position} from the
     * {@code matrix board} passed as parameter that are different from the
     * actual board displayed
     *
     * @param updatedBoard the updated board that is potentially different
     *                     from the one displayed
     * @return every graphical tile displayed with its coordinate that is
     * different from the content of {@code updated board} (parameter)
     */
    @NotNull
    @Contract("_ -> new")
    private List<Coordinate> getDifferentTileCoordinateFromBoard(TileSubject[][] updatedBoard) {
        return new ArrayList<>(getDifferentTileMapFromBoard(updatedBoard).keySet());
    }

    /**
     * Retrieves all the {@linkplain TileSubjectView graphical tile subjects}
     * and their {@linkplain Coordinate position} from the {@code bookshelf matrix}
     * passed as parameter that are different from the actual bookshelf displayed
     *
     * @param updatedBookshelf the updated bookshelf that is potentially different
     *                         from the one displayed
     * @return every graphical tile displayed with its coordinate that is
     * different from the content of {@code updated bookshelf} (parameter)
     */
    private Map<Coordinate, TileSubjectView> getDifferentTileMapFromBookshelf(TileSubject[][] updatedBookshelf) {
        return gameInterfaceController.getThisPlayerBookshelfContent()
                .entrySet()
                .stream()
                .filter(entry -> hasDifferentContent(entry, updatedBookshelf))
                .collect(HashMap::new, (map, entry) -> map.put(entry.getKey(), entry.getValue()), HashMap::putAll);
    }

    /**
     * Retrieves all the {@linkplain TileSubjectView graphical tile subjects}
     * from the {@code bookshelf matrix} passed as parameter that are different
     * from the actual board displayed
     *
     * @param updatedBookshelf the updated bookshelf that is potentially different
     *                         from the one displayed
     * @return every graphical tile displayed with its coordinate that is
     * different from the content of {@code updated bookshelf} (parameter)
     */
    @NotNull
    @Contract("_ -> new")
    private List<TileSubjectView> getDifferentTileFromBookshelf(TileSubject[][] updatedBookshelf) {
        return new ArrayList<>(getDifferentTileMapFromBookshelf(updatedBookshelf).values());
    }

    /**
     * Verify if a {@code board entry} (composed by a couple formed of
     * {@linkplain Coordinate coordinate} and {@linkplain TileSubjectView
     * tile} has a different content (tile subject) than the
     * {@code board} in same row and column coordinate
     *
     * @param entry             the board entry to be checked
     * @param tileSubjectMatrix the matrix representing the board
     * @return {@code true} if the {@code boardEntry} has a different content
     * than the content of {@code board} in the same position
     */
    private boolean hasDifferentContent(@NotNull Map.Entry<Coordinate, TileSubjectView> entry, @NotNull TileSubject[][] tileSubjectMatrix) {

        if (entry.getValue() == null && tileSubjectMatrix[entry.getKey().getX()][entry.getKey().getY()] == null)
            return false;
        else if (entry.getValue() == null && tileSubjectMatrix[entry.getKey().getX()][entry.getKey().getY()] != null)
            return true;

        return entry.getValue().getTileSubject() != tileSubjectMatrix[entry.getKey().getX()][entry.getKey().getY()];
    }

    public void checkThisPlayerUpdate(TileSubject[][] updatedBoard, Map.Entry<String, TileSubject[][]> thisPlayerUpdatedBookshelf) {

        if (thisPlayerUpdatedBookshelf == null || updatedBoard == null)
            return;

        List<TileSubjectView> tilesTakenFromBoard = getDifferentTileFromBoard(updatedBoard);

        List<TileSubjectView> differentTilesInBookshelf = getDifferentTileFromBookshelf(thisPlayerUpdatedBookshelf.getValue());

        if (tilesTakenFromBoard.size() > 0 && differentTilesInBookshelf.size() == 0) {
            /*MyShelfieAlertCreator.displayErrorAlert(
                    "There was a problem with the communication with the server, the operation done will be rejected",
                    "Cannot perform the played"
            );*/

            gameInterfaceController.reverseClientPlayedFromBox();
            gameInterfaceController.clearTilesOldParent();

        } else if (differentTilesInBookshelf.size() > 0 && tilesTakenFromBoard.size() == 0) {
            gameInterfaceController.transferTilesToBookshelf();

            gameInterfaceController.clearTilesOldParent();
        }
    }

    private void transferEndTokenToOpponent(String opponentName) {
        gameInterfaceController.assignEndGameTokenToOpponent(opponentsInterfaceController.getOpponentEndTokenCell(opponentName));
    }

    private void transferToken1ToOpponent(String opponentName) {
        gameInterfaceController.assignToken1ToOpponent(opponentsInterfaceController.getOpponentFirstScoringTokenCell(opponentName));
    }

    private void transferToken2ToOpponent(String opponentName) {
        gameInterfaceController.assignToken2ToOpponent(opponentsInterfaceController.getOpponentSecondScoringTokenCell(opponentName));
    }

    public void receivedMessageOperation(@NotNull Triple<String, List<String>, String> lastMessage) {

        if (getGUILauncher().isThisPlayer(lastMessage.getFirst()))
            opponentsInterfaceController.manageSentMessage(lastMessage.getSecond(), lastMessage.getThird());
        else
            opponentsInterfaceController.manageReceivedMessage(lastMessage);
    }

    public void handleOpponentInterfaceInformation() {
        opponentsInterfaceController.addReceiverInChat(getOpponentPlayers().toArray(String[]::new));
    }

    public boolean isThisPlayerPlaying() {
        return getGUILauncher().isThisPlayer(getGUILauncher().getGUIModel().getCurrentPlayer()) &&
                gameInterfaceController.boxHasTiles();
    }

    public boolean isBoardEmpty() {
        final boolean[] isEmpty = {true};
        gameInterfaceController.getBoardContent()
                .forEach((key, value) -> {
                    if (value != null)
                        isEmpty[0] = false;
                });

        return isEmpty[0];
    }

    public boolean areAllBookshelfEmpty() {
        return areOpponentPlayerBookshelvesEmpty() && isThisPlayerBookshelfEmpty();
    }

    public boolean areOpponentPlayerBookshelvesEmpty() {
        final boolean[] isEmpty = {true};

        opponentsInterfaceController.getAllOpponentBookshelf().forEach(bookshelfState -> {
            bookshelfState.forEach((key, value) -> {
                if (value != null)
                    isEmpty[0] = false;
            });
        });

        return isEmpty[0];
    }

    public boolean isThisPlayerBookshelfEmpty() {
        final boolean[] isEmpty = {true};
        gameInterfaceController.getThisPlayerBookshelfContent()
                .forEach((key, value) -> {
                    if (value != null)
                        isEmpty[0] = false;
                });

        return isEmpty[0];
    }

    public void firstTimeFillBoard(TileSubject[][] newBoard) {
        gameInterfaceController.fillBoard(newBoard);
    }

    public void firstTimeFillBookshelves(@NotNull Map<String, TileSubject[][]> bookShelves) {
        bookShelves.forEach((nickName, newBookshelf) -> {
            if (getGUILauncher().isThisPlayer(nickName))
                gameInterfaceController.fillBookshelf(newBookshelf);
            else
                opponentsInterfaceController.fillBookshelf(nickName, newBookshelf);
        });
    }

    public void blockGameControlsOperation() {

        gameInterfaceController.disableGameButton();

        mainRootPane.requestFocus();
    }

    public void enableGameControlsOperation() {

        gameInterfaceController.enableGameButton();

        mainRootPane.requestFocus();
    }

    public void showGoalsOperation() {
        gameInterfaceController.addGoalsToGameInterface(getCommonGoalsMapAsArray());
    }

    private Integer[] getCommonGoalsMapAsArray() {
        Integer[] commonGoalsMap = new Integer[getGUILauncher().getGUIModel().getAvailableScores().size()];

        for (int i = 0; i < commonGoalsMap.length; ++i) {
            commonGoalsMap[i] = getGUILauncher().getGUIModel().getAvailableScores().get(i);
        }

        return commonGoalsMap;
    }

    public void updatedActiveTilesOnBoardNoneSelectedOperation() {
        gameInterfaceController.externalSetActiveTilesOnBoardNoneSelected();
    }

    public void showUpdatesInDisplayContentOperation(String text) {
        if (getGUILauncher().isThisPlayer(text))
            text = "your";
        else
            text += "'s";

        gameInterfaceController.updateTextInDisplayContent("It's " + text + " turn");
    }

    public void addFirstPlayerSeatOperation(String playerNick) {
        if (getGUILauncher().isThisPlayer(playerNick))
            gameInterfaceController.addFirstPlayerSeat();
        else
            opponentsInterfaceController.addFirstPlayerSeatToOpponent(playerNick);
    }

    public Integer requestDisplayedCommonGoalScore(int commonGoalNumber) {
        try {
            return gameInterfaceController.getDisplayedCommonGoalScore(commonGoalNumber);
        } catch (NullPointerException | EmptyStackException e) {
            return getGUILauncher().getGUIModel().getAvailableScores().get(commonGoalNumber);
        }
    }

    public void assignScoringToken1(String nickName) {
        if (getGUILauncher().isThisPlayer(nickName))
            gameInterfaceController.startTokenAnimation1();
        else
            transferToken1ToOpponent(nickName);
    }

    public void assignScoringToken2(String nickName) {
        if (getGUILauncher().isThisPlayer(nickName))
            gameInterfaceController.startTokenAnimation2();
        else
            transferToken2ToOpponent(nickName);
    }

    public void assignEndGameToken(String nickName) {
        if (getGUILauncher().isThisPlayer(nickName))
            gameInterfaceController.startEndGameTokenAnimation();
        else
            transferEndTokenToOpponent(nickName);
    }

    public void playersPointGained() {
        getGUILauncher().getGUIModel()
                .getPlayers()
                .forEach(this::commonGoalScoringTokenGained);
    }

    private void commonGoalScoringTokenGained(String nickName) {
        if (getGUILauncher().isThisPlayer(nickName))
            gameInterfaceController.forceCommonGoalScoreAssigment(getGUILauncher().getGUIModel().getPlayersPointsByNickname(nickName));
        else
            opponentsInterfaceController.forceCommonGoalScoreAssignment(nickName);
    }
}
