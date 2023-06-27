package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.ClientInterface;
import it.polimi.ingsw.controller.JSONExclusionStrategy.ExcludedFromJSON;
import it.polimi.ingsw.controller.listeners.OnAssignedPersonalGoalListener;
import it.polimi.ingsw.controller.listeners.OnPlayerStateChangedListener;
import it.polimi.ingsw.controller.listeners.localListeners.OnUpdateNeededListener;

import java.io.Serial;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Player is a class that represent a player of the game. Players are identified by a {@link Player#nickName}, which is unique within the game.
 * Each player is associated with a {@link PersonalGoal}, that the player must satisfy in order to earn more points. The score earned by the player
 * is saved in the {@link Player#pointPlayer} attribute that always has a null or positive value.
 *
 * @see PersonalGoal
 *
 * <p>
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 * @version 3.0
 * </p>
 * @since 10/04/2023
 */
public class Player implements Serializable, OnUpdateNeededListener {
    @Serial
    @ExcludedFromJSON
    private static final long serialVersionUID = 97354642643274L;

    /**
     * String that represents the nickname of the player
     */
    private final String nickName;

    /**
     * Object that represent the {@link PersonalGoal personal goal} associated exclusively to the player.
     */
    @ExcludedFromJSON
    private PersonalGoal personalGoal;

    /**
     * Object that represent the {@link BookShelf bookshelf} associated exclusively to the player.
     */
    @ExcludedFromJSON
    private BookShelf bookShelf;

    /**
     * Object that represent the {@link PointPlayer  player's points}..
     */
    @ExcludedFromJSON
    private PointPlayer pointPlayer;

    /**
     * Object that represent the {@link PlayerState state} of the player.
     */
    @ExcludedFromJSON
    private PlayerState playerState;

    /**
     * Client Inteface associated with the Player
     * @see ClientInterface
     */
    @ExcludedFromJSON
    private ClientInterface virtualView;

    /**
     * List of {@linkplain OnPlayerStateChangedListener}
     */
    @ExcludedFromJSON
    private final List<OnPlayerStateChangedListener> onPlayerStateChangedListeners;

    /**
     * List of {@linkplain OnAssignedPersonalGoalListener}
     */
    @ExcludedFromJSON
    private final List<OnAssignedPersonalGoalListener> onAssignedPersonalGoalListeners;

    /**
     * List of {@linkplain OnUpdateNeededListener}
     */
    @ExcludedFromJSON
    private final List<OnUpdateNeededListener> onUpdateNeededListeners;

    /**
     * Constructor that sets the fields of the class to the parameter passed.
     * @param nickName A {@link String} representing the nickname of the player.
     * @param virtualView
     */
    public Player(String nickName, ClientInterface virtualView) {
        this.nickName = nickName;
        this.personalGoal = null;
        this.bookShelf = new BookShelf();
        this.pointPlayer = new PointPlayer();
        this.playerState = PlayerState.DISCONNECTED;
        this.virtualView = virtualView;
        onPlayerStateChangedListeners = new LinkedList<>();
        onAssignedPersonalGoalListeners = new LinkedList<>();
        onUpdateNeededListeners = new LinkedList<>();
    }

    /**
     * Constructor that sets the fields of the class to the parameter passed.
     * @param nickName A {@link String} representing the nickname of the player.
     */
    public Player(String nickName) {
        this.nickName = nickName;
        this.personalGoal = null;
        this.bookShelf = new BookShelf();
        this.pointPlayer = new PointPlayer();
        this.playerState = PlayerState.DISCONNECTED;
        onPlayerStateChangedListeners = new LinkedList<>();
        onAssignedPersonalGoalListeners = new LinkedList<>();
        onUpdateNeededListeners = new LinkedList<>();
    }

    /**
     * Constructor that sets the fields of the class to the parameter passed.
     * @param nickName A {@link String} representing the nickname of the player.
     * @param personalGoal The {@link PersonalGoal} that will be associated to the player.
     *
     * @see PersonalGoal
     */
    public Player( String  nickName, PersonalGoal personalGoal ){
        this.nickName = nickName;
        this.personalGoal = personalGoal;
        this.bookShelf = new BookShelf();
        this.pointPlayer = new PointPlayer();
        this.playerState = PlayerState.DISCONNECTED;
        onPlayerStateChangedListeners = new LinkedList<>();
        onAssignedPersonalGoalListeners = new LinkedList<>();
        onUpdateNeededListeners = new LinkedList<>();
    }

    /**
     * Method that return the player's {@link #playerState state} associated with the player.
     * @return the {@link #playerState state} associated to the player.
     *
     * @see PlayerState
     */
    public PlayerState getPlayerState() {
        return playerState;
    }

    /**
     * Method that sets the player's {@link #playerState}.
     * @param playerState the {@link PlayerState} that need to be set to {@link #playerState}.
     */
    public void setPlayerState(PlayerState playerState) {
        PlayerState oldPlayerState = this.playerState;
        this.playerState = playerState;
        notifyOnPlayerStateChanged();
        if(oldPlayerState == PlayerState.DISCONNECTED && playerState == PlayerState.CONNECTED) {
            notifyOnUpdateNeeded();
        }
    }

    /**
     * Returns the virtual view associated with the player.
     * @return the virtual view
     * @see ClientInterface
     */
    public ClientInterface getVirtualView() {
        return virtualView;
    }

    /**
     * Sets the virtual view for the player.
     * @param virtualView the virtual view to set
     * @see ClientInterface
     */
    public void setVirtualView(ClientInterface virtualView) {
        this.virtualView = virtualView;
    }

    /**
     * Method that return the {@link BookShelf} associated to the player.
     * @return the player's {@link #bookShelf bookshelf}.
     */
    public BookShelf getBookShelf() {
        return bookShelf;
    }

    /**
     * Method that sets the {@link #bookShelf bookshelf} associated to the player.
     * @param bookShelf  the {@link BookShelf} that need to be set to the player..
     */
    public void setBookShelf(BookShelf bookShelf) {
        this.bookShelf = bookShelf;
    }

    /**
     * Method that sets the {@code endGameScore} value to the attribute {@code scoreEndGame} in the player's {@link #pointPlayer}.
     * @param endGameScore the value that need to be set to the {@code scoreEndGame} attribute in player's {@link #pointPlayer}.
     * @see PointPlayer
     */
    public void assignScoreEndGame(int endGameScore) {
        this.pointPlayer.setScoreEndGame(endGameScore);
    }

    /**
     * Method that returns the player's {@link #pointPlayer}.
     * @return the {@link PointPlayer} associated to the player.
     *
     * @see {@link PointPlayer}
     */
    public PointPlayer getPointPlayer() {
        return pointPlayer;
    }

    /**
     * Method that sets the player's {@link #pointPlayer}
     * @param pointPlayer the {@link PointPlayer} that needs to be set to the player.
     */
    public void setPointPlayer(PointPlayer pointPlayer) {
        this.pointPlayer = pointPlayer;
    }

    /**
     * Method that gets the nickname of the player.
     * @return A {@link String} representing the nickname of the player.
     */
    public String getNickName(){
        return nickName;
    }

    /**
     * Method that gets the {@link PersonalGoal} of the player.
     * @return The {@link PersonalGoal} associated with the player.
     *
     * @see PersonalGoal
     */
    public PersonalGoal getPersonalGoal() {
        return personalGoal;
    }

    /**
     * Method that sets the {@link PersonalGoal} of the player.
     * @param personalGoal the {@link PersonalGoal} that needs to be set to the player.
     */
    public void setPersonalGoal(PersonalGoal personalGoal) {
        this.personalGoal = personalGoal;
        notifyOnAssignedPersonalGoal();
    }

    /**
     * Overriding toString() default method.
     * @return A {@link String} representing the object {@link Player}.
     * @apiNote Resulting String will be displayed only on a lines as follows:
     * {@code Player{nickname= }}
     */
    @Override
    public String toString() {
        return "Player{" +
                "nickname=" + getNickName() +
                '}';
    }

    /**
     * Sets the listener for player state changes.
     * @param onPlayerStateChangedListener the listener to set
     * @see OnPlayerStateChangedListener
     */
    public void setOnPlayerStateChangedListener(OnPlayerStateChangedListener onPlayerStateChangedListener) {
        onPlayerStateChangedListeners.add(onPlayerStateChangedListener);
    }

    /**
     * Removes the listener for player state changes.
     * @param onPlayerStateChangedListener the listener to remove
     * @see OnPlayerStateChangedListener
     */
    public void removeOnPlayerStateChangedListener(OnPlayerStateChangedListener onPlayerStateChangedListener) {
        onPlayerStateChangedListeners.remove(onPlayerStateChangedListener);
    }

    /**
     * Sets the listener for assigned personal goal events.
     * @param onAssignedPersonalGoalListener  the listener to set
     * @see OnAssignedPersonalGoalListener
     */
    public void setOnAssignedPersonalGoalListener(OnAssignedPersonalGoalListener onAssignedPersonalGoalListener) {
        onAssignedPersonalGoalListeners.add(onAssignedPersonalGoalListener);
    }

    /**
     * Removes the listener for assigned personal goal events.
     * @param onAssignedPersonalGoalListener  the listener to remove
     * @see OnAssignedPersonalGoalListener
     */
    public void removeOnAssignedPersonalGoalListener(OnAssignedPersonalGoalListener onAssignedPersonalGoalListener) {
        onAssignedPersonalGoalListeners.remove(onAssignedPersonalGoalListener);
    }

    /**
     * Notifies all registered listeners about the assigned personal goal.
     */
    public void notifyOnAssignedPersonalGoal() {
        for(OnAssignedPersonalGoalListener onAssignedPersonalGoalListener : onAssignedPersonalGoalListeners) {
            onAssignedPersonalGoalListener.onAssignedPersonalGoal(this.nickName, this.personalGoal.getGoalPattern(), this.personalGoal.getScoreMap());
        }
    }

    /**
     * Notifies all registered listeners about the player state change.
     */
    public void notifyOnPlayerStateChanged() {
        for(OnPlayerStateChangedListener onPlayerStateChangedListener : onPlayerStateChangedListeners) {
            onPlayerStateChangedListener.onPlayerStateChanged(this.nickName, this.playerState);
        }
    }

    /**
     * Notifies all registered listeners that an update is needed.
     */
    public void notifyOnUpdateNeeded() {
        for(OnUpdateNeededListener onUpdateNeededListener : onUpdateNeededListeners) {
            onUpdateNeededListener.onUpdateNeededListener(this);
        }
    }

    /**
     * Listener method called when an update is needed.
     * @param player the player triggering the update
     */
    @Override
    public void onUpdateNeededListener(Player player) {
        onPlayerStateChangedListeners.stream().forEach(v->v.onPlayerStateChanged(this.nickName,this.playerState));
        if(this.personalGoal != null) {
            onAssignedPersonalGoalListeners.stream().forEach(v->v.onAssignedPersonalGoal(this.nickName,this.personalGoal.getGoalPattern(), this.personalGoal.getScoreMap()));
        }
    }

    /**
     * Sets the listener for update needed events.
     * @param onUpdateNeededListener the listener to set
     */
    public void setOnUpdateNeededListener(OnUpdateNeededListener onUpdateNeededListener) {
        onUpdateNeededListeners.add(onUpdateNeededListener);
    }

    /**
     * Overriding equals() default method.
     * @param o the reference object with which to compare
     * @return {@code true} if and only if this object is the same as the object argument; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;

        if(o == null || getClass() != o.getClass())
            return false;

        Player thatPlayer = (Player) o;

        return nickName.equals(thatPlayer.nickName) &&
                Objects.equals(personalGoal, thatPlayer.personalGoal) &&
                Objects.equals(bookShelf, thatPlayer.bookShelf) &&
                Objects.equals(pointPlayer, thatPlayer.pointPlayer) &&
                playerState == thatPlayer.playerState &&
                Objects.equals(virtualView, thatPlayer.virtualView) &&
                Objects.equals(onPlayerStateChangedListeners, thatPlayer.onPlayerStateChangedListeners) &&
                Objects.equals(onAssignedPersonalGoalListeners, thatPlayer.onAssignedPersonalGoalListeners);
    }

    /**
     * Overriding hashCode() default method.
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(nickName, personalGoal, bookShelf, pointPlayer, playerState, virtualView, onPlayerStateChangedListeners, onAssignedPersonalGoalListeners);
    }
}