package it.polimi.ingsw.view.tui;

import it.polimi.ingsw.view.UI;
import it.polimi.ingsw.view.ViewData;
import it.polimi.ingsw.view.tui.input.Input;
import it.polimi.ingsw.view.tui.input.InputSelector;
import it.polimi.ingsw.view.tui.input.setup.AskProtocol;
import it.polimi.ingsw.view.tui.input.setup.AskWaitTillConnectionUp;
import it.polimi.ingsw.view.tui.page.HomePage;
import it.polimi.ingsw.view.tui.page.Page;
import it.polimi.ingsw.view.tui.page.WinnerPage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

/**
 * The `TUI` (Text User Interface) class represents the command-line interface (CLI) of the game.
 * It provides methods to handle user input, display pages, and communicate with the game model.
 * This class extends the abstract `UI` class and implements its specific operations for the CLI.
 *
 * <p>
 * The `TUI` class manages the interaction between the game and the user through a text-based interface.
 * It allows users to navigate through different pages, input commands, and receive updates and notifications from the game.
 * The class utilizes a page-based approach, where each page represents a specific view or interaction within the game.
 * The `TUI` class handles the rendering and user input for these pages, as well as the communication with the game model.
 * </p>
 *
 * <p>
 * By extending the `UI` class, the `TUI` class inherits the core functionality for managing the game's user interface.
 * It overrides specific methods to tailor them to the requirements of the command-line interface.
 * These methods include handling new messages, current player changes, game state changes, exceptions, and connection status.
 * </p>
 *
 * <p>
 * The `TUI` class also maintains the current page and input in progress, allowing for proper management of user interactions.
 * It provides methods to set and retrieve the current page, as well as the current input being processed.
 * Additionally, it offers utility methods to read user input, refresh the current page, and manage the input in progress state.
 * </p>
 *
 * <p>
 * Overall, the `TUI` class serves as the textual interface for the game, allowing users to interact with the game
 * through a command-line interface and providing a seamless experience for playing the game.
 * </p>
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public class TUI extends UI {
    /**
     * The current page displayed in the TUI.
     */
    private Page page;

    /**
     * The current input being processed in the TUI.
     */
    private Input currentInput;

    /**
     * The PrintStream used for output in the TUI.
     */
    private final PrintStream printStream;

    /**
     * Indicates whether an input operation is in progress.
     */
    private boolean inputInProgress;

    /**
     * Initializes a new instance of the TUI class.
     */
    public TUI() {
        this.page = null;
        printStream = System.out;
        setModel(new ViewData(9, 5, 6));
        getModel().setUserInterface(this);
        inputInProgress = false;
    }

    /**
     * Returns the PrintStream used for output in the TUI.
     *
     * @return The PrintStream used for output.
     */
    public PrintStream getPrintStream() {
        return printStream;
    }

    /**
     * Sets the current page displayed in the TUI.
     *
     * @param page The page to set as the current page.
     */
    public void setPage(Page page) {
        this.currentInput = new InputSelector(this, printStream);
        this.page = page;
        page.show();
    }

    /**
     * Returns the current page displayed in the TUI.
     *
     * @return The current page.
     */
    public Page getPage() {
        return page;
    }

    /**
     * Returns the current input being processed in the TUI.
     *
     * @return The current input.
     */
    public Input getCurrentInput() {
        return currentInput;
    }

    /**
     * Sets the current input being processed in the TUI.
     *
     * @param currentInput The input to set as the current input.
     */
    public void setCurrentInput(Input currentInput) {
        this.currentInput = currentInput;
        currentInput.printPrompt();
    }

    /**
     * Launches the TUI and starts reading input from the user.
     */
    @Override
    public void launchUI() {
        AsyncReader asyncReader = new AsyncReader(this, new BufferedReader(new InputStreamReader(System.in)));
        new Thread(asyncReader).start();
        this.setCurrentInput(new AskProtocol(this, getPrintStream()));
    }

    /**
     * Handles the event when a new message is received.
     *
     * @param sender The sender of the message (his nickname).
     */
    @Override
    public synchronized void onNewMessage(String sender) {
        try {
            while (inputInProgress) {
                this.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.currentInput = new InputSelector(this, printStream);
        if (!sender.equals(getModel().getThisPlayer())) {
            if (page != null) {
                page.onNewMessage();
            } else {
                getPrintStream().println("There is a new message from " + sender);
            }
        }


    }

    /**
     * Handles the event when the current player is changed.
     *
     * @param newCurrentPlayer The new current player.
     */

    @Override
    public synchronized void onCurrentPlayerChanged(String newCurrentPlayer) {
        try {
            System.out.println("new current player");
            while (inputInProgress) {
                System.out.println("waiting");

                this.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.currentInput = new InputSelector(this, printStream);
        if (page == null) {
            page = new HomePage(this);
        }
        page.onCurrentPlayerChanged();
    }

    /**
     * Displays the winner page.
     */
    @Override
    public synchronized void showWinner() {

        //this.page = new WinnerPage(getModel(), this);
    }

    /**
     * Handles the event when an exception occurs.
     */
    @Override
    public synchronized void onException() {
        currentInput.onException();
    }

    /**
     * handles the event of a changed state
     */
    @Override
    public synchronized void onGameStateChanged() {
        try {
            while (inputInProgress) {
                this.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (getModel().getGameState().equals("END")) {
            this.page = new WinnerPage(this);
        }
        else {
            this.page.show();
        }
    }

    /**
     * Handles the event when new messages are available.
     */
    @Override
    public synchronized void onNewMessages() {

    }

    /**
     * Handles the event when the connection is lost.
     */
    @Override
    public synchronized void onConnectionLost() {
        try {
            while (inputInProgress) {
                this.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.setCurrentInput(new AskWaitTillConnectionUp(this, getPrintStream()));


    }


    /**
     * Reads a line of input from the user.
     *
     * @param line The input line.
     */
    public synchronized void readLine(String line) {
        getCurrentInput().readLine(line);
    }

    /**
     * Refreshes the current page to reflect any changes.
     */
    public synchronized void refresh() {
        if (page != null) {
            page.show();
        }
    }

    /**
     * Sets the input progress status.
     *
     * @param progress Indicates whether an input operation is in progress.
     */
    public synchronized void setInputInProgress(boolean progress) {
        this.inputInProgress = progress;
        if (!progress) {
            this.notifyAll();
        }
    }
}
