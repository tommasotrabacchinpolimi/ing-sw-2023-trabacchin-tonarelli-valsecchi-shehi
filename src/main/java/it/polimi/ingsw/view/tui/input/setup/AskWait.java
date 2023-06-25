package it.polimi.ingsw.view.tui.input.setup;

import it.polimi.ingsw.view.tui.TUI;
import it.polimi.ingsw.view.tui.input.AskConfirm;
import it.polimi.ingsw.view.tui.input.Input;

import java.io.PrintStream;

public class AskWait extends Input {
    public AskWait(TUI tui, PrintStream out) {
        super(tui, out);
        getTUI().setInputInProgress(false);
    }

    @Override
    public void printPrompt() {
        getOut().println("Please wait till the game begin");
    }

    @Override
    public void readLine(String line) {
        if(line.equals("quit")) {
            getTUI().getLogicController().quitGame();
            getTUI().setCurrentInput(new AskConfirm(getTUI(), getOut()));
        }
    }

    @Override
    public void onException() {
        getOut().println(getTUI().getModel().getException());
        getTUI().setCurrentInput(new AskNickname(getTUI(), getOut()));
    }
}
