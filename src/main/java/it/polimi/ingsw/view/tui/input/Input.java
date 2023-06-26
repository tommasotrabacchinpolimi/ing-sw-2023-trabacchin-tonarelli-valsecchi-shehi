package it.polimi.ingsw.view.tui.input;

import it.polimi.ingsw.view.tui.TUI;

import java.io.PrintStream;

public abstract class Input {

    private final TUI tui;

    private final PrintStream out;

    private boolean locked;

    public Input(TUI tui, PrintStream out) {
        this.tui = tui;
        this.out = out;
        this.locked = false;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
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
