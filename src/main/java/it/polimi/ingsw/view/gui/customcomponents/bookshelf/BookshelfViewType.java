package it.polimi.ingsw.view.gui.customcomponents.bookshelf;

enum BookshelfViewType {
    PERSONAL("empty_bookshelf_cell", "bookshelfImagePane", "bookshelfGrid"),
    OPPONENT("opponent_empty_bookshelf_cell", "bookshelfOpponentImagePane", "bookshelfOpponentGrid");

    /**
     * The default class applied to the cells of the bookshelf
     * when they don't contain a tile
     *
     * @apiNote the style-class is different, according to the
     * owner of the bookshelf (different enum value)
     */
    private final String emptyCellClass;

    private final String bookshelfImagePaneId;

    /**
     * The unique ID applied on the graphical component
     * representing the bookshelf grid
     */
    private final String bookshelfGrid;

    /**
     * The default class applied to the cells of the bookshelf
     * when they contain a tile
     *
     * @apiNote the style-class is the same, according to the
     * owner of the bookshelf
     */
    private static final String FILLED_CELL_CLASS = "filled_bookshelf_cell";

    BookshelfViewType(String emptyCellClass, String bookshelfImagePaneId, String bookshelfGrid) {
        this.emptyCellClass = emptyCellClass;
        this.bookshelfImagePaneId = bookshelfImagePaneId;
        this.bookshelfGrid = bookshelfGrid;
    }

    String getEmptyCellClass() {
        return emptyCellClass;
    }

    String getFilledCellClass(){
        return FILLED_CELL_CLASS;
    }

    String getBookshelfImagePaneId() {
        return bookshelfImagePaneId;
    }

    String getBookshelfGrid() {
        return bookshelfGrid;
    }
}
