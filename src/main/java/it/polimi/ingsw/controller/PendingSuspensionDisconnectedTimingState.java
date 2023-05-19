package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerState;

import java.util.TimerTask;

public class PendingSuspensionDisconnectedTimingState extends TimingState{
    public PendingSuspensionDisconnectedTimingState(TimingStateMachine timingStateMachine, Player previousPlayer) {
        super(timingStateMachine, previousPlayer);
        setTimerTask(new TimerTask() {
            @Override
            public void run() {
                timerGoOff();
            }
        });
        getTimingStateMachine().registerTimerTask(getTimerTask(), 10 * 1000);
    }

    @Override

    public synchronized void timerGoOff() {
        if(isTriggered() || getTimingStateMachine().getController().getState().getGameState() == GameState.END){
            return;
        }
        setTriggered();
        System.out.println("suspending");
        getTimingStateMachine().setTimingState(new InitTimingState(getTimingStateMachine(), getTimingStateMachine().getController().getState().getCurrentPlayer()));
        GameState gameState = getTimingStateMachine().getController().getState().getGameState();
        getTimingStateMachine().getController().getState().setGameState(GameState.SUSPENDED);
        getTimingStateMachine().getController().setGameManager(new SuspendedGameManager(getTimingStateMachine().getController(), gameState));
    }

    @Override
    public synchronized void currentPlayerStateChanged(Player player, PlayerState playerState) {
        if(isTriggered() || getTimingStateMachine().getController().getState().getGameState() == GameState.END){
            return;
        }
        if(playerState.equals(PlayerState.CONNECTED)) {
            getTimerTask().cancel();
            getTimingStateMachine().setTimingState(new ConnectedTimingState(getTimingStateMachine(),getTimingStateMachine().getController().getState().getCurrentPlayer()));
        }
    }

    @Override
    public boolean isDisconnectedTiming() {
        return false;
    }
}
