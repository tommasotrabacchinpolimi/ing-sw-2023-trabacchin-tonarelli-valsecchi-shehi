package it.polimi.ingsw.view.tui.input.chatinput;

import it.polimi.ingsw.view.tui.TUI;
import it.polimi.ingsw.view.tui.input.Input;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * The AskReceiver class represents an input prompt for asking the message receivers. It extends the {@link Input} class.
 *
 * @see Input
 * @see TUI
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public class AskReceiver extends Input {

    /**
     * Constructs a new AskReceiver instance with the specified {@link TUI} and {@link PrintStream}.
     * @param tui  The {@link TUI} object.
     * @param out  The {@link PrintStream} for output.
     *
     * @see TUI
     */
    public AskReceiver(TUI tui, PrintStream out) {
        super(tui, out);
        getTUI().setInputInProgress(true);
    }

    /**
     * Prints the prompt for entering the message receivers information.
     * @see Input#printPrompt() 
     */
    @Override
    public void printPrompt() {
        getOut().println("Please, enter the following information:");
        getOut().println("If you want the message to be private, enter the nickname of the player. Otherwise, type 'all' to send the message to all players.");
    }

    /**
     * Reads the user's input and processes it.
     * @param line The user's input.
     * @see Input#readLine(String) 
     */
    @Override
    public void readLine(String line) {
        if (!line.equals("all") && !getOtherPlayer().contains(line)) {
            getOut().println("Something went wrong, the word you have typed is not right. Please enter it again...");
            getTUI().setCurrentInput(new AskReceiver(getTUI(), getOut()));
        }
        else {
            getTUI().setCurrentInput(new AskText(getTUI(), getOut(), line));
        }
    }

    /**
     * Retrieves the list of other players' nicknames.
     * @return The list of other players' nicknames.
     */
    private List<String> getOtherPlayer(){
        List<String> nicknames = new ArrayList<>(getTUI().getModel().getPlayers().stream().filter(n -> !n.equals(getTUI().getModel().getThisPlayer())).toList());
        while(nicknames.size()-3 < 0){
            nicknames.add("");
        }
        return nicknames;
    }
}
