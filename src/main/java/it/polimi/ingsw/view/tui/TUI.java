package it.polimi.ingsw.view.tui;

import it.polimi.ingsw.view.Client;
import it.polimi.ingsw.view.LogicInterface;
import it.polimi.ingsw.view.UI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class TUI extends UI implements Runnable{
    private static final int DIM_BOARD = 9;
    private static final int DIMCOL_BOOKSHELF = 5;
    private static final char EMPTY = '-';
    private final BufferedReader bufferedReader;
    private final PrintStream out = System.out;
    private TUIState state;
    private LogicInterface client;

    private enum TUIState{
        HOME,
        CHAT,
        BOOKSHELVES,
        POINTS
    }

    public TUI() {
        state = TUIState.HOME;
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }
    private final Object lock = new Object();
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
        //current player
        out.println("Current Player is ");
        //players+ players state
        //Player points
        //common goals
        //printBoardBookShelfPersonalGoal
    }

    private void showChat(){

    }

    private void showPoints(){

    }

    private void showBookshelves(){

    }

    private void printBoardBookShelfPersonalGoal(char[][] board, char[][] bookshelf, char[][] personalGoal){
        out.println("              Living Room Board:                           Your BookShelf:                   Your Personal Goal:");
        out.println( "     1   2   3   4   5   6   7   8   9  " );
        out.println( "   ┌───┬───┬───╔═══╦═══╗───┬───┬───┬───┐" );

        for( int i = 0; i < DIM_BOARD; ++i ){
            out.print("     ");

            for( int j = 0; j < DIM_BOARD; ++j){
                if( j == 0 )
                    out.print( "║ " + board[i][j] + " ║ ");
                else if( j < DIM_BOARD - 1 )
                    out.print( board[i][j] + " ║ " );
                else
                    out.print( board[i][j] + " ║" );
            }

            out.print("               ");
            if(i >= 3){
                for(int j = 0; j < DIMCOL_BOOKSHELF; j++){
                    if( j == 0 )
                        out.print( "║ " + bookshelf[i][j] + " ║ ");
                    else if( j < DIMCOL_BOOKSHELF - 1 )
                        out.print( bookshelf[i][j] + " ║ " );
                    else
                        out.print( bookshelf[i][j] + " ║" );
                }
                out.print("               ");
                for(int j = 0; j < DIMCOL_BOOKSHELF; j++){
                    if( j == 0 )
                        System.out.print( "║ " + personalGoal[i][j] + " ║ ");
                    else if( j < DIMCOL_BOOKSHELF - 1 )
                        System.out.print( personalGoal[i][j] + " ║ " );
                    else
                        System.out.print( personalGoal[i][j] + " ║" );
                }
            }
            System.out.println();

            if( i < DIM_BOARD - 1 ){
                if(i==2)
                    System.out.println("     ╠═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╣               ╔═══╦═══╦═══╦═══╦═══╗               ╔═══╦═══╦═══╦═══╦═══╗");
                else if (i < 2) {
                    System.out.println("     ╠═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╣");
                }
                else {
                    System.out.println("     ╠═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╣               ╠═══╬═══╬═══╬═══╬═══╣               ╠═══╬═══╬═══╬═══╬═══╣");
                }
            }
        }

        System.out.println("     ╚═══╩═══╩═══╩═══╩═══╩═══╩═══╩═══╩═══╝               ╚═══╩═══╩═══╩═══╩═══╝               ╚═══╩═══╩═══╩═══╩═══╝");
    }

    private void printSingleBookShelf(char[][] bookShelf1,String nickname1, char[][] bookShelf2,String nickname2, char[][] bookShelf3, String nickname3){

    }

    private char[][] fromTileSubjectToChar(){
        return null;
    }

    private char[][] fromTileTypeToChar(){
        return null;
    }

}
