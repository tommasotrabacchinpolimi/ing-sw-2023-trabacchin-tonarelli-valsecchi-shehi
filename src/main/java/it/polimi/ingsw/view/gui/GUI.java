package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.view.Client;
import it.polimi.ingsw.view.UI;
import it.polimi.ingsw.view.ViewData;
import javafx.application.Platform;

import java.io.IOException;

/**
 * The `GUI` class is responsible for managing the interaction between the client, user interface
 * and server in the graphical user interface (GUI) version of the game.
 * It extends the `UI` class and overrides its methods to provide GUI-specific functionality.
 *
 * <p>
 *     The class utilizes the JavaFX framework for GUI rendering and event handling.
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

    GUILauncher guiLauncher;

    /**
     * Constructs a new `GUI` instance with the specified `GUILauncher` object.
     * The `GUILauncher` is responsible for launching the GUI application.
     *
     * @param guiLauncher The `GUILauncher` object.
     */
    public GUI(GUILauncher guiLauncher) {
        this.guiLauncher = guiLauncher;
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
    public void onNewMessage(String sender) {
        Platform.runLater(() -> {
            guiLauncher.handleNewMessage(getModel().getLastMessage());
        });
    }

    /**
     * Handles the event of receiving multiple new messages.
     * This method is called when multiple new messages are received and updates the GUI accordingly.
     */
    @Override
    public void onNewMessages() {
        Platform.runLater(() -> {
            getModel().getMessages().forEach((message) -> guiLauncher.handleNewMessage(message));
        });
    }

    /**
     * Handles the event of connection loss.
     * This method is called when the connection with the server is lost.
     * It allows for handling the UI behavior when the connection is no longer available.
     */
    @Override
    public void onConnectionLost() {
        // TODO: Implement connection lost handling in GUI
    }

    /**
     * Handles the event of the current player being changed.
     * This method is called when the current player in the game is changed.
     * It allows for updating the UI to reflect the change in the current player.
     *
     * @param newCurrentPlayer The new current player.
     */
    @Override
    public void onCurrentPlayerChanged(String newCurrentPlayer) {
        Platform.runLater(() -> {
            guiLauncher.handleLivingRoomUpdate();
            guiLauncher.handleBlockGameControls();
        });
    }

    /**
     * Handles the event of displaying the winner.
     * This method is called when the game ends and the winner needs to be displayed.
     */
    @Override
    public void showWinner() {
        // TODO: Implement displaying the winner in GUI
    }

    /**
     * Handles the event of an exception occurring.
     * This method is called when an exception is encountered during the game.
     * It allows for handling and displaying the exception in the GUI.
     */
    @Override
    public void onException() {
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
        Platform.runLater(() -> {
            if (getModel().getGameState().equals("INIT")) {
                // If the player has to wait for others
                // TODO: Handle game state INIT in GUI

            } else if (getModel().getGameState().equals("MID")) {
                // The game has started and the main interface needs to be displayed
                // Also called when the game evolves from SUSPENDED to MID
                guiLauncher.manageMainInterface();

            } else if (getModel().getGameState().equals("SUSPENDED")) {
                // The game is blocked and needs to wait for other players
                // Check that the game was previously in MID and not INIT
                // TODO: Handle game state SUSPENDED in GUI

            } else if (getModel().getGameState().equals("FINAL")) {
                // The last turn needs to be performed
                // TODO: Handle game state FINAL in GUI

            } else if (getModel().getGameState().equals("END")) {
                // Display the winner, if present
                // TODO: Handle game state END in GUI
            }
        });
    }
}
