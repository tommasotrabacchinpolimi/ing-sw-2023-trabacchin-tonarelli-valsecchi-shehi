package it.polimi.ingsw.view.tui_alternative.input;

import it.polimi.ingsw.view.tui_alternative.TUI;
import it.polimi.ingsw.view.tui_alternative.input.setup.AskNickname;

import java.io.PrintStream;

public class AskConfirm extends Input{
    public AskConfirm(TUI tui, PrintStream out) {
        super(tui, out);
        getTUI().setInputInProgress(true);
    }

    @Override
    public void printPrompt() {
        getOut().println("Press enter to start a new game, quit to exit the app");
    }

    @Override
    public void readLine(String line) {
        if(line.equals("")) {
            getTUI().setCurrentInput(new AskNickname(getTUI(), getOut()));
        }
        else if(line.equals("quit")) {
            System.exit(0);
        }
        else {
            getOut().println("Invalid choice, please try again");
            getTUI().setCurrentInput(new AskConfirm(getTUI(), getOut()));
        }
    }
}
