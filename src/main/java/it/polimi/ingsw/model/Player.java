package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;
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
public class Player implements Serializable {
    private static final long serialVersionUID = 97354642643274L;
    private final String nickName;
    private final PersonalGoal personalGoal;
    private BookShelf bookShelf;
    private PointPlayer pointPlayer;

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
        pointPlayer = new PointPlayer();

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
}
