package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.listeners.OnCurrentPlayerChangedListener;
import it.polimi.ingsw.controller.listeners.OnPlayerStateChangedListener;
import it.polimi.ingsw.controller.listeners.OnStateChangedListener;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerState;

import java.util.Timer;
import java.util.TimerTask;

/*METTERE VERSIONE E DATA CORRETTA NEL JAVADOC*/
/**
 * <p>
 * The TimingStateMachine class is responsible for managing the timer state in the game.
 * <br>
 * It implements the OnPlayerStateChangedListener and OnCurrentPlayerChangedListener interfaces.
 * <br>
 * These interfaces are used to receive notifications when the current player or a player's state changes.
 * </p>
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 *
 * @version
 * @since
 *
 *
 */
public class TimingStateMachine implements OnPlayerStateChangedListener, OnCurrentPlayerChangedListener {


    private TimingState timingState;
    private Controller controller;

    private final Timer timer;

    /**
     * Constructs a new TimingStateMachine with the specified Controller.
     *
     * @param controller the Controller instance
     */
    public TimingStateMachine(Controller controller, long delay) {
        timer = new Timer();
        timingState = new InitTimingState(this, null, delay);
        this.controller = controller;
    }

    /**
     * {@inheritDoc}
     * This method is called when the current player changes.
     * It delegates the current player change event to the current timing state.
     *
     * @param nickname the nickname of the new current player
     */
    @Override
    public void onCurrentPlayerChangedListener(String nickname) {
        timingState.currentPlayerChanged(controller.getState().getCurrentPlayer());
    }

    /**
     * {@inheritDoc}
     * This method is called when a player's state changes.
     * If the current player's nickname matches the given nickname, it delegates
     * <br>
     * the player state change event to the current timing state.
     *
     * @param nickname the nickname of the player whose state changed
     * @param playerState the new state of the player
     */
    @Override
    public void onPlayerStateChanged(String nickname, PlayerState playerState) {
        if(controller.getState().getCurrentPlayer()!=null && controller.getState().getCurrentPlayer().getNickName().equals(nickname)) {
            timingState.currentPlayerStateChanged(controller.getState().getCurrentPlayer(), playerState);
        }
    }

    /**
     * Registers a TimerTask with the specified delay.
     * <br>
     * This method is used to schedule a task to be executed by the timer.
     * @param timerTask the TimerTask to be scheduled
     * @param delay the delay in milliseconds before the task is executed
     */
    public void registerTimerTask(TimerTask timerTask, long delay) {
        timer.schedule(timerTask, delay);
    }

    /**
     * Sets the current timing state.
     *
     * @param timingState the new timing state
     */
    public void setTimingState(TimingState timingState) {
        this.timingState = timingState;
    }



    /**
     * Returns the Controller instance associated with the timing state machine.
     *
     * @return the Controller instance
     */
    public Controller getController() {
        return controller;
    }

    public TimingState getTimingState() {
        return timingState;
    }




}
