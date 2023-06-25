package it.polimi.ingsw.view.tui.input.setup;

import it.polimi.ingsw.view.tui.TUI;
import it.polimi.ingsw.view.tui.input.Input;

import java.io.PrintStream;

public class AskCreateOrJoin extends Input {

    private final String nickname;

    public AskCreateOrJoin(TUI tui, PrintStream out, String nickname) {
        super(tui, out);
        this.nickname = nickname;
    }

    @Override
    public void printPrompt() {
        getOut().println("Ok, now chose one of the following options: ");
        getOut().println("1) create a new game");
        getOut().println("2) join an existing game");
        getOut().println("3) rejoin an existing game after a disconnection");
    }

    @Override
    public void readLine(String line) {
        if(line.equals("1") || line.equals("2") || line.equals("3")) {
            if(line.equals("1")) {
                getTUI().setCurrentInput(new AskPlayersNumber(getTUI(), getOut(), nickname));
            }
            else {
                getTUI().setCurrentInput(new AskWait(getTUI(), getOut()));
                getTUI().getLogicController().joinGame(nickname);
                getTUI().getModel().setThisPlayer(nickname);
            }
        }
        else {
            getOut().println("Invalid choice, please try again...");
            getTUI().setCurrentInput(new AskCreateOrJoin(getTUI(), getOut(), nickname));
        }
    }
}
