package it.polimi.ingsw.view.gui.customcomponents.bookshelf;

import it.polimi.ingsw.utils.Coordinate;
import javafx.scene.layout.StackPane;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OpponentBookshelfView extends BookshelfView {

    public OpponentBookshelfView() {
        super(BookshelfViewType.OPPONENT);
    }

    public Map<Coordinate, StackPane> getMapOfDifferencesInBookshelf(List<Coordinate> differencesInBookshelf) {
        return bookshelfCells.entrySet()
                .stream()
                .filter(entry -> isContainedInDifferencesBookshelf(differencesInBookshelf, entry))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * Verify if the (single) bookshelfCell passed as argument is
     * contained in the coordinates that are changed
     *
     * @param differencesInBookshelf the list of coordinates that are
     *                               changed
     * @param bookshelfCell          a single pair of coordinate and
     *                               bookshelf cell that needs to be
     *                               checked if it is changed
     * @return {@code true} if the {@code bookshelfCell} is part of
     * the cells that are changed in the bookshelf, {@code false}
     * otherwise
     */
    private boolean isContainedInDifferencesBookshelf(@NotNull List<Coordinate> differencesInBookshelf,
                                                      @NotNull Map.Entry<Coordinate, StackPane> bookshelfCell) {
        return differencesInBookshelf.stream()
                .anyMatch(coordinate -> bookshelfCell.getKey().equals(coordinate));
    }
}
