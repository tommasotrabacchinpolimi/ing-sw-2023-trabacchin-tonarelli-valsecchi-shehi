package it.polimi.ingsw.view.gui.layout.bookshelf;

import it.polimi.ingsw.model.TileSubject;
import it.polimi.ingsw.utils.Coordinate;
import it.polimi.ingsw.view.gui.customcomponents.bookshelf.OpponentBookshelfView;
import it.polimi.ingsw.view.gui.customcomponents.commongoal.ScoringTokenView;
import it.polimi.ingsw.view.gui.customcomponents.pointpane.SquarePointPane;
import it.polimi.ingsw.view.gui.customcomponents.tileview.TileSubjectView;
import it.polimi.ingsw.view.gui.customcomponents.tileview.TileViewInOpponent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URL;
import java.util.*;

import static it.polimi.ingsw.utils.color.MyShelfieColor.BONE;

public class SingleOpponentBookShelfController extends BookshelfController {

    @FXML
    private SquarePointPane singleOpponentPointPane;

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

    private int selectedColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bookshelfCells = opponentBookshelfView.getBookshelfCells();
    }

    public void setOpponentBookshelfName(String playerName) {
        opponentBookshelfName.setText(playerName);

        opponentBookshelfName.setStyle("-fx-background-color: " + BONE.getLightenRGBAStyleSheet(0.10) + ";" +
                "-fx-border-radius: 0.3em;" +
                "-fx-font-family: 'Special Elite Regular';" +
                "-fx-wrap-text: true;" +
                "-fx-text-alignment: center;" +
                "-fx-font-size: 1.7em;" +
                "-fx-text-fill: " + BONE.getRGBAStyleSheet(0.93) + ";" +
                "-fx-padding: 0.5em 0.1em 0.7em 0.1em;");
    }

    public void insertTilesInOpponentBookshelf(TileSubject[][] updatedBookshelf, List<TileSubjectView> takenTiles) throws IllegalArgumentException {
        TileSubject[][] opponentBookshelf = opponentBookshelfView.toTileSubjectMatrix();

        if (!verifyUpdatedBookshelf(updatedBookshelf, opponentBookshelf))
            throw new IllegalArgumentException();

        Map<Coordinate, StackPane> differentBookshelfCells = getDifferencesInBookshelf(updatedBookshelf, opponentBookshelf);

        if (!verifyDifferencesInBookshelfSameColumn(differentBookshelfCells.keySet().stream().toList()))
            throw new IllegalArgumentException();

        if(differentBookshelfCells.size() > 0) {
            selectedColumn = differentBookshelfCells.keySet()
                    .stream()
                    .toList()
                    .get(0)
                    .getY();

            insertTilesInBookshelf(mapToTilesDifferences(differentBookshelfCells, updatedBookshelf, takenTiles));
        }
    }

    /**
     * Verify if the {@code updatedBookshelf} and {@code opponentBookshelf}
     * are compatible
     *
     * @param updatedBookshelf  the bookshelf that is verified to be
     *                          compatible with the actual bookshelf
     * @param opponentBookshelf the actual opponent bookshelf that is
     *                          considered to be "solid" (correct)
     * @return {@code true} if the two bookshelves are compatible,
     * {@code false} otherwise
     */
    @Contract(pure = true)
    private boolean verifyUpdatedBookshelf(@NotNull TileSubject[][] updatedBookshelf, @NotNull TileSubject[][] opponentBookshelf) {

        if (updatedBookshelf.length != opponentBookshelf.length)
            return false;

        for (int i = 0; i < updatedBookshelf.length; ++i) {
            for (int j = 0; j < updatedBookshelf[i].length; ++j) {
                if (updatedBookshelf[i].length != opponentBookshelf[i].length)
                    return false;
            }
        }

        return true;
    }

    /**
     * Retrieves the differences in the opponent bookshelf and
     * the updated bookshelf
     *
     * @param updatedBookshelf
     * @param opponentBookshelf
     * @return
     * @throws IllegalArgumentException
     */
    @NotNull
    private Map<Coordinate, StackPane> getDifferencesInBookshelf(@NotNull TileSubject[][] updatedBookshelf,
                                                                 @NotNull TileSubject[][] opponentBookshelf)
            throws IllegalArgumentException {

        List<Coordinate> differencesInBookshelf = new ArrayList<>();

        for (int i = 0; i < opponentBookshelf.length; ++i) {
            for (int j = 0; j < opponentBookshelf[i].length; ++j) {
                if (opponentBookshelf[i][j] != updatedBookshelf[i][j])
                    differencesInBookshelf.add(new Coordinate(i, j));
            }
        }

        return opponentBookshelfView.getMapOfDifferencesInBookshelf(differencesInBookshelf);
    }

    /**
     * @param differencesInBookshelf
     * @return {@code true} if the differences in the bookshelf have
     * the same column, {@code false} otherwise
     */
    private boolean verifyDifferencesInBookshelfSameColumn(@NotNull List<Coordinate> differencesInBookshelf) {
        return differencesInBookshelf.stream()
                .filter(coordinate -> differencesInBookshelf.stream()
                        .anyMatch(coordinate1 -> coordinate.getY() == coordinate1.getY()))
                .toList().size() == differencesInBookshelf.size();
    }

    private Map<Coordinate, TileSubjectView> mapToTilesDifferences(Map<Coordinate, StackPane> differentBookshelfCells,
                                                                   TileSubject[][] updatedBookshelf,
                                                                   List<TileSubjectView> takenTiles) {
        Map<Coordinate, TileSubjectView> differenceInTiles = new HashMap<>();

        differentBookshelfCells.forEach((key, value) -> {
            TileSubjectView takenTile = popEqualsFromTakenTiles(takenTiles, updatedBookshelf[key.getX()][key.getY()]);

            if(takenTile != null)
                differenceInTiles.put(key, takenTile);
        });

        return differenceInTiles;
    }

    /**
     *
     * @param takenTiles
     * @param updatedTile
     * @return {@code null} if no match to the element is found
     */
    @Nullable
    private TileSubjectView popEqualsFromTakenTiles(@NotNull List<TileSubjectView> takenTiles, TileSubject updatedTile) {
        TileSubjectView takenTile = takenTiles.stream()
                .filter(tile -> tile.isEqualTileSubject(updatedTile))
                .findFirst()
                .orElse(null);

        takenTiles.remove(takenTile);

        return takenTile;
    }

    /**
     * @param tile
     * @param row
     * @param column
     * @throws NoSuchElementException if the bookshelf has not a cell at the specified position
     */
    @Override
    public void forcedInsertionInBookshelf(TileSubject tile, int row, int column) throws NoSuchElementException {
        TileSubjectView newTile = new TileSubjectView(tile, new TileViewInOpponent());
        getBookshelfCellAt(row, column).orElseThrow().getChildren().add(newTile);
    }

    @Override
    void insertTilesInBookshelf(@NotNull Map<Coordinate, TileSubjectView> coordinateTiles) {
        coordinateTiles.forEach((coordinate, tile) -> {
            tile.toOpponentBookShelf(getColumnPane(), getBookshelfCellAt(coordinate).orElseThrow());
        });
    }

    @Nullable
    @Contract(pure = true)
    private StackPane getColumnPane() {
        switch (selectedColumn) {
            case 0 -> {
                return leftPane;
            }
            case 1 -> {
                return midLeftPane;
            }
            case 2 -> {
                return midPane;
            }
            case 3 -> {
                return midRightPane;
            }
            case 4 -> {
                return rightPane;
            }

            default -> {
                return null;
            }
        }
    }

    public Pane getSingleOpponentFirstPlayerSeatCell() {
        return singleOpponentPointPane.getFirstPlayerSeatCell();
    }

    public Pane getSingleOpponentEndTokenCell() {
        return singleOpponentPointPane.getEndTokenCell();
    }

    public Pane getSingleOpponentFirstScoringTokenCell() {
        return singleOpponentPointPane.getFirstScoringTokenCell();
    }

    public Pane getSingleOpponentSecondScoringTokenCell() {
        return singleOpponentPointPane.getSecondScoringTokenCell();
    }

    //For testing
    public TileSubject[][] getOpponentBookshelf(){
        return opponentBookshelfView.toTileSubjectMatrix();
    }

    public void forceDisplayCommonGoalScore(List<Integer> playerPoints) {
        if(playerPoints.get(1) != 0) {
            getSingleOpponentFirstScoringTokenCell().getChildren().add(new ScoringTokenView(playerPoints.get(1)));
        }

        if(playerPoints.get(2) != 0) {
            getSingleOpponentSecondScoringTokenCell().getChildren().add(new ScoringTokenView(playerPoints.get(1)));
        }
    }
}
