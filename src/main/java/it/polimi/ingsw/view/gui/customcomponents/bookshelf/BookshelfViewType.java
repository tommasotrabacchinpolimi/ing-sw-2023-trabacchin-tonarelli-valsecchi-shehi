package it.polimi.ingsw.view.gui.customcomponents.bookshelf;

/**
 * The BookshelfViewType enum represents the different types of bookshelf views in the GUI.
 * It defines the style classes, IDs, and grid representations for each bookshelf view type.
 * Each bookshelf view type can be either PERSONAL or OPPONENT.
 * <p>
 * The style class and ID of the cells in the bookshelf grid depend on the view type.
 * When a cell is empty, it uses the emptyCellClass, and when it contains a tile, it uses FILLED_CELL_CLASS.
 * <p>
 * The bookshelfImagePaneId is the unique ID applied to the graphical component representing the bookshelf grid.
 * The bookshelfGrid is the unique ID applied to the grid itself.
 *
 * @apiNote The style class for empty cells and the bookshelfImagePaneId are different for each owner of the bookshelf.
 * @apiNote The FILLED_CELL_CLASS style class is the same for all bookshelf view types.
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 *
 */
enum BookshelfViewType {
    /**
     * Represents a personal bookshelf view.
     * The default class applied to the cells of the bookshelf when they don't contain a tile is "empty_bookshelf_cell".
     * The style-class of the bookshelfImagePane is "bookshelfImagePane".
     * The unique ID applied to the bookshelf grid is "bookshelfGrid".
     */
    PERSONAL("empty_bookshelf_cell", "bookshelfImagePane", "bookshelfGrid"),

    /**
     * Represents an opponent bookshelf view.
     * The default class applied to the cells of the bookshelf when they don't contain a tile is "opponent_empty_bookshelf_cell".
     * The style-class of the bookshelfImagePane is "bookshelfOpponentImagePane".
     * The unique ID applied to the bookshelf grid is "bookshelfOpponentGrid".
     */
    OPPONENT("opponent_empty_bookshelf_cell", "bookshelfOpponentImagePane", "bookshelfOpponentGrid");

    /**
     * The default class applied to the cells of the bookshelf when they don't contain a tile.
     */
    private final String emptyCellClass;

    /**
     * The unique ID applied to the graphical component representing the bookshelf grid.
     */
    private final String bookshelfImagePaneId;

    /**
     * The unique ID applied to the bookshelf grid.
     */
    private final String bookshelfGrid;

    /**
     * The default class applied to the cells of the bookshelf when they contain a tile.
     */
    private static final String FILLED_CELL_CLASS = "filled_bookshelf_cell";

    /**
     * Constructs a BookshelfViewType enum constant with the specified parameters.
     *
     * @param emptyCellClass       the default class applied to the cells of the bookshelf when they don't contain a tile
     * @param bookshelfImagePaneId the unique ID applied to the graphical component representing the bookshelf grid
     * @param bookshelfGrid        the unique ID applied to the bookshelf grid
     */
    BookshelfViewType(String emptyCellClass, String bookshelfImagePaneId, String bookshelfGrid) {
        this.emptyCellClass = emptyCellClass;
        this.bookshelfImagePaneId = bookshelfImagePaneId;
        this.bookshelfGrid = bookshelfGrid;
    }

    /**
     * Returns the default class applied to the cells of the bookshelf when they don't contain a tile.
     *
     * @return the empty cell class
     */
    String getEmptyCellClass() {
        return emptyCellClass;
    }

    /**
     * Returns the default class applied to the cells of the bookshelf when they contain a tile.
     *
     * @return the filled cell class
     */
    String getFilledCellClass() {
        return FILLED_CELL_CLASS;
    }

    /**
     * Returns the unique ID applied to the graphical component representing the bookshelf grid.
     *
     * @return the bookshelf image pane ID
     */
    String getBookshelfImagePaneId() {
        return bookshelfImagePaneId;
    }

    /**
     * Returns the unique ID applied to the bookshelf grid.
     *
     * @return the bookshelf grid ID
     */
    String getBookshelfGrid() {
        return bookshelfGrid;
    }
}
