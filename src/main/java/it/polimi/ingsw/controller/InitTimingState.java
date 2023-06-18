package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerState;

/**
 *
 * The {@code InitTimingState} class represents the timing state during the initialization phase of the game.
 *
 * It extends the {@code TimingState} abstract class and provides specific behavior for the initial timing state.
 *
 * This timing state is responsible for managing the timing and state transitions during the initialization phase of the game.
 *
 * <p>
 * This class overrides methods from the {@code TimingState} class to provide custom behavior for the initialization timing state.
 *
 * It includes the {@code timerGoOff()} method to handle the timer expiration event, and the {@code currentPlayerStateChanged()}
 *
 * method to handle changes in the player's state during the initialization phase.
 *
 * </p>
 * <p>
 * The {@code InitTimingState} class is used by the {@code TimingStateMachine} to control the flow and timing of the game.
 *
 * It is initialized with the previous player, and it provides a method to check if it represents a disconnected timing state.
 *
 * </p>
 * @see TimingState
 *
 * @see TimingStateMachine
 *
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 * @since 28/04/2023
 * @version 2.0
 */
public class InitTimingState extends TimingState{

    /**
     Constructs a new {@code InitTimingState} with the specified timing state machine and previous player.
     @param timingStateMachine the timing state machine controlling the game flow
     @param previousPlayer the previous player in the game
     */
    public InitTimingState(TimingStateMachine timingStateMachine, Player previousPlayer) {
        super(timingStateMachine, previousPlayer);
    }
    @Override
    public synchronized void timerGoOff() {

    }

    @Override
    public synchronized void currentPlayerStateChanged(Player player, PlayerState playerState) {

    }

    /**
     *{@inheritDoc}
     *Returns {@code false} since the initialization timing state is not a disconnected timing state.
     */
    @Override
    public boolean isDisconnectedTiming() {
        return false;
    }
}
