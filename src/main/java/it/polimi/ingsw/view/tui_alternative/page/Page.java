package it.polimi.ingsw.view.tui_alternative.page;

import it.polimi.ingsw.model.BoardSquareType;
import it.polimi.ingsw.model.TileSubject;
import it.polimi.ingsw.model.TileType;
import it.polimi.ingsw.utils.color.MyShelfieAttribute;
import it.polimi.ingsw.view.ViewData;
import it.polimi.ingsw.view.tui_alternative.TUI;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.model.BoardSquareType.*;
import static it.polimi.ingsw.model.BoardSquareType.THREE_DOTS;
import static it.polimi.ingsw.utils.color.MyShelfieAnsi.colorize;

public abstract class Page {


    private PrintStream out;

    private TUI tui;

    protected static final int DIM_BOARD = 9;
    protected static final int DIMCOL_BOOKSHELF = 5;
    protected static final int DIMROW_BOOKSHELF = 6;
    protected static final char EMPTY = '-';

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

    protected Page(TUI tui) {
        this.tui = tui;
        out = tui.getPrintStream();
    }

    public abstract void show();

    public ViewData getModel() {
        return tui.getModel();
    }

    public void onCurrentPlayerChanged() {
        out.println("There is a new player, please refresh the page pressing enter");
    }

    public void onNewMessage() {
        out.println("There is a new message from " + getModel().getLastMessage().getFirst());
    }

    public void onException() {
        out.println(getModel().getException());
    }

    /**
        @author Melanie Tonarelli
     **/
    public void reset() {
        out.println("\033[2J");
    }

    /**
     * @author Melanie Tonarelli
     * @param point
     * @return
     */
    protected String printPointOrPoints(int point){
        if(point == 1) return " point ";
        if(point == -1) return "";
        return " points";
    }

    /**
     * @author Melanie Tonarelli
     * @param row
     * @return
     */
    protected String getDividerBookShelf(int row){
        switch (row){
            case 0 -> { return colorize("╔═══╦═══╦═══╦═══╦═══╗", MyShelfieAttribute.TEXT_COLOR(245,245,246)); }
            case 1, 2, 3, 4, 5 -> { return colorize("╠═══╬═══╬═══╬═══╬═══╣", MyShelfieAttribute.TEXT_COLOR(245,245,246)); }
            case 6 -> { return colorize("╚═══╩═══╩═══╩═══╩═══╝", MyShelfieAttribute.TEXT_COLOR(245,245,246)); }
            default -> { return ""; }
        }
    }

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

    protected void printPoint(List<Integer> point, int index){
        if(point != null)
            out.print(point.get(index) + " " + printPointOrPoints(point.get(index)) + "               ");
    }

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

    protected List<String> getOtherPlayer(){
        List<String> nicknames = new ArrayList<>(getModel().getPlayers().stream().filter(n -> !n.equals(getModel().getThisPlayer())).toList());
        while(nicknames.size()-3 < 0){
            nicknames.add("");
        }
        return nicknames;
    }

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

    protected List<Integer> getOtherTotalPoints(){
        List<String> nicknames = getOtherPlayer();
        List<Integer> totals = new ArrayList<>();
        totals.add(getModel().getTotalPointByNickname(nicknames.get(0)));
        totals.add(getModel().getTotalPointByNickname(nicknames.get(1)));
        totals.add(getModel().getTotalPointByNickname(nicknames.get(2)));
        return totals;
    }

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
