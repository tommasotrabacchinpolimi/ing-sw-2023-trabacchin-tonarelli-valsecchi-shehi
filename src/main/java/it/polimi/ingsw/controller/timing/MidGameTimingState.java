package it.polimi.ingsw.controller.timing;

import it.polimi.ingsw.controller.SuspendedGameManager;
import it.polimi.ingsw.model.GameState;

import java.util.TimerTask;

public class MidGameTimingState extends TimingState{
    public MidGameTimingState(TimingStateMachine timingStateMachine) {
        super(timingStateMachine);
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("timer gone off");
                timerGoOff();
            }
        };
        setTimerTask(timerTask);
        getTimingStateMachine().registerTimerTask(timerTask);
    }

    @Override
    public synchronized void onCurrentPlayerChanged() {
        if(!(isAlreadyTriggered() || getTimingStateMachine().getController().getState().getCurrentPlayer().equals(getTimingStateMachine().getPreviousPlayer()) || getTimingStateMachine().getController().getState().getGameState().equals(GameState.END))) {
            getTimerTask().cancel();
            handleCurrentPlayerChange();
        }

    }

    @Override
    public synchronized void onGameStateChanged() {
        //do nothing
    }

    @Override
    public synchronized void timerGoOff() {
        if(!isAlreadyTriggered() || getTimingStateMachine().getController().getState().getGameState().equals(GameState.END)) {

            getTimingStateMachine().getController().getGameManager().setNextCurrentPlayer();
        }
    }

    private void handleCurrentPlayerChange() {
        setAlreadyTriggered(true);

        if(getTimingStateMachine().getController().getGameManager().verifyAllDisconnectedPlayer(getTimingStateMachine().getPreviousPlayer())) {
            getTimingStateMachine().setPreviousPlayer(getTimingStateMachine().getController().getState().getCurrentPlayer());
            getTimingStateMachine().getController().setGameManager(new SuspendedGameManager(getTimingStateMachine().getController(), getTimingStateMachine().getController().getState().getGameState()));
            getTimingStateMachine().getController().getState().setGameState(GameState.SUSPENDED);
            getTimingStateMachine().setTimingState(new SuspendedGameTimingState(getTimingStateMachine()));
        }
        else {
            getTimingStateMachine().setPreviousPlayer(getTimingStateMachine().getController().getState().getCurrentPlayer());
            getTimingStateMachine().setTimingState(new MidGameTimingState(getTimingStateMachine()));
        }
    }
}
