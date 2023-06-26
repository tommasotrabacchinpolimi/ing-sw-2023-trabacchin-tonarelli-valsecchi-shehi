package it.polimi.ingsw.view.tui.input.setup;

import it.polimi.ingsw.view.tui.TUI;
import it.polimi.ingsw.view.tui.input.Input;

import java.io.PrintStream;


/**
 * The AskPlayersNumber class represents an input prompt for choosing the number of players
 * in the game when the player is creating a new game.
 * It extends the {@link Input} class.
 * @see Input
 * @see TUI
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public class AskPlayersNumber extends Input {
    /**
     * String that contains the player's nickname.
     */
    private final String nickname;

    /**
     * Constructs an AskPlayersNumber object with the specified {@link TUI}, {@link PrintStream}, and nickname.
     * @param tui the {@link TUI} (Text User Interface) object
     * @param out the {@link PrintStream} to print messages
     * @param nickname the nickname of the player
     *                 
     * @see TUI
     */
    public AskPlayersNumber(TUI tui, PrintStream out, String nickname) {
        super(tui, out);
        this.nickname = nickname;
    }

    /**
     * Prints the prompt message asking the user to choose the number of players in the game.
     * @see Input#printPrompt() 
     */
    @Override
    public void printPrompt() {
        getOut().println("So you need to choose how many people there will be in the game (it has to be a number between 2 and 4, including you) :");
    }

    /**
     * Reads the user's input and processes it accordingly by creating the game.
     * @param line the user's input
     * @see Input#readLine(String) 
     */
    @Override
    public void readLine(String line) {
        if(line.equals("2") || line.equals("3") || line.equals("4")) {
            getTUI().setCurrentInput(new AskWait(getTUI(), getOut()));
            getTUI().getLogicController().createGame(nickname, Integer.parseInt(line));
            getTUI().getModel().setThisPlayer(nickname);
        }
        else {
            getOut().println("Invalid choice, please try again...");
            getTUI().setCurrentInput(new AskPlayersNumber(getTUI(), getOut(), nickname));
        }
    }
}
