package it.polimi.ingsw.controller.timing;

import it.polimi.ingsw.controller.SuspendedGameManager;
import it.polimi.ingsw.model.GameState;

import java.util.TimerTask;

/**
 * The MidGameTimingState class represents the {@linkplain GameState#MID mid-game state} in the {@linkplain TimingStateMachine timing state machine}.
 * This state is triggered when the game is in the {@linkplain GameState#MID}.
 * @see TimingStateMachine
 * @see TimingState
 * @see GameState
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public class MidGameTimingState extends TimingState{
    /**
     * Constructs a MidGameTimingState object with the specified timing state machine.
     * @param timingStateMachine The timing state machine.
     * @see TimingStateMachine
     */
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

    /**
     * {@inheritDoc}
     * @see TimingState#onCurrentPlayerChanged()
     */
    @Override
    public synchronized void onCurrentPlayerChanged() {
        if(!(isAlreadyTriggered() || getTimingStateMachine().getController().getState().getCurrentPlayer().equals(getTimingStateMachine().getPreviousPlayer()) || getTimingStateMachine().getController().getState().getGameState().equals(GameState.END))) {
            getTimerTask().cancel();
            handleCurrentPlayerChange();
        }

    }

    /**
     * {@inheritDoc}
     * @see TimingState#onGameStateChanged()
     */
    @Override
    public synchronized void onGameStateChanged() {
        //do nothing
    }

    /**
     * {@inheritDoc}
     * @see TimingState#timerGoOff()
     */
    @Override
    public synchronized void timerGoOff() {
        if(!isAlreadyTriggered() || getTimingStateMachine().getController().getState().getGameState().equals(GameState.END)) {

            getTimingStateMachine().getController().getGameManager().setNextCurrentPlayer();
        }
    }

    /**
     * Handles the current player change and transitions to the appropriate timing state.
     * @see TimingState
     * @see GameState
     */
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
