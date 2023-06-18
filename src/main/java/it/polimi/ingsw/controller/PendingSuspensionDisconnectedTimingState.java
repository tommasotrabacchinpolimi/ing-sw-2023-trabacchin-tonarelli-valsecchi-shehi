package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerState;

import java.util.TimerTask;

/**
 *
 * The PendingSuspensionDisconnectedTimingState class represents the timing
 * <br>
 * state for a pending suspension of a disconnected player.
 * <br>
 * It extends the TimingState class.
 *
 */

public class PendingSuspensionDisconnectedTimingState extends TimingState{
    /**
     * Constructs a new PendingSuspensionDisconnectedTimingState with the specified TimingStateMachine and previous player.
     *
     * @param timingStateMachine the TimingStateMachine to which this state belongs
     * @param previousPlayer the player who was previously playing
     */
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


    /**
     * {@inheritDoc}
     * <p>
     * This method is called when the timer goes off.
     * <br>
     * It transitions to the InitTimingState and suspends the game.
     * </p>
     */
    @Override
    public synchronized void timerGoOff() {
        if(isTriggered() || getTimingStateMachine().getController().getState().getGameState() == GameState.END){
            return;
        }
        setTriggered();
        getTimingStateMachine().setTimingState(new InitTimingState(getTimingStateMachine(), getTimingStateMachine().getController().getState().getCurrentPlayer()));
        GameState gameState = getTimingStateMachine().getController().getState().getGameState();
        getTimingStateMachine().getController().getState().setGameState(GameState.SUSPENDED);
        getTimingStateMachine().getController().setGameManager(new SuspendedGameManager(getTimingStateMachine().getController(), gameState));
    }
    /*
        *****************************************************************************************
        * ***************************************************************************************
        * ***************************************************************************************
     */

    /**
     * {@inheritDoc}
     * This method is called when the current player's state changes. If the player becomes connected, it transitions to the ConnectedTimingState.
     *
     * @param player is the player that needs to be checked if the state has changed
     * @param playerState is the {@link PlayerState} of the player
     */
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

    /**
     * {@inheritDoc}
     * This method indicates whether this timing state is for a disconnected player.
     */
    @Override
    public boolean isDisconnectedTiming() {
        return false;
    }
}
