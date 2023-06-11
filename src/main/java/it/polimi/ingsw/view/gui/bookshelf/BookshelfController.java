package it.polimi.ingsw.view.gui.bookshelf;

import it.polimi.ingsw.utils.Coordinate;
import it.polimi.ingsw.view.gui.MyShelfieController;
import it.polimi.ingsw.view.gui.customcomponents.MyShelfieAlertCreator;
import it.polimi.ingsw.view.gui.customcomponents.tileview.TileSubjectView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * @version 2.0
 * @since 10/06/2023
 */
abstract class BookshelfController extends MyShelfieController{

    /**
     * An object that maps the coordinates of the bookshelf
     * to each container that can hold a tile.
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

    protected Optional<Map<Coordinate, StackPane>> getFreeBookshelfCells() {
        Map<Coordinate, StackPane> freeCells = new HashMap<>();

        bookshelfCells.forEach((coordinate, cell) -> {
            if(cell.getChildren().size() == 0)
                freeCells.put(coordinate, cell);
        });

        return (freeCells.size() > 0) ? Optional.of(freeCells) : Optional.empty();
    }

    public Optional<Map<Coordinate, StackPane>> getFreeBookshelfCellsInColumn(int column) {
        Map<Coordinate, StackPane> freeColumnCells = new HashMap<>();

        getFreeBookshelfCells().ifPresent(m -> {
            m.forEach((coordinate, cell) -> {
                if(coordinate.getY() == column) {
                    freeColumnCells.put(coordinate, cell);
                }
            });
        });

        return (freeColumnCells.size() > 0) ? Optional.of(freeColumnCells) : Optional.empty();
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

    public void insertTilesInBookshelf(List<TileSubjectView> tiles, int column) {
        getFreeBookshelfCellsInColumn(column).ifPresentOrElse( map -> {
            if(map.size() < tiles.size()) {
                MyShelfieAlertCreator.displayErrorAlert(
                        "You have selected " + tiles.size() + " tiles, but the " + (column + 1) +
                                "° column can only contain " + map.size() + " tiles",
                        "Exceeded max column capability"
                );
            } else {
                insertTilesInBookshelf(tiles, quicksortRowCoordinates(new ArrayList<>(map.keySet())).stream().limit(tiles.size()).toList());
            }
        }, () -> {MyShelfieAlertCreator.displayErrorAlert(
                "The " + (column + 1) + "° column is full you can't insert any tiles in it",
                "Column is full");});
    }

    private List<Coordinate> quicksortRowCoordinates(List<Coordinate> coordinates) {
        if (coordinates == null) {
            return null;
        }else if (coordinates.size() <= 1) {
            return coordinates;
        }
        quicksortRowRecursive(coordinates, 0, coordinates.size() - 1);

        return coordinates;
    }

    private void quicksortRowRecursive(List<Coordinate> coordinates, int low, int high) {
        if (low < high) {
            int pivotIndex = partitionRow(coordinates, low, high);
            quicksortRowRecursive(coordinates, low, pivotIndex - 1);
            quicksortRowRecursive(coordinates, pivotIndex + 1, high);
        }
    }

    private int partitionRow(@NotNull List<Coordinate> coordinates, int low, int high) {
        Coordinate pivot = coordinates.get(high);
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (coordinates.get(j).getX() > pivot.getX()) {
                i++;
                Collections.swap(coordinates, i, j);
            }
        }

        Collections.swap(coordinates, i + 1, high);

        return i + 1;
    }

    abstract void insertTilesInBookshelf(@NotNull Map<Coordinate, TileSubjectView> coordinateTiles);
}
