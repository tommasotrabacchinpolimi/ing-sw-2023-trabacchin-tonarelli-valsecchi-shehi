package it.polimi.ingsw.view.gui.layout.maininterface;

import it.polimi.ingsw.model.TileSubject;
import it.polimi.ingsw.utils.Coordinate;
import it.polimi.ingsw.view.gui.MyShelfieController;
import it.polimi.ingsw.view.gui.customcomponents.MyShelfieButton;
import it.polimi.ingsw.view.gui.customcomponents.tileview.TileSubjectView;
import it.polimi.ingsw.view.gui.layout.gameinterface.GameInterfaceController;
import it.polimi.ingsw.view.gui.layout.opponentsinterface.OpponentsInterfaceController;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

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
    public void onGameStateChangedNotified() {

    }

    @Override
    public void onExceptionNotified() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupTestingButton();
    }

    /**
     * Handles all the operations necessary to updates the board and
     * the bookshelf of the opponents
     *
     * @param board             the updated board
     * @param playerBookshelves the updated opponent player bookshelf
     */
    private void transferTilesToOpponent(TileSubject[][] board, Map<String, TileSubject[][]> playerBookshelves) {

        List<TileSubjectView> takenTiles = getTakenTilesFromBoard(board);

        gameInterfaceController.removeOpponentTakenTiles(takenTiles);

        opponentsInterfaceController.updateOpponentBookshelf(playerBookshelves, takenTiles);
    }

    /**
     * Retrieves all the {@linkplain TileSubjectView graphical tile subjects}
     * and their {@linkplain Coordinate position} from the {@code matrix board}
     * passed as parameter that are different from the actual board displayed
     *
     * @param board the updated board that is potentially different from the
     *              one displayed
     * @return every graphical tile displayed with its coordinate that is
     * different from the content of {@code board} (parameter)
     */
    @NotNull
    private List<TileSubjectView> getTakenTilesFromBoard(TileSubject[][] board) {
        return gameInterfaceController.retrieveBoardState()
                .entrySet()
                .stream()
                .filter(entry -> boardHasDifferentContent(entry, board))
                .map(Map.Entry::getValue)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Verify if a {@code boardEntry} (composed by a couple formed of
     * {@linkplain Coordinate coordinate} and {@linkplain TileSubjectView
     * tile} have a different content (tile subject) than the
     * {@code board} in same row and column coordinate
     *
     * @param boardEntry the board entry to be checked
     * @param board      the matrix representing the board
     * @return {@code true} if the {@code boardEntry} has a different content
     * than the content of {@code board} in the same position
     */
    private boolean boardHasDifferentContent(@NotNull Map.Entry<Coordinate, TileSubjectView> boardEntry, @NotNull TileSubject[][] board) {
        return boardEntry.getValue().getTileSubject() != board[boardEntry.getKey().getX()][boardEntry.getKey().getY()];
    }

    private void transferEndTokenToOpponent(String opponentName) {
        gameInterfaceController.assignEndGameTokenToOpponent(opponentsInterfaceController.getFreeOpponentPointCell(opponentName));
    }

    private void transferToken1ToOpponent(String opponentName) {
        gameInterfaceController.assignToken1ToOpponent(opponentsInterfaceController.getFreeOpponentPointCell(opponentName));
    }

    private void transferToken2ToOpponent(String opponentName) {
        gameInterfaceController.assignToken2ToOpponent(opponentsInterfaceController.getFreeOpponentPointCell(opponentName));
    }

    //For testing purpose

    private void setupTestingButton() {
        MyShelfieButton fillBoardButton = new MyShelfieButton("Fill board");
        fillBoardButton.setId("fillBoardButton");
        fillBoardButton.setOnMouseClicked(value -> gameInterfaceController.fillBoard(value));

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
            TileSubject[][] differentBoard = getDifferentBoard();
            transferTilesToOpponent(differentBoard, getDifferentBookshelvesMap(differentBoard));
        });

        gameInterfaceController.testingBox.getChildren().addAll(fillBoardButton,
                testTokenAnimation1, testTokenAnimation2, getEndGame, assignEndGame, assignTokenOpponent1,
                assignTokenOpponent2, tilesToOpponent);
    }

    private TileSubject[][] getDifferentBoard() {
        TileSubject[][] modifiedBoard = gameInterfaceController.getTilesOnBoardMatrix();

        int get = 0;

        for (int i = 0; i < modifiedBoard.length; ++i) {
            for (int j = 0; j < modifiedBoard[i].length; ++j) {
                if(modifiedBoard[i][j] != null) {
                    modifiedBoard[i][j] = null;
                    ++get;
                }

                if(get == 3)
                    return modifiedBoard;
            }
        }

        return modifiedBoard;
    }

    @NotNull
    private Map<String, TileSubject[][]> getDifferentBookshelvesMap(TileSubject[][] modifiedBoard) {
        List<TileSubjectView> taken = getTakenTilesFromBoard(modifiedBoard);

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
