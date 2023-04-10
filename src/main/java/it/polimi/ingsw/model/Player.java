package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.ClientInterface;
import it.polimi.ingsw.controller.JSONExclusionStrategy.ExcludedFromJSON;
import it.polimi.ingsw.controller.listeners.OnPlayerStateChangedListener;
import it.polimi.ingsw.controller.listeners.OnStateChangedListener;
import it.polimi.ingsw.controller.listeners.localListeners.OnUpdateNeededListener;
import it.polimi.ingsw.net.RemoteInterface;
import it.polimi.ingsw.net.TestClientInterface;

import java.io.Serial;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Player is a class that represent a player of the game. Players are identified by a {@link Player#nickName}, which is unique within the game.
 * Each player is associated with a {@link PersonalGoal}, that the player must satisfy in order to earn more points. The score earned by the player
 * is saved in the {@link Player#pointPlayer} attribute that always has a null or positive value.
 *
 * @author Melanie Tonarelli
 * @version 1.0, 26/03/23
 * @see PersonalGoal
 */
public class Player<R extends ClientInterface> implements Serializable, OnUpdateNeededListener<R> {
    @Serial
    @ExcludedFromJSON
    private static final long serialVersionUID = 97354642643274L;

    private final String nickName;

    @ExcludedFromJSON
    private final PersonalGoal personalGoal;

    @ExcludedFromJSON
    private BookShelf<R> bookShelf;

    @ExcludedFromJSON
    private PointPlayer pointPlayer;

    private PlayerState playerState;

    private R virtualView;

    private List<OnPlayerStateChangedListener> onPlayerStateChangedListeners;

    public Player(String nickName, R virtualView) {
        this.nickName = nickName;
        this.personalGoal = null;
        this.bookShelf = new BookShelf<R>();
        this.pointPlayer = new PointPlayer();
        this.playerState = PlayerState.CONNECTED;
        this.virtualView = virtualView;
        onPlayerStateChangedListeners = new LinkedList<>();
    }
    public Player(String nickName) {
        this.nickName = nickName;
        this.personalGoal = null;
        this.bookShelf = new BookShelf<R>();
        this.pointPlayer = new PointPlayer();
        this.playerState = PlayerState.CONNECTED;
        onPlayerStateChangedListeners = new LinkedList<>();
    }

    /**
     * Constructor hat sets the fields of the class to the parameter passed.
     * @param nickName A {@link String} representing the nickname of the player.
     * @param personalGoal The {@link PersonalGoal} that will be associated to the player.
     *
     * @see PersonalGoal
     */
    public Player( String  nickName, PersonalGoal personalGoal ){
        this.nickName = nickName;
        this.personalGoal = personalGoal;
        this.bookShelf = new BookShelf<R>();
        this.pointPlayer = new PointPlayer();
        this.playerState = PlayerState.CONNECTED;
        onPlayerStateChangedListeners = new LinkedList<>();
    }

    public PlayerState getPlayerState() {
        return playerState;
    }

    public void setPlayerState(PlayerState playerState) {
        this.playerState = playerState;
        notifyOnPlayerStateChanged();
    }

    public R getVirtualView() {
        return virtualView;
    }

    public void setVirtualView(R virtualView) {
        this.virtualView = virtualView;
    }

    public BookShelf<R> getBookShelf() {
        return bookShelf;
    }

    public void setBookShelf(BookShelf<R> bookShelf) {
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
    public void notifyOnPlayerStateChanged() {
        for(OnPlayerStateChangedListener onPlayerStateChangedListener : onPlayerStateChangedListeners) {
            onPlayerStateChangedListener.onPlayerStateChanged(this.nickName, this.playerState);
        }
    }

    @Override
    public void onUpdateNeededListener(Player<R> player) {
        onPlayerStateChangedListeners.stream().filter(v-> player.getVirtualView() == v).findAny().ifPresentOrElse(v->v.onPlayerStateChanged(this.nickName,this.playerState),()->System.err.println("unable to update about player state changed"));
    }
}