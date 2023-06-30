package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.view.Client;
import it.polimi.ingsw.view.UI;
import it.polimi.ingsw.view.ViewData;
import javafx.application.Platform;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The `GUI` class is responsible for managing the interaction between the client, user interface
 * and server in the graphical user interface (GUI) version of the game.
 * It extends the `UI` class and overrides its methods to provide GUI-specific functionality.
 *
 * <p>
 * The class utilizes the JavaFX framework for GUI rendering and event handling.
 * </p>
 *
 * <p>
 * The `GUI` class initializes the GUI launcher and launches the user interface upon instantiation.
 * It sets up the game model and the logic controller, which handles the communication with the server.
 * </p>
 *
 * <p>
 * The class provides implementations for various UI events, such as handling new messages, connection loss
 * current player changes, exceptions, and game state changes.
 * These implementations use the JavaFX `Platform.runLater()` method to ensure that UI updates are performed
 * on the JavaFX application thread.</p>
 *
 * <p>Please note that the main application launching the JavaFX program is the {@link GUILauncher} class.
 * Refer to the `GUILauncher` class for starting the GUI application.</p>
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 * @version 2.0
 * @apiNote The real application launching the JavaFX program is {@link GUILauncher}.
 * @see UI
 * @since 30/05/2023
 */
public class GUI extends UI {

    /**
     * Represents GuiLauncher object.
     */
    GUILauncher guiLauncher;

    /**
     * If it is {@code false} is not executed {@code true} otherwise
     */
    private boolean isGUILauncherSet;

    /**
     * Constructs a new `GUI` instance with the specified `GUILauncher` object.
     * The `GUILauncher` is responsible for launching the GUI application.
     *
     * @param guiLauncher The `GUILauncher` object.
     */
    public GUI(GUILauncher guiLauncher) {
        this.guiLauncher = guiLauncher;
        this.isGUILauncherSet = false;
        launchUI();
    }

    /**
     * Launches the GUI by setting up the game model and logic controller.
     * This method is called internally upon instantiation of the `GUI` class.
     */
    @Override
    public void launchUI() {
        setModel(new ViewData(9, 5, 6));
        getModel().setUserInterface(this);
        setLogicController(new Client(this, getModel()));
    }

    /**
     * Handles the event of receiving a new message from a sender.
     * This method is called when a new message is received and updates the GUI accordingly.
     *
     * @param sender The sender of the message.
     */
    @Override
    public synchronized void onNewMessage(String sender) {
        Platform.runLater(() -> {
            guiLauncher.handleNewMessage(getModel().getLastMessage());
        });
    }

    /**
     * Handles the event of receiving multiple new messages.
     * This method is called when multiple new messages are received and updates the GUI accordingly.
     */
    @Override
    public synchronized void onNewMessages() {
        /*Platform.runLater(() -> {
            getModel().getMessages()
                    .forEach((message) -> guiLauncher.handleNewMessage(message));
        });*/
    }

    /**
     * Handles the event of connection loss.
     * This method is called when the connection with the server is lost.
     * It allows for handling the UI behavior when the connection is no longer available.
     */
    @Override
    public synchronized void onConnectionLost() {
        // TODO: Implement connection lost handling in GUI
        Platform.runLater(() -> {
            guiLauncher.showWaitingToReconnect();
        });
        getLogicController().reConnect();
        getLogicController().joinGame(getModel().getThisPlayer());
    }

    /**
     * Handles the event of the current player being changed.
     * This method is called when the current player in the game is changed.
     * It allows for updating the UI to reflect the change in the current player.
     *
     * @param newCurrentPlayer The new current player.
     */
    @Override
    public synchronized void onCurrentPlayerChanged(String newCurrentPlayer) {
        while (!isGUILauncherSet()) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        Platform.runLater(() -> {
            guiLauncher.handleInfoTextDisplay();
            guiLauncher.handleLivingRoomUpdate();
            guiLauncher.handleBlockGameControls();
            guiLauncher.handleFirstPlayerSeatAssignment();
            guiLauncher.handleAssignedCommonGoals();
            guiLauncher.handleAssignedEndToken();
        });
    }

    /**
     * Handles the event of displaying the winner.
     * This method is called when the game ends and the winner needs to be displayed.
     */
    @Override
    public synchronized void showWinner() {
        if (getModel().getGameState().equals("END"))
            guiLauncher.showWinningPageOperation();
        // TODO: Implement displaying the winner in GUI
    }

    /**
     * Handles the event of an exception occurring.
     * This method is called when an exception is encountered during the game.
     * It allows for handling and displaying the exception in the GUI.
     */
    @Override
    public synchronized void onException() {
        Platform.runLater(() -> {
            guiLauncher.handleRemoteException(getModel().getException());
        });
    }

    /**
     * Handles the event of the game state being changed.
     * This method is called when the game state transitions to a new state.
     * It allows for updating the UI to reflect the new game state.
     */
    @Override
    public void onGameStateChanged() {
        System.out.println("in on game state changed");
        Platform.runLater(() -> {
            synchronized (this) {
                System.out.println("in run later " + getModel().getGameState());
                if (getModel().getGameState().equals("INIT")) {
                    // If the player has to wait for others
                    // TODO: Handle game state INIT in GUI
                    guiLauncher.showWaitForPlayers();

                } else if (getModel().getGameState().equals("MID")) {
                    System.out.println("in MID branch");
                    // The game has started and the main interface needs to be displayed
                    // Also called when the game evolves from SUSPENDED to MID

                    guiLauncher.hideWaitingView();

                    if (!isGUILauncherSet()) {
                        System.out.println("in if");
                        guiLauncher.manageMainInterface();
                        System.out.println("out of manager main interface");
                        setGUILauncherSet(true);
                        System.out.println("gui launcher set");
                        System.out.println("notified");
                    }
                    this.notifyAll();

                } else if (getModel().getGameState().equals("SUSPENDED")) {
                    // The game is blocked and needs to wait for other players
                    // Check that the game was previously in MID and not INIT
                    // TODO: Handle game state SUSPENDED in GUI
                    guiLauncher.showWaitingToReconnect();

                } else if (getModel().getGameState().equals("FINAL")) {
                    // The last turn needs to be performed
                    // TODO: Handle game state FINAL in GUI
                    guiLauncher.showWinningPageOperation();

                } else if (getModel().getGameState().equals("END")) {
                    // Display the winner, if present
                    // TODO: Handle game state END in GUI
                }
            }
            System.out.println("end of run later");
        });
    }

    /**
     * Returns the current value of the flag indicating if the GUI launcher is set.
     *
     * @return {@code true} if the GUI launcher is set, {@code false} otherwise.
     */
    public synchronized boolean isGUILauncherSet() {
        return isGUILauncherSet;
    }

    /**
     * Sets the flag indicating if the GUI launcher is set.
     *
     * @param GUILauncherSet The value to set for the GUI launcher flag.
     */
    public synchronized void setGUILauncherSet(boolean GUILauncherSet) {
        isGUILauncherSet = GUILauncherSet;
    }
}