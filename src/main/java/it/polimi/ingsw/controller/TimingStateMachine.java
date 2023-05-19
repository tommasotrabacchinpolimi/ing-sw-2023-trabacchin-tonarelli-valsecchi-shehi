package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.listeners.OnCurrentPlayerChangedListener;
import it.polimi.ingsw.controller.listeners.OnPlayerStateChangedListener;
import it.polimi.ingsw.controller.listeners.OnStateChangedListener;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerState;

import java.util.Timer;
import java.util.TimerTask;

public class TimingStateMachine implements OnPlayerStateChangedListener, OnCurrentPlayerChangedListener {
    private TimingState timingState;
    private Controller controller;

    private Timer timer;

    public TimingStateMachine(Controller controller) {
        timer = new Timer();
        timingState = new InitTimingState(this);
        this.controller = controller;
    }
    @Override
    public void onCurrentPlayerChangedListener(String nickname) {
        timingState.currentPlayerChanged(controller.getState().getCurrentPlayer());
    }

    @Override
    public void onPlayerStateChanged(String nickname, PlayerState playerState) {
        if(controller.getState().getCurrentPlayer()!=null && controller.getState().getCurrentPlayer().getNickName().equals(nickname)) {
            timingState.currentPlayerStateChanged(controller.getState().getCurrentPlayer(), playerState);
        }
    }
    public void registerTimerTask(TimerTask timerTask, long delay) {
        timer.schedule(timerTask, delay);
    }

    public void setTimingState(TimingState timingState) {
        this.timingState = timingState;
    }

    public void timerGoOff() {
        timingState.timerGoOff();
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
}
