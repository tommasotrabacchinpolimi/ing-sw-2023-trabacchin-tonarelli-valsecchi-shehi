package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerState;

public class InitTimingState extends TimingState{

    public InitTimingState(TimingStateMachine timingStateMachine) {
        super(timingStateMachine);
    }
    @Override
    public synchronized void timerGoOff() {

    }
    @Override
    public synchronized void currentPlayerStateChanged(Player player, PlayerState playerState) {

    }
}
