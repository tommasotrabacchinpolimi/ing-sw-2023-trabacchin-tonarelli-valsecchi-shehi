package it.polimi.ingsw.view.tui.input.setup;

import it.polimi.ingsw.view.tui.TUI;
import it.polimi.ingsw.view.tui.input.Input;

import java.io.PrintStream;

/**
 * The AskAddress class represents an input prompt for asking the user to insert a server address
 * in order to create a connection to the server.
 * It extends the {@link Input} class.
 *
 * @see it.polimi.ingsw.view.tui.input.Input
 * @see TUI
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public class AskAddress extends Input {
    /**
     * String that indicates the protocol chosen by the player.
     */
    private final String choice;

    /**
     * Constructs a new AskAddress instance with the specified {@link TUI}, {@link PrintStream}, and {@link #choice}.
     *
     * @param tui    The {@link TUI} object.
     * @param out    The {@link PrintStream} for output.
     * @param choice The type of connection chosen by the player.
     */
    public AskAddress(TUI tui, PrintStream out, String choice) {
        super(tui, out);
        this.choice = choice;
    }

    /**
     * Prints the prompt for inserting the server address.
     * @see Input#printPrompt()  
     */
    @Override
    public void printPrompt() {
        getOut().println("Now insert an address number");
    }

    /**
     * Reads the user's input and sets the next input as an AskPort instance with the specified parameters.
     *
     * @param line The user's input which container the server address.
     * @see Input#readLine(String) 
     */
    @Override
    public void readLine(String line) {
        getTUI().setCurrentInput(new AskPort(getTUI(), getOut(), choice, line));
    }
}
