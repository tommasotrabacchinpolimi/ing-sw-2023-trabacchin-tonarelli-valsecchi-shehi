package it.polimi.ingsw.controller.listeners;

import java.util.List;

/**
 * A listener interface for notifying when the list of players has changed,
 * for example when a new player joins the game.
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public interface OnPlayersListChangedListener {

    /**
     * Called when the list of players has changed.
     * @param players The updated list of player names.
     */
    public void onPlayersListChanged(List<String> players);
}
