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

    private final Player previousPlayer;

    public TimingState(TimingStateMachine timingStateMachine, Player previousPlayer) {
        ALREADY_TRIGGERED = false;
        this.timingStateMachine = timingStateMachine;
        this.previousPlayer = previousPlayer;
    }
    public TimingStateMachine getTimingStateMachine() {
        return timingStateMachine;
    }

    public void setTimingStateMachine(TimingStateMachine timingStateMachine) {
        this.timingStateMachine = timingStateMachine;
    }

    public abstract void timerGoOff() ;

    public void currentPlayerChanged(Player player) {
        if(isTriggered() || getTimingStateMachine().getController().getState().getGameState() == GameState.END){
            return;
        }
        setTriggered();
        if(player.getPlayerState().equals(PlayerState.CONNECTED)) {
            getTimingStateMachine().setTimingState(new ConnectedTimingState(getTimingStateMachine(), getTimingStateMachine().getController().getState().getCurrentPlayer()));
        }
        else if(player.getPlayerState().equals(PlayerState.DISCONNECTED)) {
            if(!getTimingStateMachine().getController().getGameManager().verifyAllDisconnectedPlayer(getPreviousPlayer())) {
                getTimingStateMachine().setTimingState(new DisconnectedTimingState(getTimingStateMachine(), getTimingStateMachine().getController().getState().getCurrentPlayer()));
            }
            else {
                getTimingStateMachine().setTimingState(new PendingSuspensionDisconnectedTimingState(getTimingStateMachine(), getTimingStateMachine().getController().getState().getCurrentPlayer()));
            }
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

    public abstract boolean isDisconnectedTiming();

    public Player getPreviousPlayer() {
        return previousPlayer;
    }
}
