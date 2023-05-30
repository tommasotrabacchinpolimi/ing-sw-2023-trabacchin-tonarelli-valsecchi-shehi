package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.view.Client;
import it.polimi.ingsw.view.UI;
import it.polimi.ingsw.view.ViewData;

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

    public GUI() {
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

    }

    @Override
    public void onCurrentPlayerChanged(String newCurrentPlayer) {

    }

    @Override
    public void showWinner() {

    }

    @Override
    public void onException() throws IOException {

    }

    @Override
    public void onGameStateChanged() throws IOException {

    }
}
