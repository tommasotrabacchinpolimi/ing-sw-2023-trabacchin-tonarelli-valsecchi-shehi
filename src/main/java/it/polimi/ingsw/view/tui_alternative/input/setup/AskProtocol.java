package it.polimi.ingsw.view.tui_alternative.input.setup;

import it.polimi.ingsw.view.tui_alternative.TUI;
import it.polimi.ingsw.view.tui_alternative.input.Input;

import java.io.PrintStream;

public class AskProtocol extends Input {

    public AskProtocol(TUI tui, PrintStream out) {
        super(tui, out);
        getTUI().setInputInProgress(true);
    }

    @Override
    public void printPrompt() {
        getOut().println("Please, choose a connection method: 1) socket; 2) rmi.");
    }

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
