package it.polimi.ingsw.controller.timing;

import it.polimi.ingsw.model.GameState;

/**
 * The SuspendedGameTimingState class represents the suspended game state in the {@linkplain TimingStateMachine timing state machine}.
 * This state is triggered when the game is in the {@linkplain GameState#SUSPENDED} state.
 * @see TimingState
 * @see TimingStateMachine
 * @see GameState
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public class SuspendedGameTimingState extends TimingState{
    /**
     * Constructs a SuspendedGameTimingState object with the specified timing state machine.
     * @param timingStateMachine The timing state machine.
     * @see TimingStateMachine
     */
    public SuspendedGameTimingState(TimingStateMachine timingStateMachine) {
        super(timingStateMachine);
    }

    /**
     * {@inheritDoc}
     * @see TimingState#onCurrentPlayerChanged()
     */
    @Override
    public void onCurrentPlayerChanged() {
        //do nothing, cannot happen
    }

    /**
     * {@inheritDoc}
     * @see TimingState#onGameStateChanged()
     * @see GameState
     */
    @Override
    public void onGameStateChanged() {
        if(!getTimingStateMachine().getController().getState().getGameState().equals(GameState.SUSPENDED)) {
            getTimingStateMachine().setTimingState(new MidGameTimingState(getTimingStateMachine()));
        }
    }

    /**
     * {@inheritDoc}
     * @see TimingState#timerGoOff()
     */
    @Override
    public void timerGoOff() {
        //do nothing, cannot happen
    }
}
