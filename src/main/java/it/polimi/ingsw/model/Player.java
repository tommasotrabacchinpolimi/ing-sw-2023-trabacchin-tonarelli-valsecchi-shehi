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
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 * @version 3.0
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

    @ExcludedFromJSON
    private PersonalGoal personalGoal;

    @ExcludedFromJSON
    private BookShelf bookShelf;

    @ExcludedFromJSON
    private PointPlayer pointPlayer;

    @ExcludedFromJSON
    private PlayerState playerState;

    @ExcludedFromJSON
    private ClientInterface virtualView;

    @ExcludedFromJSON
    private final List<OnPlayerStateChangedListener> onPlayerStateChangedListeners;

    @ExcludedFromJSON
    private final List<OnAssignedPersonalGoalListener> onAssignedPersonalGoalListeners;
    @ExcludedFromJSON
    private final List<OnUpdateNeededListener> onUpdateNeededListeners;

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

    public PlayerState getPlayerState() {
        return playerState;
    }

    public void setPlayerState(PlayerState playerState) {
        PlayerState oldPlayerState = this.playerState;
        this.playerState = playerState;
        notifyOnPlayerStateChanged();
        if(oldPlayerState == PlayerState.DISCONNECTED && playerState == PlayerState.CONNECTED) {
            notifyOnUpdateNeeded();
        }
    }

    public ClientInterface getVirtualView() {
        return virtualView;
    }

    public void setVirtualView(ClientInterface virtualView) {
        this.virtualView = virtualView;
    }

    public BookShelf getBookShelf() {
        return bookShelf;
    }

    public void setBookShelf(BookShelf bookShelf) {
        this.bookShelf = bookShelf;
    }

    public void assignScoreEndGame(int endGameScore) {
        this.pointPlayer.setScoreEndGame(endGameScore);
    }

    public PointPlayer getPointPlayer() {
        return pointPlayer;
    }

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

    public void setPersonalGoal(PersonalGoal personalGoal) {
        this.personalGoal = personalGoal;
        notifyOnAssignedPersonalGoal();
    }

    /**
     * Method that returns a {@link String} representing the object {@link Player}.
     * @return A {@link String} representing the object {@link Player}.
     */
    @Override
    public String toString() {
        return "Player{" +
                "nickname=" + getNickName() +
                '}';
    }

    public void setOnPlayerStateChangedListener(OnPlayerStateChangedListener onPlayerStateChangedListener) {
        onPlayerStateChangedListeners.add(onPlayerStateChangedListener);
    }

    public void removeOnPlayerStateChangedListener(OnPlayerStateChangedListener onPlayerStateChangedListener) {
        onPlayerStateChangedListeners.remove(onPlayerStateChangedListener);
    }

    public void setOnAssignedPersonalGoalListener(OnAssignedPersonalGoalListener onAssignedPersonalGoalListener) {
        onAssignedPersonalGoalListeners.add(onAssignedPersonalGoalListener);
    }

    public void removeOnAssignedPersonalGoalListener(OnAssignedPersonalGoalListener onAssignedPersonalGoalListener) {
        onAssignedPersonalGoalListeners.remove(onAssignedPersonalGoalListener);
    }

    public void notifyOnAssignedPersonalGoal() {
        for(OnAssignedPersonalGoalListener onAssignedPersonalGoalListener : onAssignedPersonalGoalListeners) {
            onAssignedPersonalGoalListener.onAssignedPersonalGoal(this.nickName, this.personalGoal.getGoalPattern(), this.personalGoal.getScoreMap());
        }
    }

    public void notifyOnPlayerStateChanged() {
        for(OnPlayerStateChangedListener onPlayerStateChangedListener : onPlayerStateChangedListeners) {
            onPlayerStateChangedListener.onPlayerStateChanged(this.nickName, this.playerState);
        }
    }

    public void notifyOnUpdateNeeded() {
        for(OnUpdateNeededListener onUpdateNeededListener : onUpdateNeededListeners) {
            onUpdateNeededListener.onUpdateNeededListener(this);
        }
    }

    @Override
    public void onUpdateNeededListener(Player player) {
        onPlayerStateChangedListeners.stream().filter(v -> player.getVirtualView() == v).findAny().ifPresentOrElse(v->v.onPlayerStateChanged(this.nickName,this.playerState),()->System.err.println("unable to update about player state changed"));
        if(this.personalGoal != null) {
            onAssignedPersonalGoalListeners.stream().filter(v -> player.getVirtualView() == v).findAny().ifPresentOrElse(v->v.onAssignedPersonalGoal(this.nickName,this.personalGoal.getGoalPattern(), this.personalGoal.getScoreMap()),()->System.err.println("unable to notify about assigned personal goal"));
        }
    }

    public void setOnUpdateNeededListener(OnUpdateNeededListener onUpdateNeededListener) {
        onUpdateNeededListeners.add(onUpdateNeededListener);
    }

    public void removeOnUpdateNeededListener(OnUpdateNeededListener onUpdateNeededListener) {
        onUpdateNeededListeners.remove(onUpdateNeededListener);
    }

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

    @Override
    public int hashCode() {
        return Objects.hash(nickName, personalGoal, bookShelf, pointPlayer, playerState, virtualView, onPlayerStateChangedListeners, onAssignedPersonalGoalListeners);
    }
}