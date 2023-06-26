package it.polimi.ingsw.view.tui.page;

import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.utils.color.MyShelfieAttribute;
import it.polimi.ingsw.view.tui.TUI;

import java.io.PrintStream;
import java.util.List;
import java.util.Objects;

import static it.polimi.ingsw.utils.color.MyShelfieAnsi.colorize;

/**
 * The HomePage class represents the home page of the {@link TUI}.
 * It extends the {@link Page} class and provides methods to display the home page.
 *
 * @see it.polimi.ingsw.view.tui.page.Page
 * @see TUI
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public class HomePage extends Page{
    /**
     * Attribute that represents the {@link PrintStream output stream}.
     */
    private PrintStream out;

    /**
     * Constructs a new HomePage object with the given {@link TUI} (Text User Interface).
     * Initializes the output stream by obtaining it from the {@link TUI}.
     * @param tui the {@link TUI} object
     */
    public HomePage(TUI tui) {
        super(tui);
        this.out = tui.getPrintStream();
    }

    /**
     * Displays the home page in the {@link TUI}.
     * Shows the game state, players' information, points, common goals, board, bookshelf, personal goal, current player and unread messages.
     * @see it.polimi.ingsw.view.ViewData
     * @see Page#show()
     */
    @Override
    public void show() {
        reset();
        out.print(colorize("                                                           ", MyShelfieAttribute.DARKEN_GREEN_BACK()));
        out.print(colorize("MY SHELFIE: HOME OF " + getModel().getThisPlayer(), MyShelfieAttribute.DARKEN_GREEN_BACK()));
        out.println(colorize("                                                           ", MyShelfieAttribute.DARKEN_GREEN_BACK()));

        printGameState(getModel().getGameState());

        out.print("Here are all the players in the game: ");
        for(String name: getModel().getPlayers()){
            if(getModel().getPlayersState().get(name)==null) {
                continue;
            }
            out.print(name);
            if(!Objects.equals(getModel().getPlayersState().get(name) , "CONNECTED")){
                out.print("(" + colorize(getModel().getPlayersState().get(name).toLowerCase(), MyShelfieAttribute.ITALIC()) + ")");
            }
            out.print("   ");
        }
        out.println();

        List<Integer> points = getModel().getPlayersPoints().get(getModel().getThisPlayer());
        if(points!=null) {
            out.println(colorize("Your points: ", MyShelfieAttribute.BOLD()));
            out.println("Adjacent Tiles = " + points.get(0) + printPointOrPoints(points.get(0)));
            out.println("Common Goal 1 =  " + points.get(1) + printPointOrPoints(points.get(1)));
            out.println("Common Goal 2 =  "+ points.get(2) + printPointOrPoints(points.get(2)));
            out.println("End Game =       " + points.get(3) + printPointOrPoints(points.get(3)));
            out.println("Personal Goal =  " + points.get(4) + printPointOrPoints(points.get(4)));
            int totalScore = getModel().getTotalPointByNickname(getModel().getThisPlayer());
            out.println(colorize("Total Points", MyShelfieAttribute.BOLD()) + " =   " + totalScore + printPointOrPoints(totalScore));
        }

        if(getModel().getCommonGoals()[0]!=null || getModel().getCommonGoals()[1]!=null) {
            out.println();
            out.println(colorize("Common Goal 1: ", MyShelfieAttribute.BOLD()) + getModel().getCommonGoals()[0] + "\n     " + "Available Score = " + getModel().getAvailableScores().get(0));
            out.println(colorize("Common Goal 2: ", MyShelfieAttribute.BOLD()) + getModel().getCommonGoals()[1] + "\n     " + "Available Score = " + getModel().getAvailableScores().get(1));
        }

        out.println();
        if(getModel().getBoard()!=null && getModel().getBookShelfByNickname(getModel().getThisPlayer())!=null && getModel().getPersonalGoal()!=null) {
            printBoardBookShelfPersonalGoal(fromTileSubjectToChar(getModel().getBoard(), true),
                    fromTileSubjectToChar(getModel().getBookShelfByNickname(getModel().getThisPlayer()), false),
                    fromTileTypeToChar(getModel().getPersonalGoal()));
        }

        if(getModel().getCurrentPlayer() != null && !getModel().getGameState().equals(GameState.SUSPENDED.toString()) && !getModel().getGameState().equals(GameState.END.toString())) {
            if(getModel().getCurrentPlayer().equals(getModel().getThisPlayer())){
                out.println(colorize("It's your turn to play!", MyShelfieAttribute.BOLD(), MyShelfieAttribute.GREEN_TEXT()));
            } else {
                out.println(colorize("Current Player is " + getModel().getCurrentPlayer(), MyShelfieAttribute.BOLD(), MyShelfieAttribute.GREEN_TEXT()));
            }
        }
        if(getModel().getUnreadMessages()) {
            out.println("You have some unread messages, go to the chat to read them");
        }
        out.println(colorize("<--To learn how to play, please type 'help'.-->", MyShelfieAttribute.BOLD()));
    }

    /**
     * Overrides the {@link Page#onCurrentPlayerChanged} method from the parent class.
     * This method is called when the current player changes.
     * It calls the {@link #show()} method to display the updated home page.
     * @see Page#onCurrentPlayerChanged()
     */
    @Override
    public void onCurrentPlayerChanged() {
        this.show();
    }

    /**
     * Prints the board, bookshelf and personal goal of the player.
     * @param board the matrix representing the board
     * @param bookshelf the matrix representing the player's bookshelf
     * @param personalGoal the matrix representing the player's personal goal
     * @see it.polimi.ingsw.view.ViewData
     */
    private void printBoardBookShelfPersonalGoal(char[][] board, char[][] bookshelf, char[][] personalGoal){
        out.println("             Living Room Board:                           Your BookShelf:                   Your Personal Goal:");
        out.println("     1   2   3   4   5   6   7   8   9                   1   2   3   4   5                  " );
        out.println(getDividerBoard(0) + "               " + getDividerBookShelf(0) + "               " + getDividerPersonalGoal(0));

        for( int i = 0; i < DIM_BOARD; ++i ){
            printLineBoard(i, board);

            out.print("               ");
            if(i < 6){
                printLineBookShelf(i,bookshelf);
                out.print("               ");
                printLinePersonalGoal(i,personalGoal);
            } else if (i==6){
                out.print("                                     ");
                out.print("Personal Goal Points:");
            } else if (i==7) {
                out.print("p = PLANT\t b = BOOK \t \t \t \t p 1 | 2 | 4 | 6 | 9 | 12");
            } else {
                out.print("t = TROPHY\t c = CAT \t \t \t \t p = points achieved with relative #");
            }
            out.println();

            if( i < DIM_BOARD - 1){
                if(i < 6)
                    out.println(getDividerBoard(i+1) + "               "+ getDividerBookShelf(i+1) +"               "+ getDividerPersonalGoal(i+1) );
                else if (i==6) {
                    out.println(getDividerBoard(i+1) + "               " + "Legend:         \t \t \t \t \t \t # 1 | 2 | 3 | 4 | 5 | 6");
                } else {
                    out.println(getDividerBoard(i + 1) + "               " + "f = FRAME\t g = GAME \t \t \t \t # = numbers of matched tiles");
                }
            }
        }

        out.println(getDividerBoard(DIM_BOARD));
    }

    /**
     * Returns the divider line for the board based on the specified row number.
     * @param row the row number
     * @return the divider line for the board
     */
    private String getDividerBoard(int row) {
        switch (row) {
            case 0 -> {
                return colorize("   ┌───┬───┬───╔═══╦═══╗───┬───┬───┬───┐", MyShelfieAttribute.TEXT_COLOR(245, 245, 246));
            }
            case 1 -> {
                return colorize("   ├───┼───┼───╠═══╬═══╬═══╗───┼───┼───┤", MyShelfieAttribute.TEXT_COLOR(245, 245, 246));
            }
            case 2 -> {
                return colorize("   ├───┼───╔═══╬═══╬═══╬═══╬═══╗───┼───┤", MyShelfieAttribute.TEXT_COLOR(245, 245, 246));
            }
            case 3 -> {
                return colorize("   ├───╔═══╬═══╬═══╬═══╬═══╬═══╬═══╦═══╗", MyShelfieAttribute.TEXT_COLOR(245, 245, 246));
            }
            case 4 -> {
                return colorize("   ╔═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╣", MyShelfieAttribute.TEXT_COLOR(245, 245, 246));
            }
            case 5 -> {
                return colorize("   ╠═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╝", MyShelfieAttribute.TEXT_COLOR(245, 245, 246));
            }
            case 6 -> {
                return colorize("   ╚═══╩═══╬═══╬═══╬═══╬═══╬═══╬═══╝───┤", MyShelfieAttribute.TEXT_COLOR(245, 245, 246));
            }
            case 7 -> {
                return colorize("   ├───┼───╚═══╬═══╬═══╬═══╬═══╝───┼───┤", MyShelfieAttribute.TEXT_COLOR(245, 245, 246));
            }
            case 8 -> {
                return colorize("   ├───┼───┼───╚═══╬═══╬═══╣───┼───┼───┤", MyShelfieAttribute.TEXT_COLOR(245, 245, 246));
            }
            case 9 -> {
                return colorize("   └───┴───┴───┴───╚═══╩═══╝───┴───┴───┘", MyShelfieAttribute.TEXT_COLOR(245, 245, 246));
            }
            default -> {
                return null;
            }
        }
    }

    /**
     * Returns a divider line for the personal goal based on the specified row.
     * @param row The row number for which the divider line is generated.
     * @return The divider line as a formatted string.
     */
    private String getDividerPersonalGoal(int row){
        switch (row){
            case 0 -> { return colorize("┌───┬───┬───┬───┬───┐", MyShelfieAttribute.TEXT_COLOR(245,245,246)); }
            case 1, 2, 3, 4, 5 -> { return colorize("├───┼───┼───┼───┼───┤", MyShelfieAttribute.TEXT_COLOR(245,245,246)); }
            case 6 -> { return colorize("└───┴───┴───┴───┴───┘", MyShelfieAttribute.TEXT_COLOR(245,245,246)); }
            default -> { return ""; }
        }
    }

    /**
     * Prints a single line of the board based on the specified row and the board matrix.
     * @param r     The row number to be printed.
     * @param board The char matrix representing the board.
     */
    private void printLineBoard(int r, char[][] board){
        int n; //number of print
        int c; //starting column
        int i;
        StringBuilder result = new StringBuilder("");

        result.append((char) (r + 'A')).append("  ");

        switch (r) {
            case 0, 1, 7 -> {
                result.append(colorize("│   │   │   ", MyShelfieAttribute.TEXT_COLOR(245, 245, 246)));
                c = 3;
            }
            case 2, 6 -> {
                result.append(colorize("│   │   ", MyShelfieAttribute.TEXT_COLOR(245, 245, 246)));
                c = 2;
            }
            case 3 -> {
                result.append(colorize("│   ", MyShelfieAttribute.TEXT_COLOR(245, 245, 246)));
                c = 1;
            }
            case 8 -> {
                result.append(colorize("│   │   │   │   ", MyShelfieAttribute.TEXT_COLOR(245, 245, 246)));
                c = 4;
            }
            default -> {
                result.append("");
                c = 0;
            }
        }
        switch (r) {
            case 0, 8 -> n = 2;
            case 1, 7 -> n = 3;
            case 2, 6 -> n = 5;
            case 3, 5 -> n = 8;
            default -> n = 9;
        }
        result.append( colorize("║", MyShelfieAttribute.TEXT_COLOR(245,245,246)) );
        for( i = 0; i < n; ++i ){
            result.append( toPrintChar(board[r][c + i]) );
            if( i < n - 1 )
                result.append(colorize("║", MyShelfieAttribute.TEXT_COLOR(245,245,246)));
        }

        result.append( colorize("║", MyShelfieAttribute.TEXT_COLOR(245,245,246)) );

        for( i += c ; i < DIM_BOARD; ++i ){
            result.append( colorize("   │", MyShelfieAttribute.TEXT_COLOR(245,245,246)) );
        }

        out.print(result.toString());
    }

    /**
     * Prints a single line of the personal goal based on the specified row and the matrix representing the personal goal.
     * @param row    The row number to be printed.
     * @param matrix The char matrix representing the personal goal matrix.
     */
    private void printLinePersonalGoal(int row, char[][] matrix){
        if(matrix!=null) {
            for (int j = 0; j < DIMCOL_BOOKSHELF; j++) {
                if (j == 0)
                    out.print(colorize("│", MyShelfieAttribute.TEXT_COLOR(245, 245, 246)) + toPrintChar(matrix[row][j]) + colorize("│", MyShelfieAttribute.TEXT_COLOR(245, 245, 246)));
                else if (j < DIMCOL_BOOKSHELF - 1)
                    out.print(toPrintChar(matrix[row][j]) + colorize("│", MyShelfieAttribute.TEXT_COLOR(245, 245, 246)));
                else
                    out.print(toPrintChar(matrix[row][j]) + colorize("│", MyShelfieAttribute.TEXT_COLOR(245, 245, 246)));
            }
        }
    }
}
