package it.polimi.ingsw.view.gui.customcomponents;

import it.polimi.ingsw.model.BookShelf;
import it.polimi.ingsw.utils.Coordinate;
import it.polimi.ingsw.view.gui.customcomponents.decorations.MyShelfieComponent;
import it.polimi.ingsw.view.gui.customcomponents.decorations.MyShelfieDecoration;
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
 * <p>The graphical component representing the bookshelf</p>
 *
 * @version 1.0
 * @since 10/06/2023
 */
public class BookshelfView extends StackPane implements MyShelfieComponent {

    /**
     * The Cascading-Style-Sheet path to the file containing
     * the customization of the bookshelf
     */
    private static final String CSS_FILE_PATH = "/it.polimi.ingsw/gui/layout/bookshelf/Bookshelf.css";

    /**
     * The default class applied to the cells of the bookshelf
     * when they don't contain a tile
     */
    private static final String EMPTY_CELL = "empty_bookshelf_cell";

    /**
     * The default class applied to the cells of the bookshelf
     * when they contain a tile
     */
    private static final String FILLED_CELL = "filled_bookshelf_cell";

    /**
     * The unique ID applied on the graphical component
     * representing the bookshelf grid
     */
    private static final String GRID_PANE_ID = "bookshelfGrid";

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
    private Map<Coordinate, StackPane> bookshelfCells;

    /**
     * Construct a graphical bookshelf component with the
     * standard number of {@linkplain BookShelf#STANDARD_ROW row}
     * and {@linkplain BookShelf#STANDARD_COLUMN column}
     *
     * @see BookShelf
     */
    public BookshelfView() {
        this(BookShelf.STANDARD_ROW, BookShelf.STANDARD_COLUMN);
    }

    /**
     * Construct a graphical bookshelf component with the
     * number of {@code row} and {@code column} specified
     *
     * @see BookShelf
     */
    private BookshelfView(int row, int column) {
        super();

        getStylesheets().add(Objects.requireNonNull(getClass().getResource(CSS_FILE_PATH)).toExternalForm());

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
     * Initialize and retrieves a tile's container
     * for the {@linkplain #bookshelfCells bookshelf}
     * that by default:
     * <ul>
     *     <li><p>will have an ID format as follows:
     *     {@code cell + row + column};</p>
     *     <p>where:</p>
     *     <ul>
     *         <li>cell: is a standard string</li>
     *         <li>row: is a number representing
     *         the row in which the element is
     *         placed</li>
     *         <li>column: is a number representing
     *         the column in which the element is
     *         placed</li>
     *     </ul>
     *     </li>
     *     <li>a style class that corresponds to
     *     the {@value #EMPTY_CELL} constant</li>
     *     <li>a listener that is used to updated
     *     the style class of the cell based on
     *     it's children
     *     ({@link #setupCellListener(StackPane)
     *     click for more information})</li>
     * </ul>
     *
     * @param cellId the unique ID to set at the
     *               bookshelf's cell
     * @return a tile's container for the
     * {@linkplain #bookshelfCells bookshelf}
     *
     * @apiNote <p>The ID of the bookshelf's cell
     * returned can be omitted, but it is very
     * unwise</p>
     * <p>More than one ID can be specified but
     * only the first one will be considered for
     * setting the ID to the cell</p>
     */
    @NotNull
    private StackPane initBookShelfCell(String... cellId) {
        StackPane cell = new StackPane();

        Arrays.stream(cellId).findFirst().ifPresent(cell::setId);

        cell.getStyleClass().add(EMPTY_CELL);
        setupCellListener(cell);

        return cell;
    }

    /**
     * <p>Add a listener at the {@code cell} passed
     * as parameter.</p>
     * <p>The listener is used to switch between
     * the two different style classes for the
     * {@linkplain #bookshelfCells}
     * ({@value #EMPTY_CELL} and
     * {@value #FILLED_CELL})</p>
     *
     * @param cell the bookshelf cell on which
     *             apply the listener
     */
    private void setupCellListener(@NotNull StackPane cell) {
        cell.getChildren().addListener((ListChangeListener<? super Node>) node -> {
            while(node.next()) {
                if (node.wasRemoved()) {
                    cell.getStyleClass().remove(FILLED_CELL);
                    cell.getStyleClass().add(EMPTY_CELL);
                }

                if(node.wasAdded()){

                    cell.getStyleClass().remove(EMPTY_CELL);
                    cell.getStyleClass().add(FILLED_CELL);

                    node.getList()
                            .stream()
                            .map(element -> (TileSubjectView) element)
                            .forEach(TileSubjectView::disable);
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

        gridPane.setId(GRID_PANE_ID);

        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(100.0);
        columnConstraints.setHalignment(HPos.CENTER);
        columnConstraints.setFillWidth(true);

        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setPercentHeight(100.0);
        rowConstraints.setValignment(VPos.CENTER);
        rowConstraints.setFillHeight(true);

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
        bookshelfImagePane.setId("bookshelfImagePane");

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
