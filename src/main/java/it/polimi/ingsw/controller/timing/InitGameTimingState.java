package it.polimi.ingsw.controller.timing;

import it.polimi.ingsw.model.GameState;

public class InitGameTimingState extends TimingState{
    public InitGameTimingState(TimingStateMachine timingStateMachine) {
        super(timingStateMachine);
    }

    @Override
    public void onCurrentPlayerChanged() {
        if(getTimingStateMachine().getController().getState().getGameState().equals(GameState.MID)) {
            getTimingStateMachine().setTimingState(new MidGameTimingState(getTimingStateMachine()));
        } else if(getTimingStateMachine().getController().getState().getGameState().equals(GameState.SUSPENDED)) {
            getTimingStateMachine().setTimingState(new SuspendedGameTimingState(getTimingStateMachine()));
        }    }

    @Override
    public void onGameStateChanged() {
        //do nothing
    }

    @Override
    public void timerGoOff() {
        //do nothing, cannot happen
    }
}
