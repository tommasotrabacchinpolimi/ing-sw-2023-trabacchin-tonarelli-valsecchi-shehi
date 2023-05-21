package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerState;

public class InitTimingState extends TimingState{

    public InitTimingState(TimingStateMachine timingStateMachine, Player previousPlayer) {
        super(timingStateMachine, previousPlayer);
    }
    @Override
    public synchronized void timerGoOff() {

    }
    @Override
    public synchronized void currentPlayerStateChanged(Player player, PlayerState playerState) {

    }

    @Override
    public boolean isDisconnectedTiming() {
        return false;
    }
}
