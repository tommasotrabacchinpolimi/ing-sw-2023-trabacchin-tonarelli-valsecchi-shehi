package it.polimi.ingsw.view.tui;

import it.polimi.ingsw.model.TileSubject;
import it.polimi.ingsw.model.TileType;
import it.polimi.ingsw.utils.Triple;
import it.polimi.ingsw.view.Client;
import it.polimi.ingsw.view.LogicInterface;
import it.polimi.ingsw.view.UI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TUI extends UI implements Runnable{
    private static final int DIM_BOARD = 9;
    private static final int DIMCOL_BOOKSHELF = 5;
    private static final int DIMROW_BOOKSHELF = 6;
    private static final char EMPTY = '-';
    private final BufferedReader bufferedReader;
    private final PrintStream out = System.out;
    private TUIState state;
    private final Object lock = new Object();

    private enum TUIState{
        HOME,
        CHAT,
        OTHERS
    }

    public TUI() {
        state = TUIState.HOME;
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void launch() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            welcome();
            lock.wait();
            while(true) {

            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    private String readLine() throws IOException {
        String input;
        synchronized (lock){
            input = bufferedReader.readLine();
        }
        return input;
    }

    private void welcome() throws IOException {
        synchronized (lock) {
            String choice;
            out.print("""
                          .         .                                                                                                                                   \s
                         ,8.       ,8.   `8.`8888.      ,8'              d888888o.   8 8888        8 8 8888888888   8 8888         8 8888888888    8 8888 8 8888888888  \s
                        ,888.     ,888.   `8.`8888.    ,8'             .`8888:' `88. 8 8888        8 8 8888         8 8888         8 8888          8 8888 8 8888        \s
                       .`8888.   .`8888.   `8.`8888.  ,8'              8.`8888.   Y8 8 8888        8 8 8888         8 8888         8 8888          8 8888 8 8888        \s
                      ,8.`8888. ,8.`8888.   `8.`8888.,8'               `8.`8888.     8 8888        8 8 8888         8 8888         8 8888          8 8888 8 8888        \s
                     ,8'8.`8888,8^8.`8888.   `8.`88888'                 `8.`8888.    8 8888        8 8 888888888888 8 8888         8 888888888888  8 8888 8 888888888888\s
                    ,8' `8.`8888' `8.`8888.   `8. 8888                   `8.`8888.   8 8888        8 8 8888         8 8888         8 8888          8 8888 8 8888        \s
                   ,8'   `8.`88'   `8.`8888.   `8 8888                    `8.`8888.  8 8888888888888 8 8888         8 8888         8 8888          8 8888 8 8888        \s
                  ,8'     `8.`'     `8.`8888.   8 8888                8b   `8.`8888. 8 8888        8 8 8888         8 8888         8 8888          8 8888 8 8888        \s
                 ,8'       `8        `8.`8888.  8 8888                `8b.  ;8.`8888 8 8888        8 8 8888         8 8888         8 8888          8 8888 8 8888        \s
                ,8'         `         `8.`8888. 8 8888                 `Y8888P ,88P' 8 8888        8 8 888888888888 8 888888888888 8 8888          8 8888 8 888888888888\s
                """);
            out.println("Welcome to My Shelfie!");
            out.println("First of all, insert your unique nickname: ");
            String nickname = readLine();
            out.println("Ok, now chose one of the following options: ");
            out.println("1) create a new game");
            out.println("2) join an existing game");
            out.println("3) rejoin an existing game after a disconnection");
            choice = readLine();
            if (choice.equals("1")) {
                out.println("So you need to let us know with how many people you want to play with (it has to be a number between 1 and 3) :");
                int numberOfPlayer = Integer.parseInt(readLine()) + 1;
                getLogicController().createGame(nickname, numberOfPlayer);
            } else if (choice.equals("2")) {
                getLogicController().joinGame(nickname);
            } else if (choice.equals("3")) {
                getLogicController().joinGame(nickname);
            } else {

            }
            out.println("We are ready to start! Wait for the other users to join...");
        }
    }

    private void home(){ //mostra le cose base
        state = TUIState.HOME;
        out.println("MY SHELFIE: HOME OF " + getModel().getThisPlayer());
        printGameState(getModel().getGameState());
        out.print("Here are all the players in the game: ");
        for(String name: getModel().getPlayers()){
            out.print(name);
            if(!Objects.equals(getModel().getPlayersState().get(name) , "CONNECTED")){
                out.print("(" + getModel().getPlayersState().get(name).toLowerCase() + ")");
            }
            out.print("   ");
        }
        out.println();
        out.println("Your points are: ");
        List<Integer> points = getModel().getPlayersPoints().get(getModel().getThisPlayer());
        out.println("Common Goal 1 = " + points.get(0) + printPointOrPoints(points.get(0)));
        out.println("Common Goal 2 = " + points.get(1) + printPointOrPoints(points.get(1)));
        out.println("End Game = " + points.get(2) + printPointOrPoints(points.get(2)));
        out.println("Personal Goal = " + points.get(3) + printPointOrPoints(points.get(3)));
        out.println("Adjacent Tiles = " + points.get(4) + printPointOrPoints(points.get(4)));

        out.println();
        out.println("Common Goals are: ");
        out.println("Common Goal 1: " + getModel().getCommonGoals()[0] + "\n     " + "Available Score = " + getModel().getAvailableScores().get(0));
        out.println("Common Goal 2: " + getModel().getCommonGoals()[1] + "\n     " + "Available Score = " + getModel().getAvailableScores().get(1));

        printBoardBookShelfPersonalGoal(fromTileSubjectToChar(getModel().getBoard()),
                fromTileSubjectToChar(getModel().getBookShelfByNickname(getModel().getThisPlayer())),
                fromTileTypeToChar(getModel().getPersonalGoal()));

        out.println("Current Player is " + getModel().getCurrentPlayer());
    }

    private void showChat(){
        state = TUIState.CHAT;
        out.println("MY SHELFIE: CHAT");
        List<Triple<String, List<String>, String>> messages = getModel().getMessages();
        for(Triple<String, List<String>, String> message : messages){
            if(message.getSecond().contains(getModel().getThisPlayer())){
                out.println(message.getFirst() + privateOrPublicMessage(message.getSecond()) + message.getThird());
            }
        }
        out.println("There aren't any more messages.");
    }

    private void showBookshelves(){
        state = TUIState.OTHERS;
        out.println("MY SHELFIE: OTHER PLAYERS' BOOKSHELVES AND POINTS");
        List<String> nicknames = getModel().getPlayers();
        if(nicknames.size() <= 2){
            while(nicknames.size()-3 < 0){
                nicknames.add("");
            }
        }
        char[][] bookShelf1 = fromTileSubjectToChar(getModel().getBookShelfByNickname(nicknames.get(0)));
        char[][] bookShelf2 = fromTileSubjectToChar(getModel().getBookShelfByNickname(nicknames.get(1)));
        char[][] bookShelf3 = fromTileSubjectToChar(getModel().getBookShelfByNickname(nicknames.get(2)));
        List<Integer> pointPlayer1 = getModel().getPlayersPoints().get(nicknames.get(0));
        List<Integer> pointPlayer2 = getModel().getPlayersPoints().get(nicknames.get(1));
        List<Integer> pointPlayer3 = getModel().getPlayersPoints().get(nicknames.get(2));
        printOthersBookShelf(nicknames.get(0), nicknames.get(1), nicknames.get(2), bookShelf1, bookShelf2, bookShelf3);
        out.println();
        printOthersPoint(nicknames.get(0), nicknames.get(1), nicknames.get(2), pointPlayer1, pointPlayer2, pointPlayer3);
    }

    private void printBoardBookShelfPersonalGoal(char[][] board, char[][] bookshelf, char[][] personalGoal){
        out.println("              Living Room Board:                           Your BookShelf:                   Your Personal Goal:");
        out.println( "     1   2   3   4   5   6   7   8   9  " );
        System.out.println(getDividerBoard(0) + "               " + getDividerBookShelf(0) + "               " + getDividerBookShelf(0));

        for( int i = 0; i < DIM_BOARD; ++i ){
            printLineBoard(i, board);

            out.print("               ");
            if(i < 6){
                printLineBookShelf(i,bookshelf);
                out.print("               ");
                printLineBookShelf(i,personalGoal);
            }
            out.println();

            if( i < DIM_BOARD - 1){
                if(i <= 6)
                    out.println(getDividerBoard(i+1) + "               "+ getDividerBookShelf(i+1) +"               "+ getDividerBookShelf(i+1) );
                else  {
                    out.println(getDividerBoard(i+1));
                }
            }
        }

        out.println(getDividerBoard(DIM_BOARD));
    }

    private String getDividerBoard(int row) {
        switch(row) {
            case 0 -> {
                return "   ┌───┬───┬───╔═══╦═══╗───┬───┬───┬───┐";
            }
            case 1 -> {
                return "   ├───┼───┼───╠═══╬═══╬═══╗───┼───┼───┤";
            }
            case 2 -> {
                return "   ├───┼───╔═══╬═══╬═══╬═══╬═══╗───┼───┤";
            }
            case 3 -> {
                return "   ├───╔═══╬═══╬═══╬═══╬═══╬═══╬═══╦═══╗";
            }
            case 4 -> {
                return "   ╔═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╣";
            }
            case 5 -> {
                return "   ╠═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╝";
            }
            case 6 -> {
                return "   ╚═══╩═══╬═══╬═══╬═══╬═══╬═══╬═══╝───┤";
            }
            case 7 -> {
                return "   ├───┼───╚═══╬═══╬═══╬═══╬═══╝───┼───┤";
            }
            case 8 -> {
                return "   ├───┼───┼───╚═══╬═══╬═══╣───┼───┼───┤";
            }
            case 9 -> {
                return "   └───┴───┴───┴───╚═══╩═══╝───┴───┴───┘";
            }
            default -> {
                return null;
            }
        }
    }

    private String getDividerBookShelf(int row){
        switch (row){
            case 0 -> { return "╔═══╦═══╦═══╦═══╦═══╗"; }
            case 1, 2, 3, 4, 5 -> { return "╠═══╬═══╬═══╬═══╬═══╣"; }
            case 6 -> { return "╚═══╩═══╩═══╩═══╩═══╝"; }
            default -> { return ""; }
        }
    }

    private void printLineBoard(int r, char[][] board){
        int n; //number of print
        int c; //starting column
        int i;
        StringBuilder result = new StringBuilder("");

        result.append((char) (r + 'A')).append("  ");

        switch (r) {
            case 0, 1, 7 -> {
                result.append("│   │   │   ");
                c = 3;
            }
            case 2, 6 -> {
                result.append("│   │   ");
                c = 2;
            }
            case 3 -> {
                result.append("│   ");
                c = 1;
            }
            case 8 -> {
                result.append("│   │   │   │   ");
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

        result.append( "║ " );

        for( i = 0; i < n; ++i ){
            result.append( board[r][c + i] );

            if( i < n - 1 )
                result.append( " ║ ");
        }

        result.append( " ║" );

        for( i += c ; i < DIM_BOARD; ++i ){
            result.append( "   │" );
        }

        out.print(result.toString());
    }

    private void printLineBookShelf(int row, char[][] matrix) {
        for(int j = 0; j < DIMCOL_BOOKSHELF; j++){
            if( j == 0 )
                out.print( "║ " + matrix[row][j] + " ║ ");
            else if( j < DIMCOL_BOOKSHELF - 1 )
                out.print( matrix[row][j] + " ║ " );
            else
                out.print( matrix[row][j] + " ║" );
        }
    }

    private void printOthersBookShelf(String nickname1, String nickname2, String nickname3, char[][] bookShelf1, char[][] bookShelf2, char[][] bookShelf3){
        out.println("                       " + nickname1 + ":                    " + nickname2 + ":                    " + nickname3 );
        out.println("                " + getDividerBookShelf(0) + "               " + getDividerBookShelf(0) + "               " + getDividerBookShelf(0));
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
        }
    }

    private void printOthersPoint(String nickname1, String nickname2, String nickname3, List<Integer> pointPlayer1, List<Integer> pointPlayer2, List<Integer> pointPlayer3){
        out.print("                 ");
        out.println(nickname1 + "        " + nickname2 + "        " + nickname3);
        //inizio
        out.print("Common Goal 1:  ");
        out.print(pointPlayer1.get(0) + " " + printPointOrPoints(pointPlayer1.get(0)) + "        ");
        out.print(pointPlayer2.get(0) + " " + printPointOrPoints(pointPlayer2.get(0)) + "        ");
        out.println(pointPlayer3.get(0) + " " + printPointOrPoints(pointPlayer3.get(0)) + "        ");
        out.print("Common Goal 2:  ");
        out.print(pointPlayer1.get(1) + " " + printPointOrPoints(pointPlayer1.get(1)) + "        ");
        out.print(pointPlayer2.get(1) + " " + printPointOrPoints(pointPlayer2.get(1)) + "        ");
        out.println(pointPlayer3.get(1) + " " + printPointOrPoints(pointPlayer3.get(1)) + "        ");
        out.print("End Game:       ");
        out.print(pointPlayer1.get(2) + " " + printPointOrPoints(pointPlayer1.get(2)) + "        ");
        out.print(pointPlayer2.get(2) + " " + printPointOrPoints(pointPlayer2.get(2)) + "        ");
        out.println(pointPlayer3.get(2) + " " + printPointOrPoints(pointPlayer3.get(2)) + "        ");
        out.print("Personal goal:  ");
        out.print(pointPlayer1.get(3) + " " + printPointOrPoints(pointPlayer1.get(3)) + "        ");
        out.print(pointPlayer2.get(3) + " " + printPointOrPoints(pointPlayer2.get(3)) + "        ");
        out.println(pointPlayer3.get(3) + " " + printPointOrPoints(pointPlayer3.get(3)) + "        ");
        out.print("Adjacent Tiles: ");
        out.print(pointPlayer1.get(4) + " " + printPointOrPoints(pointPlayer1.get(4)) + "        ");
        out.print(pointPlayer2.get(4) + " " + printPointOrPoints(pointPlayer2.get(4)) + "        ");
        out.println(pointPlayer3.get(4) + " " + printPointOrPoints(pointPlayer3.get(4)) + "        ");
    }

    private char[][] fromTileSubjectToChar(TileSubject[][] matrix){
        if(matrix == null) return null;
        char[][] result = new char[matrix.length][matrix[0].length];
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[0].length; j++){
                result[i][j] = getCharFromTileType(matrix[i][j].getTileType());
            }
        }
        return result;
    }

    private char[][] fromTileTypeToChar(TileType[][] matrix){
        if(matrix == null) return null;
        char[][] result = new char[matrix.length][matrix[0].length];
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[0].length; j++){
                result[i][j] = getCharFromTileType(matrix[i][j]);
            }
        }
        return result;
    }

    private char getCharFromTileType(TileType tileType){
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

    private void printGameState(String gameState){
        switch(gameState){
            case "INIT" -> {
                out.println("The game is starting, please wait until the other players will join the game.");
            }
            case "MID", "FINAL" -> {
                out.println("The game is in progress.");
            }
            case "SUSPENDED" -> {
                out.println("The game is suspended because there is nobody left to play besides you.");
            }
            case "END" -> {
                out.println("The game is over!");
            }
            default -> {
                out.println();
            }
        }
    }

    private String printPointOrPoints(int point){
        if(point == 0) return "point";
        if(point == -1) return "";
        return "points";
    }

    private String privateOrPublicMessage(List<String> receivers){
        if(receivers.size() == 1) return "(private)";
        return "(public)";
    }

}
