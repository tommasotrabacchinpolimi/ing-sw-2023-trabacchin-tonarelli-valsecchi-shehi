package it.polimi.ingsw.view.tui.input;

import it.polimi.ingsw.view.tui.TUI;
import it.polimi.ingsw.view.tui.input.setup.AskNickname;

import java.io.PrintStream;


/**
 * The AskConfirm class represents an input prompt for confirming the start of a new game.
 * It extends the {@link Input} class.
 * @see Input
 * @see TUI
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public class AskConfirm extends Input{

    /**
     * Constructs an AskConfirm object with the specified {@link TUI} and {@link PrintStream output stream}.
     * @param tui the {@link TUI} (Text User Interface) object
     * @param out the {@link PrintStream output stream} to print messages
     */
    public AskConfirm(TUI tui, PrintStream out) {
        super(tui, out);
        getTUI().setInputInProgress(true);
    }

    /**
     * Prints the prompt message asking the user to press enter to start a new game or type {@code quit}
     * to exit the application.
     * @see Input#printPrompt() 
     */
    @Override
    public void printPrompt() {
        getOut().println("Press 'enter' to start a new game, 'quit' to exit the app");
    }

    /**
     * Reads the user's input and takes appropriate action based on the input.
     * @param line the user's input
     * @see Input#readLine(String) 
     */
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
