package it.polimi.ingsw.view.tui;

import it.polimi.ingsw.view.UI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class TUI extends UI implements Runnable{
    private static final int DIM = 9;
    private BufferedReader bufferedReader;
    private PrintStream out = System.out;

    public TUI() {
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
            System.out.print("""
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

    private void clear(){ //mostra le cose base
        //current player
        //players+ paylers state
        //Player points
        //common goals
        //board
        //bookshelf
        //personal goal
    }

    private void showChat(){

    }

    private void showPoints(){

    }

    private void showBookshelves(){

    }

}
