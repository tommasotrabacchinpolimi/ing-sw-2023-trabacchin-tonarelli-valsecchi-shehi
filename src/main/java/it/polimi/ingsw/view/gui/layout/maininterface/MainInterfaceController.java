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

        if (takenTiles.size() > 0 && areUpdatedOpponentBookshelves(updatedOpponentBookshelves.values())) {
            gameInterfaceController.removeOpponentTakenTiles(takenTiles);

            opponentsInterfaceController.updateOpponentBookshelf(updatedOpponentBookshelves, takenTiles);

            Platform.runLater(this::updatedActiveTilesOnBoardNoneSelectedOperation);
        }
    }

    private boolean areUpdatedOpponentBookshelves(Collection<TileSubject[][]> updatedOpponentBookshelves) {
        return updatedOpponentBookshelves.stream().anyMatch(this::areUpdatedOpponentBookshelf);
    }

    /**
     * @param updatedOpponentBookshelf
     * @return {@code true} if the {@code updated opponent bookshelf} has an entry that is
     * different from {@code null}, {@code false} otherwise
     */
    private boolean areUpdatedOpponentBookshelf(TileSubject[][] updatedOpponentBookshelf) {

        if (updatedOpponentBookshelf == null)
            return false;

        for (int i = 0; i < updatedOpponentBookshelf.length; ++i) {
            for (int j = 0; j < updatedOpponentBookshelf[i].length; ++j) {
                if (updatedOpponentBookshelf[i][j] != null)
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

        if (tilesTakenFromBoard.size() > 0 && differentTilesInBookshelf.size() > 0) {
            MyShelfieAlertCreator.displayErrorAlert(
                    "There was a problem with the communication with the server, the operation done will be rejected",
                    "Cannot perform the played"
            );

            gameInterfaceController.restoreClientPlayed(tilesTakenFromBoard);

        } else if (tilesTakenFromBoard.size() > 0 && isThisPlayerPlaying()) {
            undoClientPlayedOperation();
            gameInterfaceController.confirmInput();
        } else {
            gameInterfaceController.confirmInput();
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

        if (lastMessage.getFirst().equals(getGUILauncher().getGUIModel().getThisPlayer()))
            opponentsInterfaceController.manageSentMessage(lastMessage.getSecond(), lastMessage.getThird());
        else
            opponentsInterfaceController.manageReceivedMessage(lastMessage);
    }

    public void handleOpponentInterfaceInformation() {
        opponentsInterfaceController.addReceiverInChat(getOpponentPlayers().toArray(String[]::new));
    }

    public boolean isThisPlayerPlaying() {
        return getGUILauncher().getGUIModel().getCurrentPlayer().equals(getGUILauncher().getGUIModel().getCurrentPlayer()) &&
                gameInterfaceController.boxHasTiles() && gameInterfaceController.boxHasChild();
    }

    public void undoClientPlayedOperation() {
        gameInterfaceController.reverseClientPlayed();
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
            if (nickName.equals(getGUILauncher().getGUIModel().getThisPlayer()))
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
        if (getGUILauncher().getGUIModel().getThisPlayer().equals(text))
            text = "your";
        else
            text += "'s";

        gameInterfaceController.updateTextInDisplayContent("It's " + text + " turn");
    }

    public void addFirstPlayerSeatOperation(String playerNick) {
        if (getGUILauncher().getGUIModel().getPlayers().get(0).equals(getGUILauncher().getGUIModel().getThisPlayer()))
            gameInterfaceController.addFirstPlayerSeat();
        else
            opponentsInterfaceController.addFirstPlayerSeatToOpponent(playerNick);
    }

    public Integer requestDisplayedCommonGoalScore(int commonGoalNumber) {
        try {
            return gameInterfaceController.getDisplayedCommonGoalScore(commonGoalNumber);
        } catch (NullPointerException e) {
            return getGUILauncher().getGUIModel().getAvailableScores().get(commonGoalNumber);
        }
    }

    public void assignScoringToken1(String nickName) {
        if (getGUILauncher().getGUIModel().getThisPlayer().equals(nickName))
            gameInterfaceController.startTokenAnimation1();
        else
            transferToken1ToOpponent(nickName);
    }

    public void assignScoringToken2(String nickName) {
        if (getGUILauncher().getGUIModel().getThisPlayer().equals(nickName))
            gameInterfaceController.startTokenAnimation2();
        else
            transferToken2ToOpponent(nickName);
    }

    public void assignEndGameToken(String nickName) {
        if (getGUILauncher().getGUIModel().getPlayers().get(0).equals(getGUILauncher().getGUIModel().getThisPlayer()))
            gameInterfaceController.startEndGameTokenAnimation();
        else
            transferEndTokenToOpponent(nickName);
    }

    //For testing purpose
    private void setupTestingButton() {
        /*MyShelfieButton fillBoardButton = new MyShelfieButton("Fill board");
        fillBoardButton.setId("fillBoardButton");
        fillBoardButton.setOnMouseClicked(value -> gameInterfaceController.fillBoard());

        MyShelfieButton testTokenAnimation1 = new MyShelfieButton("Get Token1");
        testTokenAnimation1.setId("testTokenAnimation1");
        testTokenAnimation1.setOnMouseClicked(value -> gameInterfaceController.startTokenAnimation1(value));

        MyShelfieButton testTokenAnimation2 = new MyShelfieButton("Get Token2");
        testTokenAnimation2.setId("testTokenAnimation2");
        testTokenAnimation2.setOnMouseClicked(value -> gameInterfaceController.startTokenAnimation2(value));

        MyShelfieButton getEndGame = new MyShelfieButton("Get EndToken");
        getEndGame.setId("getEndGame");
        getEndGame.setOnMouseClicked(value -> gameInterfaceController.startEndGameTokenAnimation(value));

        MyShelfieButton assignEndGame = new MyShelfieButton("EndToken Opponent");
        assignEndGame.setId("assignEndGame");
        assignEndGame.setOnMouseClicked(value -> transferEndTokenToOpponent("Adem"));

        MyShelfieButton assignTokenOpponent1 = new MyShelfieButton("Opponent Token1");
        assignTokenOpponent1.setId("assignTokenOpponent1");
        assignTokenOpponent1.setOnMouseClicked(value -> transferToken1ToOpponent("Tommy"));

        MyShelfieButton assignTokenOpponent2 = new MyShelfieButton("Opponent Token2");
        assignTokenOpponent2.setId("assignTokenOpponent2");
        assignTokenOpponent2.setOnMouseClicked(value -> transferToken2ToOpponent("Tommy"));

        MyShelfieButton tilesToOpponent = new MyShelfieButton("Opponent Tiles");
        tilesToOpponent.setId("tilesToOpponent");
        tilesToOpponent.setOnMouseClicked(value -> {
            TileSubject[][] updatedBoard = getDifferentBoard();
            transferTilesToOpponent(updatedBoard, getDifferentBookshelvesMap(updatedBoard));
        });

        gameInterfaceController.testingBox.getChildren().addAll(
                testTokenAnimation1, testTokenAnimation2, getEndGame, assignEndGame, assignTokenOpponent1,
                assignTokenOpponent2, tilesToOpponent);*/
    }

    private TileSubject[][] getDifferentBoard() {
        TileSubject[][] modifiedBoard = gameInterfaceController.getTilesOnBoardMatrix();

        int get = 0;

        for (int i = 0; i < modifiedBoard.length; ++i) {
            for (int j = 0; j < modifiedBoard[i].length; ++j) {
                if (modifiedBoard[i][j] != null) {
                    modifiedBoard[i][j] = null;
                    ++get;
                }

                if (get == 3)
                    return modifiedBoard;
            }
        }

        return modifiedBoard;
    }

    @NotNull
    private Map<String, TileSubject[][]> getDifferentBookshelvesMap(TileSubject[][] modifiedBoard) {
        List<TileSubjectView> taken = getDifferentTileFromBoard(modifiedBoard);

        Map<String, TileSubject[][]> updates = new HashMap<>();

        TileSubject[][] opponentBookshelf = opponentsInterfaceController.getBookshelfFromName("Melanie");

        for (int i = 5; i > 2; --i) {
            opponentBookshelf[i][0] = taken.get(0).getTileSubject();
            taken.remove(0);
        }

        updates.put("Melanie", opponentBookshelf);

        return updates;
    }
}
