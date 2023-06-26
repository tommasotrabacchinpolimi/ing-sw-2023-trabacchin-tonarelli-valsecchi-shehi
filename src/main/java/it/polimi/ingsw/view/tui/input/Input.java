package it.polimi.ingsw.view.tui.input;

import it.polimi.ingsw.view.tui.TUI;

import java.io.PrintStream;

/**
 * The Input class represents an abstract input prompt in a text-based user interface.
 * It provides common functionality and structure for input prompts.
 * @see TUI
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public abstract class Input {
    /**
     * Attribute that represents the {@link TUI}.
     */
    private final TUI tui;
    /**
     * Attribute that represents the {@link PrintStream output stream}.
     */
    private final PrintStream out;
    /**
     * Boolean that is set to true if and only if the input prompt is locked.
     */
    private boolean locked;

    /**
     * Constructs an Input object with the specified {@link TUI} and {@link PrintStream output stream}.
     * @param tui the {@link TUI} (Text User Interface) object
     * @param out the {@link PrintStream output stream} to print messages
     */
    public Input(TUI tui, PrintStream out) {
        this.tui = tui;
        this.out = out;
        this.locked = false;
    }

    /**
     * Checks if the input prompt is {@link #locked}.
     * @return true if the input prompt is locked, false otherwise
     */
    public boolean isLocked() {
        return locked;
    }

    /**
     * Sets the {@link #locked} status of the input prompt.
     * @param locked the lock status to set
     */
    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    /**
     * Returns the {@link PrintStream output stream} associated with the input prompt.
     * @return the {@link PrintStream output stream}
     */
    public PrintStream getOut() {
        return out;
    }

    /**
     * Returns the {@link TUI} object associated with the input prompt.
     * @return the {@link TUI} object
     */
    public TUI getTUI() {
        return tui;
    }

    /**
     * Prints the prompt message for the input prompt. This method needs to be implemented by subclasses.
     */
    public abstract void printPrompt();

    /**
     * Reads the user's input and takes appropriate action based on the input.
     * This method needs to be implemented by subclasses.
     * @param line the user's input
     */
    public abstract void readLine(String line);

    /**
     * Callback method to handle exceptions that occurred during input processing.
     * This method can be overridden by subclasses to provide custom exception handling.
     */
    public void onException() {
    }
}
