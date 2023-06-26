package it.polimi.ingsw.view.tui.input.chatinput;

import it.polimi.ingsw.view.tui.TUI;
import it.polimi.ingsw.view.tui.input.Input;
import it.polimi.ingsw.view.tui.input.InputSelector;

import java.io.PrintStream;

public class AskText extends Input {

    private final String receivers;
    public AskText(TUI tui, PrintStream out, String receivers) {
        super(tui, out);
        this.receivers = receivers;
    }

    @Override
    public void printPrompt() {
        getOut().println("Please, enter the text of the message:");
    }

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
