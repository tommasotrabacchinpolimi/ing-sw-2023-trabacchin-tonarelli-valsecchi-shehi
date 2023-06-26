package it.polimi.ingsw.view.tui.input;

import it.polimi.ingsw.view.tui.TUI;
import it.polimi.ingsw.view.tui.input.chatinput.AskReceiver;
import it.polimi.ingsw.view.tui.input.draginput.AskCoordinates;
import it.polimi.ingsw.view.tui.page.*;

import java.io.PrintStream;
import java.util.ArrayList;

import static it.polimi.ingsw.utils.color.MyShelfieAnsi.colorize;

/**
 * The InputSelector class represents an input prompt that selects the {@link Page} to show based on the user's input.
 * It extends the {@link Input} class and provides specific functionality for selecting actions in the {@link TUI text-based user interface}.
 * @see Input
 * @see Page
 * @see TUI
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public class InputSelector extends Input{
    /**
     * Constructs an InputSelector object with the specified {@link TUI} and {@link PrintStream output stream}.
     * @param tui the TUI (Text User Interface) object
     * @param printStream the print stream to {@link PrintStream output stream}
     */
    public InputSelector(TUI tui, PrintStream printStream) {
        super(tui, printStream);
        getTUI().setInputInProgress(false);
    }

    /**
     * Prints the prompt message for the input prompt.
     * This method is not used in the InputSelector class.
     * @see Input#printPrompt()
     */
    @Override
    public void printPrompt() {

    }

    /**
     * Reads the user's input and sets the right {@link Page} to show.
     * @param line the user's chosen {@link Page} to show.
     * @see Input#readLine(String)
     */
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

    /**
     * Callback method to handle exceptions that occurred during input processing.
     * This method overrides the default implementation in the Input class.
     * @see Input#onException()
     */
    @Override
    public void onException() {
        getOut().println(getTUI().getModel().getException());
        getTUI().setCurrentInput(new InputSelector(getTUI(), getOut()));
    }
}
