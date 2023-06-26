package it.polimi.ingsw.view.tui.input.setup;

import it.polimi.ingsw.view.tui.TUI;
import it.polimi.ingsw.view.tui.input.Input;

import java.io.PrintStream;

/**
 * The AskCreateOrJoin class represents an input prompt for asking the user to choose between creating a new game,
 * joining an existing game, or rejoining a game after a disconnection. It extends the {@link Input} class.
 * 
 * @see Input
 * @see TUI
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public class AskCreateOrJoin extends Input {
    /**
     * String that contains the nickname of the player.
     */
    private final String nickname;

    /**
     * Constructs a new AskCreateOrJoin instance with the specified {@link TUI}, {@link PrintStream}, and nickname.
     *
     * @param tui      The {@link TUI} object.
     * @param out      The {@link PrintStream} for output.
     * @param nickname The nickname of the player.
     *                 
     * @see TUI
     */
    public AskCreateOrJoin(TUI tui, PrintStream out, String nickname) {
        super(tui, out);
        this.nickname = nickname;
    }

    /**
     * Prints the prompt for choosing an option: create a new game, join an existing game, or rejoin after a disconnection.
     * @see Input#printPrompt() 
     */
    @Override
    public void printPrompt() {
        getOut().println("Ok, now chose one of the following options: ");
        getOut().println("1) create a new game");
        getOut().println("2) join an existing game");
        getOut().println("3) rejoin an existing game after a disconnection");
    }

    /**
     * Reads the user's input and takes appropriate action based on the chosen option.
     * @param line The user's input.
     * @see Input#readLine(String) 
     */
    @Override
    public void readLine(String line) {
        if(line.equals("1") || line.equals("2") || line.equals("3")) {
            if(line.equals("1")) {
                getTUI().setCurrentInput(new AskPlayersNumber(getTUI(), getOut(), nickname));
            }
            else {
                getTUI().setCurrentInput(new AskWait(getTUI(), getOut()));
                getTUI().getLogicController().joinGame(nickname);
                getTUI().getModel().setThisPlayer(nickname);
            }
        }
        else {
            getOut().println("Invalid choice, please try again...");
            getTUI().setCurrentInput(new AskCreateOrJoin(getTUI(), getOut(), nickname));
        }
    }
}
