package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerState;

import java.util.TimerTask;

public class DisconnectedTimingState extends TimingState{

    public DisconnectedTimingState(TimingStateMachine timingStateMachine) {

        super(timingStateMachine);
        setTimerTask(new TimerTask() {
            @Override
            public void run() {
                getTimingStateMachine().timerGoOff();
            }
        });
        getTimingStateMachine().registerTimerTask(getTimerTask(), 60 * 1000);
    }
    @Override
    public synchronized void timerGoOff() {
        if(isTriggered() || getTimingStateMachine().getController().getState().getGameState() == GameState.END){
            return;
        }
        getTimingStateMachine().setTimingState(new InitTimingState(getTimingStateMachine()));
        getTimingStateMachine().getController().getGameManager().setNextCurrentPlayer();
    }

    @Override
    public synchronized void currentPlayerStateChanged(Player player, PlayerState playerState) {
        if(isTriggered() || getTimingStateMachine().getController().getState().getGameState() == GameState.END){
            return;
        }
        if(playerState.equals(PlayerState.CONNECTED)) {
            getTimerTask().cancel();
            getTimingStateMachine().setTimingState(new ConnectedTimingState(getTimingStateMachine()));
        }
    }

    @Override
    public synchronized void currentPlayerChanged(Player player) {
        if(isTriggered() || getTimingStateMachine().getController().getState().getGameState() == GameState.END){
            return;
        }
        getTimerTask().cancel();
        super.currentPlayerChanged(player);
    }
}
