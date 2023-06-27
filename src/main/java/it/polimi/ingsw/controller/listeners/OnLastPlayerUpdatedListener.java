package it.polimi.ingsw.controller.listeners;

import it.polimi.ingsw.model.State;

import java.util.List;

/**
 * A listener interface for notifying when the {@linkplain State#getCurrentPlayer() last player} is updated.
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public interface OnLastPlayerUpdatedListener {
    /**
     * Called when the last player is updated.
     * @param nicknameLastPlayer The nickname of the last player.
     */
    public void onLastPlayerUpdated(String nicknameLastPlayer);
}
