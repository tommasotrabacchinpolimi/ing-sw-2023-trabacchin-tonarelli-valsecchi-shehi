package it.polimi.ingsw.controller.timing;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.listeners.OnCurrentPlayerChangedListener;
import it.polimi.ingsw.controller.listeners.OnStateChangedListener;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.State;

import java.util.Timer;
import java.util.TimerTask;

/**
 * The TimingStateMachine class manages the timing-related functionality of the game,
 * such as turn durations. It implements the {@linkplain OnCurrentPlayerChangedListener} and
 * {@linkplain OnStateChangedListener} interfaces to receive notifications about
 * changes in the {@linkplain State#getCurrentPlayer() current player} and
 * {@linkplain State#getGameState() game state}.
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public class TimingStateMachine implements OnCurrentPlayerChangedListener, OnStateChangedListener {
    /**
     * Controller of the game
     * @see Controller
     */
    private final Controller controller;
    /**
     * State og the Timing State Machine
     */
    private TimingState timingState;
    /**
     * Timer to terminate player turn.
     */
    private Timer timer;
    /**
     * Player whose turn has just terminated
     * @see Player
     */
    private Player previousPlayer;
    /**
     * Maximum duration of a turn
     */
    private final long turnDuration;

    /**
     * Constructs a TimingStateMachine object with the specified {@linkplain Controller} and {@linkplain #turnDuration turn duration}.
     * @param controller The game controller.
     * @param turnDuration The duration of each turn in milliseconds.
     * @see Controller
     */
    public TimingStateMachine(Controller controller, long turnDuration) {
        this.controller = controller;
        this.timer = new Timer();
        previousPlayer = null;
        this.turnDuration = turnDuration;
        this.timingState = new InitGameTimingState(this);
    }

    /**
     * Returns the previous player.
     * @return The previous player.
     * @see Player
     */
    public Player getPreviousPlayer() {
        return previousPlayer;
    }

    /**
     * Sets the previous player.
     * @param previousPlayer The previous player.
     * @see Player
     */
    public void setPreviousPlayer(Player previousPlayer) {
        this.previousPlayer = previousPlayer;
    }

    /**
     * Returns the game controller.
     * @return The game controller.
     * @see Controller
     */
    public Controller getController() {
        return controller;
    }

    /**
     * Sets the current timing state.
     * @param timingState The timing state to set.
     * @see TimingState
     */
    public void setTimingState(TimingState timingState) {
        this.timingState = timingState;
    }

    /**
     * Called when the current player has changed.
     * @param nickname The nickname of the new current player.
     * @see OnCurrentPlayerChangedListener#onCurrentPlayerChangedListener(String)
     */
    @Override
    public void onCurrentPlayerChangedListener(String nickname) {
        timingState.onCurrentPlayerChanged();
    }

    /**
     * Called when the game state has changed.
     * @param gameState The new game state.
     * @see OnStateChangedListener
     * @see GameState
     */
    @Override
    public void onStateChanged(GameState gameState) {
        timingState.onGameStateChanged();
    }

    /**
     * Registers a {@linkplain TimerTask timer task} to be executed after the {@linkplain #turnDuration turn duration}.
     * @param timerTask The timer task to be registered.
     */
    public void registerTimerTask(TimerTask timerTask) {
        this.timer.schedule(timerTask, this.turnDuration);
    }

}
