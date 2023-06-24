package it.polimi.ingsw.view.tui_alternative.input;

import it.polimi.ingsw.view.tui_alternative.TUI;

import java.io.PrintStream;

public abstract class Input {

    private final TUI tui;

    private final PrintStream out;

    public Input(TUI tui, PrintStream out) {
        this.tui = tui;
        this.out = out;
    }

    public PrintStream getOut() {
        return out;
    }
    public TUI getTUI() {
        return tui;
    }

    public abstract void printPrompt();

    public abstract void readLine(String line);

    public void onException() {
    }
}
