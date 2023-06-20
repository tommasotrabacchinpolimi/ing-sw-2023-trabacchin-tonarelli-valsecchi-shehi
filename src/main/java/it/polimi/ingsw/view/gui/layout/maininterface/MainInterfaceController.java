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

    private void transferTilesToOpponent(TileSubject[][] board, Map<String, TileSubject[][]> playerBookshelves) {

        Map<Coordinate, TileSubjectView> takenTiles = getTakenTilesFromBoard(board);

        gameInterfaceController.removeOpponentTakenTiles(takenTiles.values().stream().toList());

        opponentsInterfaceController.updateOpponentBookshelf(playerBookshelves, takenTiles);
    }

    /**
     * Retrieves all the {@linkplain TileSubjectView graphical tile subjects}
     * and their {@linkplain Coordinate position} from the {@code matrix board}
     * passed as parameter that are different from the actual board displayed
     *
     * @param board the updated board that has potentially different from the
     *              one displayed
     * @return every graphical tile displayed with its coordinate that is
     * different from the content of {@code board} (parameter)
     */
    @NotNull
    private Map<Coordinate, TileSubjectView> getTakenTilesFromBoard(TileSubject[][] board) {
        return gameInterfaceController.retrieveBoardState()
                .entrySet()
                .stream()
                .filter(entry -> boardHasDifferentContent(entry, board))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * Verify if a {@code boardEntry} (composed by a couple formed of
     * {@linkplain Coordinate coordinate} and {@linkplain TileSubjectView
     * tile} have a different content (tile subject) than the
     * {@code board} in same row and column coordinate
     *
     * @param boardEntry the board entry to be checked
     * @param board      the matrix representing the board
     *
     * @return {@code true} if the {@code boardEntry} has a different content
     * than the content of {@code board} in the same position
     */
    private boolean boardHasDifferentContent(@NotNull Map.Entry<Coordinate, TileSubjectView> boardEntry, @NotNull TileSubject[][] board) {
        return boardEntry.getValue().getTileSubject() != board[boardEntry.getKey().getX()][boardEntry.getKey().getY()];
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
        assignEndGame.setOnMouseClicked(value -> opponentsInterfaceController.startEndGameTokenAnimationToOpponent());

        MyShelfieButton assignTokenOpponent1 = new MyShelfieButton("Opponent Token1");
        assignTokenOpponent1.setId("assignTokenOpponent1");
        assignTokenOpponent1.setOnMouseClicked(value -> opponentsInterfaceController.startTokenAnimationToOpponent1());

        MyShelfieButton assignTokenOpponent2 = new MyShelfieButton("Opponent Token2");
        assignTokenOpponent2.setId("assignTokenOpponent2");
        assignTokenOpponent2.setOnMouseClicked(value -> opponentsInterfaceController.startTokenAnimationToOpponent2());

        MyShelfieButton tilesToOpponent = new MyShelfieButton("Opponent Tiles");
        tilesToOpponent.setId("tilesToOpponent");
        //tilesToOpponent.setOnMouseClicked(value -> transferTilesToOpponent());

        gameInterfaceController.testingBox.getChildren().addAll(fillBoardButton,
                testTokenAnimation1, testTokenAnimation2, getEndGame, assignEndGame, assignTokenOpponent1,
                assignTokenOpponent2, tilesToOpponent);
    }
}
