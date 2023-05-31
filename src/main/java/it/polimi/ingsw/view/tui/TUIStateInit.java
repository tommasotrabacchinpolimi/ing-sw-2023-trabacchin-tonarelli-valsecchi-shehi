package it.polimi.ingsw.view.tui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class TUIStateInit extends TUIState{

    private final PrintStream out = System.out;
    private final BufferedReader bufferedReader;




    public TUIStateInit(TUIStateMachine tuiStateMachine) {
        super(tuiStateMachine);
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    private String readLine() throws IOException {
        return bufferedReader.readLine();
    }
    public void launchUI() {

    }

    public void onNewMessage(String sender) {
        //nothing
    }

    public void onCurrentPlayerChanged(String newCurrentPlayer) {
        //nothing
    }

    public void showWinner() {
        //nothing
    }

    public void onException() {

    }

    public void onGameStateChanged() {

    }

    @Override
    protected void newLine(String line) {

    }

    public void setup() throws IOException {
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
        out.println("Now insert your unique nickname: ");
        String nickname = readLine();
        out.println("Ok, now chose one of the following options: ");
        out.println("1) create a new game");
        out.println("2) join an existing game");
        out.println("3) rejoin an existing game after a disconnection");
        choice = readLine();
        getTuiStateMachine().setTuiState(new TUIStateWaiting(getTuiStateMachine()));
        getTuiStateMachine().getTuiState().setup();
        switch (choice) {
            case "1" -> {
                out.println("So you need to choose how many people there will be in the game (it has to be a number between 2 and 4, including you) :");
                int numberOfPlayer = Integer.parseInt(readLine());
                getTuiStateMachine().getLogicController().createGame(nickname, numberOfPlayer);
            }
            case "2", "3" -> getTuiStateMachine().getLogicController().joinGame(nickname);
            default -> {
            }
        }

    }
}
