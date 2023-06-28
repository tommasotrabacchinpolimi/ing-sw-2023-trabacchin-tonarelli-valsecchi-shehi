package it.polimi.ingsw.view.gui.customcomponents.bookshelf;

import it.polimi.ingsw.model.BookShelf;
import it.polimi.ingsw.model.TileSubject;
import it.polimi.ingsw.utils.Coordinate;
import it.polimi.ingsw.view.gui.customcomponents.guitoolkit.MyShelfieAlertCreator;
import it.polimi.ingsw.view.gui.customcomponents.decorations.MyShelfieComponent;
import it.polimi.ingsw.view.gui.customcomponents.decorations.MyShelfieDecoration;
import it.polimi.ingsw.view.gui.customcomponents.guitoolkit.MyShelfieMatrixComponent;
import it.polimi.ingsw.view.gui.customcomponents.tileview.TileSubjectView;
import javafx.collections.ListChangeListener;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.*;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * The `BookshelfView` class represents a graphical component that displays a bookshelf in a board game.
 * It provides a visual representation of the bookshelf and allows customization of its appearance.
 * The bookshelf is composed of multiple cells that can hold tiles.
 * <p>
 * Version: 2.0
 * Since: 15/06/2023
 * </p>
 *
 * <p>
 * The bookshelf view is an abstract class and should be extended to create specific bookshelf types.
 * </p>
 *
 * <p>
 * The bookshelf view supports the following features:
 * <ul>
 *   <li>Customizable bookshelf appearance using CSS</li>
 *   <li>Automatic binding of coordinates to bookshelf cells</li>
 *   <li>Dynamic updating of cell style based on tile presence</li>
 *   <li>Conversion to a matrix representation for data storage</li>
 *   <li>Customizable decorations for the bookshelf component</li>
 * </ul>
 * </p>
 *
 * <p>
 * Example usage:
 * <pre>{@code
 * BookshelfView bookshelf = new ConcreteBookshelfView();
 * // Customize the appearance of the bookshelf using CSS
 * bookshelf.getStylesheets().add("path/to/custom.css");
 * // Add the bookshelf component to a parent container
 * parentContainer.getChildren().add(bookshelf);
 * }</pre>
 * </p>
 *
 * <p>
 * The bookshelf view requires a CSS file for customization, which can be set using the `CSS_FILE_PATH` constant.
 * </p>
 *
 * <p>
 * The bookshelf view provides methods for accessing and manipulating the bookshelf cells and converting the bookshelf into a matrix representation.
 * </p>
 *
 * @version 2.0
 * @since 15/06/2023
 * @see BookshelfViewType
 * @see MyShelfieDecoration
 * @see TileSubjectView
 * @see BookShelf
 * @see Coordinate
 * @see MyShelfieComponent
 * @see MyShelfieAlertCreator
 * @see MyShelfieDecoration
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
abstract class BookshelfView extends StackPane implements MyShelfieComponent {

    /**
     * The Cascading-Style-Sheet path to the file containing
     * the customization of the bookshelf
     */
    private static final String CSS_FILE_PATH = "/it.polimi.ingsw/gui/layout/bookshelf/Bookshelf.css";

    /**
     * List that contains a series of default decorations
     * applied on a component
     *
     * @see MyShelfieDecoration
     */
    private final List<MyShelfieDecoration> baseDecorations = new ArrayList<>();

    /**
     * An object that maps the coordinates of the bookshelf
     * to each container that can hold a tile.
     */
    protected Map<Coordinate, StackPane> bookshelfCells;

    /**
     * A type that qualifies the bookshelf
     *
     * @see BookshelfViewType
     */
    private final BookshelfViewType bookshelfType;

    /**
     * Construct a graphical bookshelf component with the
     * standard number of {@linkplain BookShelf#STANDARD_ROW row}
     * and {@linkplain BookShelf#STANDARD_COLUMN column}
     *
     * @param bookshelfType the different bookshelf type
     *
     * @see BookShelf
     */
    public BookshelfView(BookshelfViewType bookshelfType) {
        this(BookShelf.STANDARD_ROW, BookShelf.STANDARD_COLUMN, bookshelfType);
    }

    /**
     * Construct a graphical bookshelf component with the
     * number of {@code row} and {@code column} specified
     *
     * @see BookShelf
     */
     private BookshelfView(int row, int column, BookshelfViewType bookshelfType) {
        super();

        this.bookshelfType = bookshelfType;

        getStylesheets().add(Objects.requireNonNull(getMyShelfieResource(CSS_FILE_PATH)).toExternalForm());

        setupBookshelfMap(row, column);

        setupBookshelfGrid(row, column);

        setupBookshelfImage();
    }

    /**
     * Used to create the bindings between bookshelf's
     * coordinate and tile container for the real
     * {@linkplain #bookshelfCells bookshelf}
     */
    private void setupBookshelfMap(int row, int column) {
        bookshelfCells = new HashMap<>();

        for(int i = 0; i < row; ++i) {
            for(int j = 0; j < column; ++j) {
                bookshelfCells.put(new Coordinate(i, j), initBookShelfCell("cell" + i + j));
            }
        }
    }

