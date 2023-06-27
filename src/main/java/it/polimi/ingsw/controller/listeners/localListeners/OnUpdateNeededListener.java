package it.polimi.ingsw.controller.listeners.localListeners;

import it.polimi.ingsw.model.Player;

/**
 * A listener interface for notifying when an update is needed for a player, for example after a disconnection.
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public interface OnUpdateNeededListener {
    /**
     * Called when an update is needed for the specified player.
     * @param player the player that needs an update.
     */
    void onUpdateNeededListener(Player player);
}
