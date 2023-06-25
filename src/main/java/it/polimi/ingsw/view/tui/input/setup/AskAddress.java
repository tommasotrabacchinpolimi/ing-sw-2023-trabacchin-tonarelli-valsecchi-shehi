package it.polimi.ingsw.view.tui.input.setup;

import it.polimi.ingsw.view.tui.TUI;
import it.polimi.ingsw.view.tui.input.Input;

import java.io.PrintStream;

public class AskAddress extends Input {

    private final String choice;
    public AskAddress(TUI tui, PrintStream out, String choice) {
        super(tui, out);
        this.choice = choice;
    }

    @Override
    public void printPrompt() {
        getOut().println("Now insert an address number");
    }

    @Override
    public void readLine(String line) {
        getTUI().setCurrentInput(new AskPort(getTUI(), getOut(), choice, line));
    }
}
