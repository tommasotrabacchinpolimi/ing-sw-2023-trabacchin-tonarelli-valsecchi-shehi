package it.polimi.ingsw.view.tui.input.chatinput;

import it.polimi.ingsw.view.tui.TUI;
import it.polimi.ingsw.view.tui.input.Input;
import it.polimi.ingsw.view.tui.input.InputSelector;

import java.io.PrintStream;

/**
 * The AskText class represents an input prompt for asking the text of a message. It extends the {@link Input} class.
 *
 * @see Input
 * @see TUI
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public class AskText extends Input {
    /**
     * String that represents the receivers of the message.
     */
    private final String receivers;

    /**
     * Constructs a new AskText instance with the specified {@link TUI}, {@link PrintStream}, and {@link #receivers}.
     * @param tui       The TUI object.
     * @param out       The PrintStream for output.
     * @param receivers The receivers of the message.
     */
    public AskText(TUI tui, PrintStream out, String receivers) {
        super(tui, out);
        this.receivers = receivers;
    }

    /**
     * Prints the prompt for entering the text of the message.
     * @see Input#printPrompt() 
     */
    @Override
    public void printPrompt() {
        getOut().println("Please, enter the text of the message:");
    }

    /**
     * Reads the user's input and sends the message to the specified receivers.
     *
     * @param line The user's input.
     * @see Input#readLine(String) 
     */
    @Override
    public void readLine(String line) {
        String[] rec = new String[4];
        if (!receivers.equals("all")) {
            rec[0] = receivers;
        } else {
            int i = 0;
            for (String player : getTUI().getModel().getPlayers()) {
                rec[i] = player;
                i++;
            }
        }
        getOut().println("Sending the message...");
        getTUI().setCurrentInput(new InputSelector(getTUI(), getOut()));
        getTUI().getLogicController().sentMessage(line, rec);
        getTUI().refresh();
    }
}
