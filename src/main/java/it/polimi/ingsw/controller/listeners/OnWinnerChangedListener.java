package it.polimi.ingsw.controller.listeners;

import it.polimi.ingsw.model.State;

/**
 * A listener interface for notifying when the {@linkplain State#getWinner()} winner of the game has changed.
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public interface OnWinnerChangedListener {
    /**
     * Called when the winner of the game has changed.
     * @param nickname The nickname of the new winner.
     */
    void onWinnerChanged(String nickname);
}

