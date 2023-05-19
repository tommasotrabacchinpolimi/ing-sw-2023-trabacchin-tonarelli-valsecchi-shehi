package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerState;

import java.util.Timer;
import java.util.TimerTask;

public abstract class TimingState {
    private TimingStateMachine timingStateMachine;

    private TimerTask timerTask;

    private boolean ALREADY_TRIGGERED;

    public TimingState(TimingStateMachine timingStateMachine) {
        ALREADY_TRIGGERED = false;
        this.timingStateMachine = timingStateMachine;
    }
    public TimingStateMachine getTimingStateMachine() {
        return timingStateMachine;
    }

    public void setTimingStateMachine(TimingStateMachine timingStateMachine) {
        this.timingStateMachine = timingStateMachine;
    }

    public abstract void timerGoOff() ;

    public void currentPlayerChanged(Player player) {

        if(player.getPlayerState().equals(PlayerState.CONNECTED)) {
            getTimingStateMachine().setTimingState(new ConnectedTimingState(getTimingStateMachine()));
        }
        else if(player.getPlayerState().equals(PlayerState.DISCONNECTED)) {
            getTimingStateMachine().setTimingState(new DisconnectedTimingState(getTimingStateMachine()));
        }
    }

    public abstract void currentPlayerStateChanged(Player player, PlayerState playerState);


    protected TimerTask getTimerTask() {
        return timerTask;
    }

    protected void setTimerTask(TimerTask timerTask) {
        this.timerTask = timerTask;
    }

    protected void setTriggered() {
        ALREADY_TRIGGERED = true;
    }

    protected boolean isTriggered() {
        return ALREADY_TRIGGERED;
    }
}
