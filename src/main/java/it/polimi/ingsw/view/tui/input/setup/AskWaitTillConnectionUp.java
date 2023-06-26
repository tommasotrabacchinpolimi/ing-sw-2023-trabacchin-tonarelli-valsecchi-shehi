package it.polimi.ingsw.view.tui.input.setup;

import it.polimi.ingsw.view.tui.TUI;
import it.polimi.ingsw.view.tui.input.Input;

import java.io.PrintStream;

public class AskWaitTillConnectionUp extends Input {
    public AskWaitTillConnectionUp(TUI tui, PrintStream out) {
        super(tui, out);
    }

    @Override
    public void printPrompt() {
        getOut().println("It seems the connection is down. Please type 'yes' if you want to try to reconnect, or 'no' if you want to close the application");
    }

    @Override
    public void readLine(String line) {
        if(line.equals("yes")) {
            System.out.println("Reconnecting...");
            getTUI().getLogicController().reConnect();
            getTUI().setCurrentInput(new AskNickname(getTUI(), getOut()));
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
