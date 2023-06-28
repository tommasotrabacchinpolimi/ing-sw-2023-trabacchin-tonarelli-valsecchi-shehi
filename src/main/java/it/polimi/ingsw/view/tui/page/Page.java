package it.polimi.ingsw.view.tui.page;

import it.polimi.ingsw.model.BoardSquareType;
import it.polimi.ingsw.model.TileSubject;
import it.polimi.ingsw.model.TileType;
import it.polimi.ingsw.utils.color.MyShelfieAttribute;
import it.polimi.ingsw.view.ViewData;
import it.polimi.ingsw.view.tui.TUI;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.model.BoardSquareType.*;
import static it.polimi.ingsw.model.BoardSquareType.THREE_DOTS;
import static it.polimi.ingsw.utils.color.MyShelfieAnsi.colorize;

/**
 * The Page class represents an abstract base class for different pages in the {@link TUI}.
 * It provides common functionality and defines abstract methods that need to be implemented by subclasses.
 *
 * @see TUI
 * @see ViewData
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public abstract class Page {
    /**
     * Attribute that represents the {@link PrintStream output stream}.
     */
    private PrintStream out;
    /**
     * Attribute that represents {@link TUI} instance associated with this page.
     */
    private TUI tui;
    /**
     * Constant that represents the number of rows and columns of the square board.
     */
    protected static final int DIM_BOARD = 9;
    /**
     * Constant that represents the number of columns of each bookshelf.
     */
    protected static final int DIMCOL_BOOKSHELF = 5;
    /**
     * Constant that represents the number of rows of each bookshelf.
     */
    protected static final int DIMROW_BOOKSHELF = 6;
    /**
     * Constant that represents an empty cell in the board.
     */
    protected static final char EMPTY = '-';
    /**
     * Constant that represents an initial matrix configuration.
     */
    private static final BoardSquareType[][] INIT_MATRIX = {
            {null, null, null, THREE_DOTS, FOUR_DOTS, null, null, null, null},
            {null, null, null, NO_DOTS, NO_DOTS, FOUR_DOTS, null, null, null},
            {null, null, THREE_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, THREE_DOTS, null, null},
            {null, FOUR_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, THREE_DOTS},
            {FOUR_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, FOUR_DOTS},
            {THREE_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, FOUR_DOTS, null},
            {null, null, THREE_DOTS, NO_DOTS, NO_DOTS, NO_DOTS, THREE_DOTS, null, null},
            {null, null, null, FOUR_DOTS, NO_DOTS, NO_DOTS, null, null, null},
            {null, null, null, null, FOUR_DOTS, THREE_DOTS, null, null, null}
    };

    /**
     * Constructs a new Page object with the specified {@link TUI} (Text-based User Interface) instance.
     * @param tui The {@link TUI} instance associated with this page.
     * @see TUI
     */
    protected Page(TUI tui) {
        this.tui = tui;
        out = tui.getPrintStream();
    }

    /**
     * Displays the content of the page.
     * This method needs to be implemented by subclasses.
     */
    public abstract void show();

    /**
     * Retrieves the {@link ViewData model} associated with the page.
     * @return The {@link ViewData} object representing the model.
     * @see ViewData
     */
    public ViewData getModel() {
        return tui.getModel();
    }

    /**
     * Notifies the page that the current player has changed.
     * This method is called when there is a new player in the game.
     */
    public void onCurrentPlayerChanged() {
        out.println("There is a new player, please refresh the page pressing enter");
    }

    /**
     * Notifies the page that a new message has been received.
     * This method is called when there is a new message from another player.
     */
    public void onNewMessage() {
        if(!getModel().getLastMessage().getFirst().equals(getModel().getThisPlayer())) {
            out.println("There is a new message from " + getModel().getLastMessage().getFirst());
        }

    }

    /**
     * Notifies the page about an exception in the model.
     * This method is called when an exception occurs in the model.
     */
    public void onException() {
        out.println(getModel().getException());
    }

    /**
     * Resets the page by clearing the console.
     */
    public void reset() {
        out.println("\033[2J");
    }

    /**
     * Prints the word {@code point} or {@code points} based on the given point.
     * @param point The number of points.
     * @return The string representation of the points.
     */
    protected String printPointOrPoints(int point){
        if(point == 1) return " point ";
        if(point == -1) return "";
        return " points";
    }

    /**
     * Returns the divider string for the specified row in the bookshelf.
     *
     * @param row The row number.
     * @return The divider string for the specified row.
     */
    protected String getDividerBookShelf(int row){
        switch (row){
            case 0 -> { return colorize("╔═══╦═══╦═══╦═══╦═══╗", MyShelfieAttribute.TEXT_COLOR(245,245,246)); }
            case 1, 2, 3, 4, 5 -> { return colorize("╠═══╬═══╬═══╬═══╬═══╣", MyShelfieAttribute.TEXT_COLOR(245,245,246)); }
            case 6 -> { return colorize("╚═══╩═══╩═══╩═══╩═══╝", MyShelfieAttribute.TEXT_COLOR(245,245,246)); }
            default -> { return ""; }
        }
    }

    /**
     * Prints a line of the bookshelf at the specified row using the given matrix.
     *
     * @param row    The row number.
     * @param matrix The character matrix representing the bookshelf.
     */
    protected void printLineBookShelf(int row, char[][] matrix) {
        if(matrix!=null) {
            for (int j = 0; j < DIMCOL_BOOKSHELF; j++) {
                if (j == 0)
                    out.print(colorize("║", MyShelfieAttribute.TEXT_COLOR(245, 245, 246)) + toPrintChar(matrix[row][j]) + colorize("║", MyShelfieAttribute.TEXT_COLOR(245, 245, 246)));
                else if (j < DIMCOL_BOOKSHELF - 1)
                    out.print(toPrintChar(matrix[row][j]) + colorize("║", MyShelfieAttribute.TEXT_COLOR(245, 245, 246)));
                else
                    out.print(toPrintChar(matrix[row][j]) + colorize("║", MyShelfieAttribute.TEXT_COLOR(245, 245, 246)));
            }
        }
    }

    /**
     * Converts the specified character to a formatted string representation of a tile.
     * @param c The character to convert.
     * @return The formatted string representation of a tile.
     */
    protected String toPrintChar(char c) {
        switch (c) {
            case EMPTY -> {
                return " " + c + " ";
            }
            case 't' -> {
                return colorize(" " + c + " ", MyShelfieAttribute.CYAN_BACK());
            }
            case 'f' -> {
                return colorize(" " + c + " ", MyShelfieAttribute.BLUE_BACK());
            }
            case 'c' -> {
                return colorize(" " + c + " ", MyShelfieAttribute.GREEN_BACK());
            }
            case 'g' -> {
                return colorize(" " + c + " ", MyShelfieAttribute.YELLOW_BACK());
            }
            case 'p' -> {
                return colorize(" " + c + " ", MyShelfieAttribute.MAGENTA_BACK());
            }
            case 'b' -> {
                return colorize(" " + c + " ", MyShelfieAttribute.WHITE_BACK());
            }
            default -> {
                return "   ";
            }
        }
    }

    /**
     * Converts a matrix of {@link TileSubject} objects to a matrix of characters.
     * @param matrix The matrix of {@link TileSubject} objects to convert.
     * @param board  A boolean value indicating if the conversion is for the game board.
     * @return The converted matrix of characters.
     * @see TileSubject
     */
    protected char[][] fromTileSubjectToChar(TileSubject[][] matrix, boolean board){
        if(matrix == null) return null;
        char[][] result = new char[matrix.length][matrix[0].length];
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[0].length; j++){
                if (matrix[i][j] == null && INIT_MATRIX[i][j] == null && board) {
                    result[i][j] = ' ';
                } else if (matrix[i][j] == null && INIT_MATRIX[i][j] != null && board) {
                    result[i][j] = EMPTY;
                } else if (matrix[i][j] == null && !board) {
                    result[i][j] = EMPTY;
                } else {
                    result[i][j] = getCharFromTileType(matrix[i][j].getTileType());
                }
            }
        }
        return result;
    }

    /**
     * Converts a matrix of {@link TileType} objects to a matrix of characters.
     *
     * @param matrix The matrix of {@link TileType} objects to convert.
     * @return The converted matrix of characters.
     * @see TileType
     */
    protected char[][] fromTileTypeToChar(TileType[][] matrix){
        if(matrix == null) return null;
        char[][] result = new char[matrix.length][matrix[0].length];
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[0].length; j++){
                if(matrix[i][j]==null){
                    result[i][j]=EMPTY;
                } else {
                    result[i][j] = getCharFromTileType(matrix[i][j]);
                }
            }
        }
        return result;
    }

    /**
     * Converts a {@link TileType} object to its corresponding character representation.
     * @param tileType The {@link TileType} to convert.
     * @return The character representation of the {@link TileType}.
     * @see TileType
     */
    protected char getCharFromTileType(TileType tileType){
        switch (tileType){
            case CAT -> { return 'c'; }
            case PLANT -> { return 'p'; }
            case FRAME -> { return 'f'; }
            case BOOK -> { return 'b'; }
            case TROPHY -> { return 't'; }
            case GAME -> { return 'g'; }
            default -> { return EMPTY; }
        }
    }

    /**
     * Prints the specified point value from a list of points at the given index.
     * @param point The list of points.
     * @param index The index of the point to print.
     *
     * @see it.polimi.ingsw.model.PointPlayer
     */
    protected void printPoint(List<Integer> point, int index){
        if(point != null)
            out.print(point.get(index) + " " + printPointOrPoints(point.get(index)) + "               ");
    }

    /**
     * Prints the game state message.
     * @param gameState The current game state.
     *
     * @see it.polimi.ingsw.model.GameState
     */
    protected void printGameState(String gameState){
        switch(gameState){
            case "INIT" -> {
                out.println("The game is starting, please wait until the other players will join the game.");
            }
            case "MID" -> {
                out.println("The game is in progress.");
            }
            case "SUSPENDED" -> {
                out.println("The game is suspended because there is nobody left to play besides you.");
            }
            case "FINAL" -> {
                out.println("The game is ending, this is the last round of turns.");
            }
            case "END" -> {
                out.println("The game is over!");
            }
            default -> {
                out.println();
            }
        }
    }

    /**
     * Retrieves the list of nicknames for the other players in the game.
     * @return The list of nicknames.
     */
    protected List<String> getOtherPlayer(){
        List<String> nicknames = new ArrayList<>(getModel().getPlayers().stream().filter(n -> !n.equals(getModel().getThisPlayer())).toList());
        while(nicknames.size()-3 < 0){
            nicknames.add("");
        }
        return nicknames;
    }

    /**
     * Retrieves the points of the other players in the game.
     * @return The list of lists containing the points for each player.
     *
     */
    protected List<List<Integer>> getOtherPoints(){
        List<String> nicknames = getOtherPlayer();
        List<List<Integer>> points = new ArrayList<>();
        List<Integer> pointPlayer1 = getModel().getPlayersPointsByNickname(nicknames.get(0));
        List<Integer> pointPlayer2 = getModel().getPlayersPointsByNickname(nicknames.get(1));
        List<Integer> pointPlayer3 = getModel().getPlayersPointsByNickname(nicknames.get(2));
        points.add(pointPlayer1);
        points.add(pointPlayer2);
        points.add(pointPlayer3);
        return points;
    }

    /**
     * Retrieves the total points of the other players in the game.
     * @return The list of total points for each player.
     */
    protected List<Integer> getOtherTotalPoints(){
        List<String> nicknames = getOtherPlayer();
        List<Integer> totals = new ArrayList<>();
        totals.add(getModel().getTotalPointByNickname(nicknames.get(0)));
        totals.add(getModel().getTotalPointByNickname(nicknames.get(1)));
        totals.add(getModel().getTotalPointByNickname(nicknames.get(2)));
        return totals;
    }

    /**
     * Retrieves the bookshelves of the other players in the game.
     * @return The list of bookshelves as matrices of characters.
     */
    protected List<char[][]> getOtherBookShelves(){
        List<String> nicknames = getOtherPlayer();
        List<char[][]> bookshelves = new ArrayList<>();
        char[][] bookShelf1 = fromTileSubjectToChar(getModel().getBookShelfByNickname(nicknames.get(0)), false);
        char[][] bookShelf2 = fromTileSubjectToChar(getModel().getBookShelfByNickname(nicknames.get(1)), false);
        char[][] bookShelf3 = fromTileSubjectToChar(getModel().getBookShelfByNickname(nicknames.get(2)), false);
        bookshelves.add(bookShelf1);
        bookshelves.add(bookShelf2);
        bookshelves.add(bookShelf3);
        return bookshelves;
    }

    /**
     * Prints the bookshelves of other players along with their nicknames.
     *
     * @param nickname1    The nickname of the first player.
     * @param nickname2    The nickname of the second player.
     * @param nickname3    The nickname of the third player.
     * @param bookShelf1   The bookshelf of the first player.
     * @param bookShelf2   The bookshelf of the second player.
     * @param bookShelf3   The bookshelf of the third player.
     */
    protected void printOthersBookShelf(String nickname1, String nickname2, String nickname3, char[][] bookShelf1, char[][] bookShelf2, char[][] bookShelf3){
        out.print("               " + nickname1);
        // 21 space for divider
        int l = 20;
        while( l - nickname1.length() >= 0){
            out.print(" ");
            l--;
        }
        out.print( "               " + nickname2);
        l = 20;
        while( l - nickname2.length() >= 0){
            out.print(" ");
            l--;
        }
        out.println("               " + nickname3);

        if(bookShelf1==null && bookShelf2==null && bookShelf3==null){
            out.println("There aren't other players yet.");
        }
        if(bookShelf1!=null) {
            out.print("               " + getDividerBookShelf(0) + "               ");
        }
        if(bookShelf2!=null){
            out.print(getDividerBookShelf(0) + "               ");
        }
        if(bookShelf3!=null) {
            out.print(getDividerBookShelf(0));
        }
        out.println();
        for(int i = 0; i < DIMROW_BOOKSHELF; i++){
            if(bookShelf1!=null){
                out.print("               ");
                printLineBookShelf(i, bookShelf1);
            }
            if(bookShelf2!=null){
                out.print("               ");
                printLineBookShelf(i, bookShelf2);
            }
            if(bookShelf3!=null){
                out.print("               ");
                printLineBookShelf(i, bookShelf3);
            }

            out.println();

            if(bookShelf1!=null){
                out.print("               " + getDividerBookShelf(i+1));
            }
            if(bookShelf2!=null){
                out.print("               " + getDividerBookShelf(i+1));
            }
            if(bookShelf3!=null){
                out.print("               " + getDividerBookShelf(i+1));
            }
            out.println();
        }
    }

    /**
     * Prints the points of other players along with their nicknames.
     *
     * @param nickname1     The nickname of the first player.
     * @param nickname2     The nickname of the second player.
     * @param nickname3     The nickname of the third player.
     * @param pointPlayer1  The points of the first player.
     * @param pointPlayer2  The points of the second player.
     * @param pointPlayer3  The points of the third player.
     * @param total1        The total points of the first player.
     * @param total2        The total points of the second player.
     * @param total3        The total points of the third player.
     */
    protected void printOthersPoint(String nickname1, String nickname2, String nickname3, List<Integer> pointPlayer1, List<Integer> pointPlayer2, List<Integer> pointPlayer3, Integer total1, Integer total2, Integer total3){
        out.print("                 " + nickname1);
        int l = 9;
        while( l - nickname1.length() > 0){
            out.print(" ");
            l--;
        }
        out.print("               " + nickname2);
        l = 9;
        while( l - nickname2.length() > 0){
            out.print(" ");
            l--;
        }
        out.println("               " + nickname3);
        out.print("Adjacent Tiles:  ");
        printPoint(pointPlayer1, 0);
        printPoint(pointPlayer2, 0);
        printPoint(pointPlayer3, 0);
        out.println();
        out.print("Common Goal 1:   ");
        printPoint(pointPlayer1, 1);
        printPoint(pointPlayer2, 1);
        printPoint(pointPlayer3, 1);
        out.println();
        out.print("Common Goal 2:   ");
        printPoint(pointPlayer1, 2);
        printPoint(pointPlayer2, 2);
        printPoint(pointPlayer3, 2);
        out.println();
        out.print("End Game:        ");
        printPoint(pointPlayer1, 3);
        printPoint(pointPlayer2, 3);
        printPoint(pointPlayer3, 3);
        out.println();
        out.print("Personal goal:   ");
        printPoint(pointPlayer1, 4);
        printPoint(pointPlayer2, 4);
        printPoint(pointPlayer3, 4);
        out.println();
        out.print(colorize("Total points:    ", MyShelfieAttribute.BOLD()));
        if(total1 != null) {
            out.print(total1 + " " + printPointOrPoints(total1) + "               ");
        }
        if(total2 != null) {
            out.print(total2 + " " + printPointOrPoints(total2) + "               ");
        }
        if(total3 != null) {
            out.print(total3 + " " + printPointOrPoints(total3));
        }
        out.println();
    }

}
