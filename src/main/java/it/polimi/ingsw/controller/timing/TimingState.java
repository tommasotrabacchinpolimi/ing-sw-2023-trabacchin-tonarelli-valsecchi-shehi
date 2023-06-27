package it.polimi.ingsw.controller.timing;

import java.util.TimerTask;

/**
 * The TimingState class represents an abstract state in the {@linkplain TimingStateMachine timing state machine}.
 * Subclasses of TimingState define specific behaviors for different game states.
 * @see TimingStateMachine
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public abstract class TimingState {
    /**
     * Timing state machine associated with the timing state
     */
    private final TimingStateMachine timingStateMachine;
    /**
     * Timer task for the turn.
     */
    private TimerTask timerTask;
    /**
     * Prevents the timer to be triggered after the state transition is occurred.
     */
    private boolean alreadyTriggered;

    /**
     * Constructs a TimingState object with the specified timing state machine.
     * @param timingStateMachine The timing state machine.
     */
    public TimingState(TimingStateMachine timingStateMachine) {
        this.timingStateMachine = timingStateMachine;
        timerTask = null;
    }

    /**
     * Returns the timing state machine associated with this state.
     * @return The timing state machine.
     * @see TimingStateMachine
     */
    public TimingStateMachine getTimingStateMachine() {
        return timingStateMachine;
    }

    /**
     * Called when the current player has changed.
     */
    public abstract void onCurrentPlayerChanged();

    /**
     * Called when the game state has changed.
     */
    public abstract void onGameStateChanged();

    /**
     * Called when the timer goes off.
     */
    public abstract void timerGoOff();

    /**
     * Returns the timer task associated with this timing state.
     * @return The timer task.
     */
    public synchronized TimerTask getTimerTask() {
        return timerTask;
    }

    /**
     * Sets the timer task associated with this timing state.
     * @param timerTask The timer task to set.
     */
    public synchronized void setTimerTask(TimerTask timerTask) {
        this.timerTask = timerTask;
    }

    /**
     * Returns whether the state has already been triggered.
     * @return true if the state has already been triggered, false otherwise.
     */
    public synchronized boolean isAlreadyTriggered() {
        return alreadyTriggered;
    }

    /**
     * Sets whether the state has already been triggered.
     * @param alreadyTriggered true if the state has already been triggered, false otherwise.
     */
    public synchronized void setAlreadyTriggered(boolean alreadyTriggered) {
        this.alreadyTriggered = alreadyTriggered;
    }
}
