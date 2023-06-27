package it.polimi.ingsw.controller.timing;

import it.polimi.ingsw.model.GameState;

/**
 * The InitGameTimingState class represents the initial state in the timing state machine.
 * This state is triggered when the game is in the {@linkplain GameState#INIT} state.
 * @see it.polimi.ingsw.controller.timing.TimingState
 * @see TimingState
 * @see GameState
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public class InitGameTimingState extends TimingState{
    /**
     * Constructs an InitGameTimingState object with the specified timing state machine.
     * @param timingStateMachine The timing state machine.
     * @see TimingStateMachine
     */
    public InitGameTimingState(TimingStateMachine timingStateMachine) {
        super(timingStateMachine);
    }

    /**
     * {@inheritDoc}
     * @see TimingState#onCurrentPlayerChanged()
     */
    @Override
    public synchronized void onCurrentPlayerChanged() {
        if(getTimingStateMachine().getController().getState().getGameState().equals(GameState.MID)) {
            getTimingStateMachine().setTimingState(new MidGameTimingState(getTimingStateMachine()));
        } else if(getTimingStateMachine().getController().getState().getGameState().equals(GameState.SUSPENDED)) {
            getTimingStateMachine().setTimingState(new SuspendedGameTimingState(getTimingStateMachine()));
        }    }

    /**
     * {@inheritDoc}
     * @see TimingState#onGameStateChanged()
     */
    @Override
    public void onGameStateChanged() {
        //do nothing
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
