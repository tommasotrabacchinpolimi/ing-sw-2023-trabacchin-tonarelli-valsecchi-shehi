package it.polimi.ingsw.view.gui.layout.bookshelf;

import it.polimi.ingsw.model.TileSubject;
import it.polimi.ingsw.utils.Coordinate;
import it.polimi.ingsw.view.gui.customcomponents.MyShelfieTriangleButton;
import it.polimi.ingsw.view.gui.customcomponents.bookshelf.OpponentBookshelfView;
import it.polimi.ingsw.view.gui.customcomponents.tileview.TileSubjectView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
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

    public void insertTilesInOpponentBookshelf(TileSubject[][] updatedBookshelf, Map<Coordinate, TileSubjectView> takenTiles)
            throws IllegalArgumentException{
        TileSubject[][] opponentBookshelf = opponentBookshelfView.toTileSubjectMatrix();

        if(verifyArgument(updatedBookshelf, opponentBookshelf))
            throw new IllegalArgumentException();

        Map<Coordinate, StackPane> differentBookshelfCells = getDifferencesInBookshelf(updatedBookshelf, opponentBookshelf);

        if(verifyDifferencesInBookshelfSameColumn(differentBookshelfCells.keySet().stream().toList()))
            throw new IllegalArgumentException();

        selectedColumn = differentBookshelfCells.keySet().stream().toList().get(0).getY();

        insertTilesInBookshelf(takenTiles);
    }

    @Contract(pure = true)
    private boolean verifyArgument(@NotNull TileSubject[][] updatedBookshelf, @NotNull TileSubject[][] opponentBookshelf) {

        if(updatedBookshelf.length != opponentBookshelf.length)
            return false;

        for (int i = 0; i < updatedBookshelf.length; ++i) {
            for (int j = 0; j < updatedBookshelf[i].length; ++j) {
                if(updatedBookshelf[i].length != opponentBookshelf[i].length)
                    return false;
            }
        }

        return true;
    }

    @NotNull
    private Map<Coordinate, StackPane> getDifferencesInBookshelf(@NotNull TileSubject[][] updatedBookshelf,
                                                                 @NotNull TileSubject[][] opponentBookshelf)
    throws IllegalArgumentException{
        List<Coordinate> differencesInBookshelf = new ArrayList<>();

        for (int i = 0; i < opponentBookshelf.length; ++i) {
            for (int j = 0; j < opponentBookshelf[i].length; ++j) {
                if(opponentBookshelf[i][j] != updatedBookshelf[i][j])
                    differencesInBookshelf.add(new Coordinate(i, j));
            }
        }

        return opponentBookshelfView.getMapOfDifferencesInBookshelf(differencesInBookshelf);
    }

    /**
     *
     * @param differencesInBookshelf
     * @return {@code true} if the differences in the bookshelf have
     * the same column, {@code false} otherwise
     */
    private boolean verifyDifferencesInBookshelfSameColumn(@NotNull List<Coordinate> differencesInBookshelf) {
        return differencesInBookshelf.stream()
                .filter(coordinate -> differencesInBookshelf.stream()
                        .anyMatch(coordinate1 -> coordinate.getY() != coordinate1.getY()))
                .toList().size() == differencesInBookshelf.size();
    }

    @Override
    public void onGameStateChangedNotified() {

    }

    @Override
    public void onExceptionNotified() {

    }

    @Override
    void insertTilesInBookshelf(@NotNull Map<Coordinate, TileSubjectView> coordinateTiles) {
        coordinateTiles.forEach((coordinate, tile) -> {
            tile.performAction(getColumnPane(), getBookshelfCellAt(coordinate).orElseThrow());
        });
    }

    @Nullable
    @Contract(pure = true)
    private StackPane getColumnPane(){
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


}
