package it.polimi.ingsw.view.gui.customcomponents.bookshelf;

import it.polimi.ingsw.utils.Coordinate;
import javafx.scene.layout.StackPane;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The `OpponentBookshelfView` class extends the `BookshelfView` class and represents a graphical component that displays an opponent's bookshelf in a board game.
 * It provides a visual representation of the opponent's bookshelf and inherits the customization and functionality of the base bookshelf view.
 * <p>
 * Example usage:
 * <pre>{@code
 * OpponentBookshelfView opponentBookshelf = new OpponentBookshelfView();
 * // Add the opponent's bookshelf component to a parent container
 * parentContainer.getChildren().add(opponentBookshelf);
 * }</pre>
 * </p>
 *
 * <p>
 * The opponent's bookshelf view is a specialized bookshelf view that supports the following features:
 * <ul>
 *   <li>Customizable bookshelf appearance using CSS</li>
 *   <li>Automatic binding of coordinates to bookshelf cells</li>
 *   <li>Dynamic updating of cell style based on tile presence</li>
 *   <li>Conversion to a matrix representation for data storage</li>
 *   <li>Customizable decorations for the bookshelf component</li>
 *   <li>Additional method to retrieve a map of specific bookshelf cells based on differences</li>
 * </ul>
 * </p>
 *
 * <p>
 * The `OpponentBookshelfView` class provides a method `getMapOfDifferencesInBookshelf` to retrieve a map of bookshelf cells based on a list of coordinates representing the differences in the bookshelf.
 * This can be useful to highlight specific cells that have changed in the opponent's bookshelf.
 * </p>
 *
 * @see BookshelfView
 * @see BookshelfViewType
 * @see Coordinate

 *
 * @since 15/06/2023
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public class OpponentBookshelfView extends BookshelfView {

    /**
     * Constructs an instance of the `OpponentBookshelfView` class.
     * It sets the bookshelf type to `BookshelfViewType.OPPONENT`.
     */
    public OpponentBookshelfView() {
        super(BookshelfViewType.OPPONENT);
    }

    /**
     * Retrieves a map of bookshelf cells that correspond to the differences in the bookshelf.
     * <br>
     * The differences are determined based on a list of coordinates that have changed in the bookshelf.
     *
     * @param differencesInBookshelf the list of coordinates that have changed in the bookshelf
     * @return a map of bookshelf cells that correspond to the differences in the bookshelf
     */
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
