package it.polimi.ingsw.view.gui.customcomponents.bookshelf;

import it.polimi.ingsw.model.BookShelf;
import it.polimi.ingsw.model.TileSubject;
import it.polimi.ingsw.utils.Coordinate;
import it.polimi.ingsw.view.gui.customcomponents.guitoolkit.MyShelfieAlertCreator;
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
 * @version 2.0
 * @since 15/06/2023
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
        TileSubject[][] bookshelfMatrix = new TileSubject[getMaxBookshelfRow()][getMaxBookshelfColumn()];

        bookshelfCells.forEach((coordinate, cell) -> {
            if (cell.getChildren().size() > 0 && cell.getChildren().size() == 1) {
                try {
                    bookshelfMatrix[coordinate.getX()][coordinate.getY()] = ((TileSubjectView) cell.getChildren().get(0)).getTileSubject();
                } catch (ClassCastException e) {
                    MyShelfieAlertCreator.displayErrorAlert(e);
                }
            }
        });

        return bookshelfMatrix;
    }

    /**
     * Retrieves the maximum row coordinate inside the bookshelf
     *
     * @return max row coordinate
     */
    private int getMaxBookshelfRow() {
        int maxRow = 0;

        for (Coordinate coordinate : bookshelfCells.keySet()) {
            if (coordinate.hasGraterRow(maxRow)) {
                maxRow = coordinate.getX();
            }
        }

        return (maxRow + 1);
    }

    /**
     * Retrieves the maximum column coordinate inside the bookshelf
     *
     * @return max column coordinate
     */
    private int getMaxBookshelfColumn() {
        int maxColumn = 0;

        for (Coordinate coordinate : bookshelfCells.keySet()) {
            if (coordinate.hasGraterColumn(maxColumn)) {
                maxColumn = coordinate.getY();
            }
        }

        return (maxColumn + 1);
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
