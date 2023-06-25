package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerState;

import java.util.TimerTask;

/**
 * The ConnectedTimingState class represents a timing state when the player is connected and active.
 *
 * It extends the TimingState class and defines the behavior specific to this state.
 *
 * This state is responsible for managing the timing and transitions between players during the game.
 *
 * It sets a timer task that triggers the transition to the next player when the timer goes off.
 *
 * If a player disconnects or the game state reaches the end, the timer is canceled and no transition occurs.
 *
 * This state does not handle player state changes.
 *
 * @see TimingState
 *
 * @see TimingStateMachine
 *
 * @see GameState
 *
 * @see Player
 *
 * @see PlayerState
 *
 * @see TimerTask
 *
 * @author Tommaso Trabbachin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 * @version 3.0
 * @since 06/04/2023
 */
public class ConnectedTimingState extends TimingState{

    /**
     * Constructs a ConnectedTimingState object with the given timing state machine and previous player.
     * <br>
     * Initializes the timer task and registers it with the timing state machine to trigger after 60 seconds.
     * @param timingStateMachine the timing state machine controlling the state transitions
     * @param previousPlayer the previous player in the game
     */

    private final long delay;
    public ConnectedTimingState(TimingStateMachine timingStateMachine, Player previousPlayer, long delay) {

        super(timingStateMachine, previousPlayer, delay);
        System.err.println("in connected timing state");
        this.delay = delay;
        setTimerTask(new TimerTask() {
            @Override
            public void run() {
                timerGoOff();
            }
        });
        getTimingStateMachine().registerTimerTask(getTimerTask(), delay);
    }
    @Override
    public synchronized void timerGoOff() {
        if(isTriggered() || getTimingStateMachine().getController().getState().getGameState() == GameState.END){
            return;
        }
        setTriggered();
        getTimingStateMachine().setTimingState(new InitTimingState(getTimingStateMachine(), getTimingStateMachine().getController().getState().getCurrentPlayer(), delay));
        getTimingStateMachine().getController().getGameManager().setNextCurrentPlayer();
    }

    @Override
    public synchronized void currentPlayerChanged(Player player) {
        if(player.equals(getPreviousPlayer())) {
            return;
        }
        getTimerTask().cancel();
        super.currentPlayerChanged(player);
    }

    @Override
    public synchronized void currentPlayerStateChanged(Player player, PlayerState playerState) {

    }

}
