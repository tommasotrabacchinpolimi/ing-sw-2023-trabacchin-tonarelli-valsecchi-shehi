package it.polimi.ingsw.controller.listeners;

import it.polimi.ingsw.model.GameState;

import java.io.IOException;

/**
 * A listener interface for notifying when the game state has changed.
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 * @see GameState
 */
public interface OnStateChangedListener {

    /**
     * Called when the game state has changed.
     * @param gameState The new game state.
     * @see GameState
     */
    public void onStateChanged(GameState gameState);
}
