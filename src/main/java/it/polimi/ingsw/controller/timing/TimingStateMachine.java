package it.polimi.ingsw.controller.timing;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.listeners.OnCurrentPlayerChangedListener;
import it.polimi.ingsw.controller.listeners.OnStateChangedListener;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;

import java.util.Timer;
import java.util.TimerTask;

public class TimingStateMachine implements OnCurrentPlayerChangedListener, OnStateChangedListener {

    private final Controller controller;

    private TimingState timingState;

    private Timer timer;

    private Player previousPlayer;

    private final long turnDuration;


    public TimingStateMachine(Controller controller, long turnDuration) {
        this.controller = controller;
        this.timer = new Timer();
        previousPlayer = null;
        this.turnDuration = turnDuration;
        this.timingState = new InitGameTimingState(this);
    }

    public Player getPreviousPlayer() {
        return previousPlayer;
    }

    public void setPreviousPlayer(Player previousPlayer) {
        this.previousPlayer = previousPlayer;
    }

    public Controller getController() {
        return controller;
    }

    public void setTimingState(TimingState timingState) {
        this.timingState = timingState;
    }

    @Override
    public void onCurrentPlayerChangedListener(String nickname) {
        timingState.onCurrentPlayerChanged();
        System.err.println("finished timing listener");
    }

    @Override
    public void onStateChanged(GameState gameState) {
        timingState.onGameStateChanged();
    }

    public void registerTimerTask(TimerTask timerTask) {
        this.timer.schedule(timerTask, this.turnDuration);
    }


}
