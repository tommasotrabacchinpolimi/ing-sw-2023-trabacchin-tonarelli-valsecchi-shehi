package it.polimi.ingsw.view.tui.input.chatinput;

import it.polimi.ingsw.view.tui.TUI;
import it.polimi.ingsw.view.tui.input.Input;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class AskReceiver extends Input {

    public AskReceiver(TUI tui, PrintStream out) {
        super(tui, out);
        getTUI().setInputInProgress(true);
    }

    @Override
    public void printPrompt() {
        getOut().println("Please, enter the following information:");
        getOut().println("If you want the message to be private, enter the nickname of the player. Otherwise, enter type 'all' to send the message to all players");
    }

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

    private List<String> getOtherPlayer(){
        List<String> nicknames = new ArrayList<>(getTUI().getModel().getPlayers().stream().filter(n -> !n.equals(getTUI().getModel().getThisPlayer())).toList());
        while(nicknames.size()-3 < 0){
            nicknames.add("");
        }
        return nicknames;
    }
}
