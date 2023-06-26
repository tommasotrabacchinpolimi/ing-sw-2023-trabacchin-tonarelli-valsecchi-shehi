package it.polimi.ingsw.controller.timing;

import java.util.TimerTask;

public abstract class TimingState {


    private final TimingStateMachine timingStateMachine;

    private TimerTask timerTask;

    private boolean alreadyTriggered;

    public TimingState(TimingStateMachine timingStateMachine) {
        this.timingStateMachine = timingStateMachine;
        timerTask = null;
    }



    public TimingStateMachine getTimingStateMachine() {
        return timingStateMachine;
    }

    public abstract void onCurrentPlayerChanged();

    public abstract void onGameStateChanged();

    public abstract void timerGoOff();

    public synchronized TimerTask getTimerTask() {
        return timerTask;
    }

    public synchronized void setTimerTask(TimerTask timerTask) {
        this.timerTask = timerTask;
    }

    public synchronized boolean isAlreadyTriggered() {
        return alreadyTriggered;
    }

    public synchronized void setAlreadyTriggered(boolean alreadyTriggered) {
        this.alreadyTriggered = alreadyTriggered;
    }
}
