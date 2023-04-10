package it.polimi.ingsw.controller.listeners;

import it.polimi.ingsw.model.PlayerState;

public interface OnPlayerStateChangedListener {
    void onPlayerStateChanged(String nickname, PlayerState playerState);

}
