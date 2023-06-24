package it.polimi.ingsw.view.tui_alternative.input;

import it.polimi.ingsw.view.tui_alternative.TUI;
import it.polimi.ingsw.view.tui_alternative.input.chatinput.AskReceiver;
import it.polimi.ingsw.view.tui_alternative.input.draginput.AskCoordinates;
import it.polimi.ingsw.view.tui_alternative.page.*;

import java.io.PrintStream;
import java.util.ArrayList;

import static it.polimi.ingsw.utils.color.MyShelfieAnsi.colorize;

public class InputSelector extends Input{

    public InputSelector(TUI tui, PrintStream printStream) {
        super(tui, printStream);
        getTUI().setInputInProgress(false);
    }
    @Override
    public void printPrompt() {

    }

    @Override
    public void readLine(String line) {
        switch (line) {
            case "help":
                getTUI().setPage(new HelpPage(getTUI()));
                break;
            case "quit":
                getTUI().getLogicController().quitGame();
                getTUI().launchUI();
                return;
            case "message":
                getTUI().setCurrentInput(new AskReceiver(getTUI(), getOut()));
                break;
            case "chat":
                getTUI().setPage(new ChatPage(getTUI()));
                break;
            case "others":
                getTUI().setPage(new OthersPage(getTUI()));
                break;
            case "exit":
                getTUI().setPage(new HomePage(getTUI()));
                break;
            case "play":
                getTUI().setCurrentInput(new AskCoordinates(getTUI(), getOut(),true, new ArrayList<>()));
                break;
            default :
                getTUI().getPage().show();
        }
    }

    @Override
    public void onException() {
        getOut().println(getTUI().getModel().getException());
        getTUI().setCurrentInput(new InputSelector(getTUI(), getOut()));
    }


}
