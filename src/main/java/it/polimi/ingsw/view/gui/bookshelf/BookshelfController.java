package it.polimi.ingsw.view.gui.bookshelf;

import it.polimi.ingsw.utils.Coordinate;
import it.polimi.ingsw.view.gui.MyShelfieController;
import it.polimi.ingsw.view.gui.customcomponents.MyShelfieAlertCreator;
import it.polimi.ingsw.view.gui.customcomponents.tileview.TileSubjectView;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.*;

import static it.polimi.ingsw.utils.color.MyShelfieColor.KOBICHA;

/**
 * @version 2.0
 * @since 10/06/2023
 */
abstract class BookshelfController extends MyShelfieController{

    /**
     *
     */
    Map<Coordinate, StackPane> bookshelfCells;

    /**
     * Given a bookshelf cell that is a child of
     * the graphical component representing the bookshelf
     * view, it returns the coordinate that corresponds
     * to that cell
     *
     * @param bookshelfCell the bookshelf cell whose
     *                      position one wants to know
     * @return the coordinates corresponding to the cell
     * given if the cell given as parameter is inside the
     * graphical component representing the bookshelf,
     * an empty {@code Optional} otherwise.
     */
    protected Optional<Coordinate> getBookshelfCellCoordinate(StackPane bookshelfCell) {
        if(GridPane.getRowIndex(bookshelfCell) != null && GridPane.getColumnIndex(bookshelfCell) != null)
            return Optional.of(new Coordinate(GridPane.getRowIndex(bookshelfCell), GridPane.getColumnIndex(bookshelfCell)));

        return Optional.empty();
    }

    /**
     * Retrieves the bookshelf cell at the given coordinate
     *
     * @param coordinate the coordinate that has to contain
     *                   a bookshelf cell
     * @return the bookshelf cell if the coordinate given
     * is in the real {@linkplain #bookshelfCells bookshelf},
     * an empty {@code Optional} otherwise.
     */
    protected Optional<StackPane> getBookshelfCellAt(Coordinate coordinate) {
        for (Coordinate c : bookshelfCells.keySet()) {
            if (c.equals(coordinate)) {
                return Optional.of(bookshelfCells.get(c));
            }
        }

        return Optional.empty();
    }

    /**
     * Retrieves the bookshelf cell at the given row and
     * column
     *
     * @param row the row at which search the bookshelf cell
     * @param column the column at which search the bookshelf cell
     * @return the bookshelf cell if the row and column index given
     * is in the real {@linkplain #bookshelfCells bookshelf},
     * an empty {@code Optional} otherwise.
     */
    protected Optional<StackPane> getBookshelfCellAt(int row, int column) {
        return getBookshelfCellAt(new Coordinate(row, column));
    }

    protected void insertTilesInBookshelf(@NotNull List<TileSubjectView> tiles, @NotNull List<Coordinate> coordinates) {
        if (tiles.size() != coordinates.size()) {
            MyShelfieAlertCreator.displayErrorAlert(
                    "You have specified a different amount of tiles than expected",
                    "Bookshelf can't contain these tiles"
            );

            return;
        }

        Map<Coordinate, TileSubjectView> coordinateTiles = new HashMap<>();

        for(int i = 0; i < tiles.size(); ++i) {
            coordinateTiles.put(coordinates.get(i), tiles.get(i));
        }

        insertTilesInBookshelf(coordinateTiles);
    }

    public void insertTilesInBookshelf(@NotNull List<TileSubjectView> tiles, @NotNull Coordinate... coordinates) {
        insertTilesInBookshelf(tiles, Arrays.asList(coordinates));
    }

    abstract void insertTilesInBookshelf(@NotNull Map<Coordinate, TileSubjectView> coordinateTiles);
}
