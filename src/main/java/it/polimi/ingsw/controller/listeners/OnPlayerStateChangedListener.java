package it.polimi.ingsw.controller.listeners;

import it.polimi.ingsw.model.PlayerState;

/**
 * A listener interface for notifying when the state of a player has changed.
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public interface OnPlayerStateChangedListener {
    /**
     * Called when the state of a player has changed.
     * @param nickname The nickname of the player whose state has changed.
     * @param playerState The new state of the player.
     */
    void onPlayerStateChanged(String nickname, PlayerState playerState);
}
