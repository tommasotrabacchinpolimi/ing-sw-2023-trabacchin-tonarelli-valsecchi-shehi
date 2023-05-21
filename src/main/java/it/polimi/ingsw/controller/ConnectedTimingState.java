package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerState;

import java.util.TimerTask;

public class ConnectedTimingState extends TimingState{

    public ConnectedTimingState(TimingStateMachine timingStateMachine, Player previousPlayer) {

        super(timingStateMachine, previousPlayer);
        setTimerTask(new TimerTask() {
            @Override
            public void run() {
                timerGoOff();
            }
        });
        getTimingStateMachine().registerTimerTask(getTimerTask(), 60 * 1000);
    }
    @Override
    public synchronized void timerGoOff() {
        if(isTriggered() || getTimingStateMachine().getController().getState().getGameState() == GameState.END){
            return;
        }
        setTriggered();
        getTimingStateMachine().setTimingState(new InitTimingState(getTimingStateMachine(), getTimingStateMachine().getController().getState().getCurrentPlayer()));
        getTimingStateMachine().getController().getGameManager().setNextCurrentPlayer();
    }

    @Override
    public synchronized void currentPlayerChanged(Player player) {
        getTimerTask().cancel();
        super.currentPlayerChanged(player);
    }

    @Override
    public synchronized void currentPlayerStateChanged(Player player, PlayerState playerState) {

    }

    @Override
    public boolean isDisconnectedTiming() {
        return false;
    }
}