    /**
     * Initialize and retrieves a tile's container for the
     * {@linkplain #bookshelfCells bookshelf} that by default:
     * <ul>
     *     <li><p>will have an ID format as follows:
     *     {@code cell + row + column};</p>
     *     <p>where:</p>
     *      <ul>
     *          <li>cell: is a standard string</li>
     *          <li>row: is a number representing the row in
     *          which the element is placed</li>
     *          <li>column: is a number representing the column
     *          in which the element is placed</li>
     *      </ul>
     *     </li>
     *     <li>a style class that corresponds to the
     *     {@linkplain  BookshelfViewType} chosen</li>
     *     <li>a listener that is used to updated the style class
     *     of the cell based on it's children
     *     ({@link #setupCellListener(StackPane) click for more
     *     information})</li>
     * </ul>
     *
     * @param cellId the unique ID to set at the bookshelf's cell
     * @return a tile's container for the
     * {@linkplain #bookshelfCells bookshelf}
     *
     * @apiNote <p>The ID of the bookshelf's cell returned can be
     * omitted, but it is very unwise</p>
     * <p>More than one ID can be specified but only the first one
     * will be considered for setting the ID to the cell</p>
     */
    @NotNull
    private StackPane initBookShelfCell(String... cellId) {
        StackPane cell = new StackPane();

        Arrays.stream(cellId).findFirst().ifPresent(cell::setId);

        cell.getStyleClass().add(bookshelfType.getEmptyCellClass());
        setupCellListener(cell);

        return cell;
    }

    /**
     * <p>Add a listener at the {@code cell} passed as parameter.</p>
     * <p>The listener is used to switch between different
     * {@linkplain #bookshelfCells bookshelf-cell}'s style
     * according to the different
     * {@linkplain BookshelfViewType bookshelf-type}</p>
     *
     * @param cell the bookshelf cell on which applies the listener
     */
    private void setupCellListener(@NotNull StackPane cell) {
        cell.getChildren().addListener((ListChangeListener<? super Node>) node -> {
            while(node.next()) {
                if (node.wasRemoved()) {
                    cell.getStyleClass().remove(bookshelfType.getFilledCellClass());
                    cell.getStyleClass().add(bookshelfType.getEmptyCellClass());
                }

                if(node.wasAdded()){

                    cell.getStyleClass().remove(bookshelfType.getEmptyCellClass());
                    cell.getStyleClass().add(bookshelfType.getFilledCellClass());

                    node.getList()
                            .stream()
                            .map(element -> (TileSubjectView) element)
                            .forEach(TileSubjectView::disableClick);
                }
            }
        });
    }

    /**
     * Construct the real bookshelf graphical components
     * that will hold the {@link TileSubjectView tiles}
     *
     * @param row the bookshelf's row number
     * @param column the bookshelf's column number
     */
    private void setupBookshelfGrid(int row, int column) {
        GridPane gridPane = new GridPane();

        gridPane.setId(bookshelfType.getBookshelfGrid());

        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(100.0);
        columnConstraints.setHalignment(HPos.CENTER);
        columnConstraints.setFillWidth(false);

        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setPercentHeight(100.0);
        rowConstraints.setValignment(VPos.CENTER);
        rowConstraints.setFillHeight(false);

        for(int i = 0; i < column; ++i)
            gridPane.getColumnConstraints().add(columnConstraints);

        for(int j = 0; j < row; ++j)
            gridPane.getRowConstraints().add(rowConstraints);

        bookshelfCells.forEach((coordinate, cell) -> {
            gridPane.add(cell, coordinate.getY(), coordinate.getX());
        });

        getChildren().add(gridPane);
        StackPane.setAlignment(gridPane, Pos.CENTER);
    }

    /**
     * Applies the bookshelf image
     * to the graphical component
     */
    private void setupBookshelfImage() {
        Pane bookshelfImagePane = new Pane();
        bookshelfImagePane.setId(bookshelfType.getBookshelfImagePaneId());

        getChildren().add(bookshelfImagePane);
    }

    /**
     * Retrieves the real
     * {@linkplain #bookshelfCells bookshelf}
     * that maps the coordinates to the
     * containers
     *
     * @return a map of coordinates and
     * bookshelf's container
     */
    public Map<Coordinate, StackPane> getBookshelfCells() {
        return bookshelfCells;
    }

    /**
     * Convert and retrieve the graphical Bookshelf
     * in a matrix coherent to the
     * {@linkplain BookShelf bookshelf} used to store
     * data
     *
     * @return a matrix representing the graphical bookshelf
     */
    public TileSubject[][] toTileSubjectMatrix() {
        return MyShelfieMatrixComponent.toTileSubjectMatrix(bookshelfCells);
    }

    /**
     * Retrieve the element that has to be customized
     *
     * @return the element that needs to be customized
     */
    @Override
    public Node getCustomizedNode() {
        return this;
    }

    /**
     * Retrieves the set of default decorations
     * that are applied on a customized
     * {@linkplain MyShelfieComponent component}
     *
     * @return the set
     */
    @Override
    public List<MyShelfieDecoration> getBaseDecorations() {
        return baseDecorations;
    }
}
