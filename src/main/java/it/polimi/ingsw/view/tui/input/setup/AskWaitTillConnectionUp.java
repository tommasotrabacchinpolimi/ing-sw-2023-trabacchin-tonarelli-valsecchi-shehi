package it.polimi.ingsw.view.tui.input.setup;

import it.polimi.ingsw.view.tui.TUI;
import it.polimi.ingsw.view.tui.input.Input;

import java.io.PrintStream;

/**
 * The AskWaitTillConnectionUp class represents an input prompt for waiting until the connection is up after a player's disconnection.
 * It extends the {@link Input} class.
 * @see Input
 * @see TUI
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public class AskWaitTillConnectionUp extends Input {
    /**
     * Constructs an AskWaitTillConnectionUp object with the specified {@link TUI} and {@link PrintStream output stream}.
     * @param tui the {@link TUI} (Text User Interface) object
     * @param out the {@link PrintStream output stream} to print messages
     */
    public AskWaitTillConnectionUp(TUI tui, PrintStream out) {
        super(tui, out);
    }

    /**
     * Prints the prompt message asking the user if they want to try reconnecting or close the application.
     * @see Input#printPrompt() 
     */
    @Override
    public void printPrompt() {
        getOut().println("It seems the connection is down. Please type 'yes' if you want to try to reconnect, or 'no' if you want to close the application");
    }

    /**
     * Reads the user's input and takes appropriate action based on the input which specifies if the player wants to reconnect or close the application.
     * @param line the user's input
     * @see Input#readLine(String) 
     */
    @Override
    public void readLine(String line) {
        if(line.equals("yes")) {
            System.out.println("Reconnecting...");
            getTUI().getLogicController().reConnect();
            if(getTUI().getModel().getThisPlayer() != null) {
                getTUI().setCurrentInput(new AskWait(getTUI(), getOut()));
                getTUI().getLogicController().joinGame(getTUI().getModel().getThisPlayer());
            }
            else {
                getTUI().setCurrentInput(new AskNickname(getTUI(), getOut()));
            }

        }
        else if(line.equals("no")) {
            System.exit(0);
        }
        else {
            getOut().println("Your choice is invalid, please try again...");
            getTUI().setCurrentInput(new AskWaitTillConnectionUp(getTUI(),getOut()));
        }
    }
}
