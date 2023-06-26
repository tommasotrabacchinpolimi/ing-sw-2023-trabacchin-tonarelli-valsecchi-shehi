package it.polimi.ingsw.view.tui.input.setup;

import it.polimi.ingsw.view.tui.TUI;
import it.polimi.ingsw.view.tui.input.Input;

import java.io.PrintStream;

/**
 * The AskProtocol class represents an input prompt for choosing a connection protocol between socket and RMI.
 * It extends the {@link Input} class.
 * @see Input
 * @see TUI
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public class AskProtocol extends Input {

    /**
     * Constructs an AskProtocol object with the specified {@link TUI} and {@link PrintStream}.
     * @param tui the {@link TUI} (Text User Interface) object
     * @param out the {@link PrintStream} to print messages
     */
    public AskProtocol(TUI tui, PrintStream out) {
        super(tui, out);
        getTUI().setInputInProgress(true);
    }

    /**
     * Prints the prompt message asking the user to choose a connection protocol.
     * @see Input#printPrompt()
     */
    @Override
    public void printPrompt() {
        getOut().println("Please, choose a connection method: 1) socket; 2) rmi.");
    }

    /**
     * Reads the user's input and processes it accordingly.
     * @param line the connection protocol chosen by the player
     * @see Input#readLine(String)
     */
    @Override
    public void readLine(String line) {
        if(line.equals("1") || line.equals("2")) {
            getTUI().setCurrentInput(new AskAddress(getTUI(), getOut(), line));
        }
        else {
            getOut().println("Invalid choice, please try again");
            getTUI().setCurrentInput(new AskProtocol(getTUI(), getOut()));
        }
    }
}
