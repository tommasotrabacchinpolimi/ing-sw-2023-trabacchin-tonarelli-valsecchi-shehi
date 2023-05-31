package it.polimi.ingsw.controller.listeners;

import it.polimi.ingsw.model.GameState;

import java.io.IOException;

public interface OnStateChangedListener {
    public void onStateChanged(GameState gameState);
}
