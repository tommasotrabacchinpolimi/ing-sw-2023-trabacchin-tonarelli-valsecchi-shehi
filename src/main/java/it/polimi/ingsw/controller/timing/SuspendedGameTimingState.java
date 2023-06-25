package it.polimi.ingsw.controller.timing;

import it.polimi.ingsw.model.GameState;

public class SuspendedGameTimingState extends TimingState{

    public SuspendedGameTimingState(TimingStateMachine timingStateMachine) {
        super(timingStateMachine);
    }

    @Override
    public void onCurrentPlayerChanged() {
        //do nothing, cannot happen
    }

    @Override
    public void onGameStateChanged() {
        if(!getTimingStateMachine().getController().getState().getGameState().equals(GameState.SUSPENDED)) {
            getTimingStateMachine().setTimingState(new MidGameTimingState(getTimingStateMachine()));
        }
    }

    @Override
    public void timerGoOff() {
        //do nothing, cannot happen
    }
}
