package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.view.Client;
import it.polimi.ingsw.view.UI;
import it.polimi.ingsw.view.ViewData;
import javafx.application.Platform;

import java.io.IOException;

/**
 * This class is used to manage the interaction between:
 * <ul>
 *     <li>client</li>
 *     <li>user interface</li>
 *     <li>server</li>
 * </ul>
 *
 * @apiNote Please note that the real application launching the java-fx program is {@link GUILauncher}
 *
 * @see UI
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 * @version 2.0
 * @since 30/05/2023
 */
public class GUI extends UI {

    GUILauncher guiLauncher;

    public GUI(GUILauncher guiLauncher) {
        this.guiLauncher = guiLauncher;
        launchUI();
    }

    @Override
    public void launchUI() {
        setModel(new ViewData(9, 5, 6));
        getModel().setUserInterface(this);
        setLogicController(new Client(this, getModel()));
    }

    @Override
    public void onNewMessage(String sender) {
        Platform.runLater(() -> {
            guiLauncher.handleNewMessage(getModel().getLastMessage());
        });
    }

    @Override
    public void onCurrentPlayerChanged(String newCurrentPlayer) {

    }

    @Override
    public void showWinner() {

    }

    @Override
    public void onException() throws IOException {
        Platform.runLater(() -> {
            guiLauncher.handleRemoteException(getModel().getException());
        });
    }

    @Override
    public void onGameStateChanged() throws IOException {
        Platform.runLater(() -> {
            if (getModel().getGameState().equals("INIT")) {
                //If the player has to wait others

            } else if (getModel().getGameState().equals("MID")) {

                guiLauncher.manageMainInterface();
                //The game has started and the main interface needs to be displayed
                //Also called when the game evolves from SUSPENDED to MID

            } else if (getModel().getGameState().equals("SUSPENDED")) {
                //the game is blocked and needs to be wait to other players
                //check that the game was previously in MID and not INIT

            } else if (getModel().getGameState().equals("FINAL")) {
                //The last turn needs to be performed

            } else if (getModel().getGameState().equals("END")) {
                //Display if present the winner
            }
        });
    }
}
