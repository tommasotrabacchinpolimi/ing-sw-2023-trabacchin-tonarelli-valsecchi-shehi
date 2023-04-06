package it.polimi.ingsw.controller.listeners;

import it.polimi.ingsw.model.GameState;

public interface OnStateChangedListener {
    public void onStateChanged(GameState gameState);
}
