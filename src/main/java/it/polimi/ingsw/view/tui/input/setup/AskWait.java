package it.polimi.ingsw.view.tui.input.setup;

import it.polimi.ingsw.view.tui.TUI;
import it.polimi.ingsw.view.tui.input.AskConfirm;
import it.polimi.ingsw.view.tui.input.Input;

import java.io.PrintStream;

/**
 * The AskWait class represents an input prompt for waiting until the game begins (when there aren't enough players).
 * It extends the {@link Input} class.
 * @see Input
 * @see TUI
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public class AskWait extends Input {

    /**
     * Constructs an AskWait object with the specified {@link TUI} and {@link PrintStream output stream}.
     * @param tui the {@link TUI} (Text User Interface) object
     * @param out the {@link PrintStream output stream} to print messages
     */
    public AskWait(TUI tui, PrintStream out) {
        super(tui, out);
        getTUI().setInputInProgress(false);
    }

    /**
     * Prints the prompt message instructing the user to wait until the game begins.
     * @see Input#printPrompt()
     */
    @Override
    public void printPrompt() {
        getOut().println("Please wait till the game begin");
    }

    /**
     * Reads the user's input and processes it accordingly when the player type {@code quit} in order to quit the game.
     * @param line the user's input
     * @see Input#readLine(String)
     */
    @Override
    public void readLine(String line) {
        if(line.equals("quit")) {
            getTUI().getLogicController().quitGame();
            getTUI().setCurrentInput(new AskConfirm(getTUI(), getOut()));
        }
    }

    /**
     * Handles an exception that occurred during the input process.
     * Prints the exception message and sets the next input prompt accordingly.
     * @see Input#onException()
     */
    @Override
    public void onException() {
        getOut().println(getTUI().getModel().getException());
        getTUI().setCurrentInput(new AskNickname(getTUI(), getOut()));
    }
}
