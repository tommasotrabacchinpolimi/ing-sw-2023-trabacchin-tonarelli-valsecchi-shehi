package it.polimi.ingsw.view.tui.input.setup;

import it.polimi.ingsw.view.tui.TUI;
import it.polimi.ingsw.view.tui.input.Input;

import java.io.PrintStream;

public class AskPlayersNumber extends Input {

    private final String nickname;

    public AskPlayersNumber(TUI tui, PrintStream out, String nickname) {
        super(tui, out);
        this.nickname = nickname;
    }

    @Override
    public void printPrompt() {
        getOut().println("So you need to choose how many people there will be in the game (it has to be a number between 2 and 4, including you) :");
    }

    @Override
    public void readLine(String line) {
        if(line.equals("2") || line.equals("3") || line.equals("4")) {
            getTUI().setCurrentInput(new AskWait(getTUI(), getOut()));
            getTUI().getLogicController().createGame(nickname, Integer.parseInt(line));
            getTUI().getModel().setThisPlayer(nickname);
        }
        else {
            getOut().println("Invalid choice, please try again...");
            getTUI().setCurrentInput(new AskPlayersNumber(getTUI(), getOut(), nickname));
        }
    }
}
