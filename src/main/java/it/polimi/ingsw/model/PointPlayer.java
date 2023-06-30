package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.listeners.OnPointsUpdatedListener;
import it.polimi.ingsw.controller.listeners.localListeners.OnUpdateNeededListener;

import java.io.Serial;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Class that represents the points achieved by a player.
 * @see Player
 * @see OnPointsUpdatedListener
 * <p>
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 * </p>
 * @version 2.0
 * @since 10/04/2023
 */

public class PointPlayer implements Serializable, OnUpdateNeededListener {
    @Serial
    private static final long serialVersionUID = 973546426438574L;


    /**
     * Number of points assigned for the {@linkplain State#getCommonGoal1() first common goal}.
     */
    private int scoreCommonGoal1;


    /**
     * Number of points assigned for the {@linkplain State#getCommonGoal2() second common goal}
     */
    private int scoreCommonGoal2;


    /**
     * Number of points assigned to the first player that fills his bookshelf
     */
    private int scoreEndGame;


    /**
     * Number of points assigned for the personal goal
     * @see PersonalGoal
     */
    private int scorePersonalGoal;


    /**
     * Number of points assigned for the groups of adjacent tiles of the same type in the player's bookshelf
     */
    private int scoreAdjacentGoal;


    /**
     * Listener to the point updated
     * @see OnPointsUpdatedListener
     */
    private final List<OnPointsUpdatedListener> onPointsUpdatedListeners;


    /**
     * Player associated with this points
     * @see Player
     */
    private Player player;


    /**
     * Constructs a new PointPlayer object with initial scores set to zero.
     */
    public PointPlayer(){
        scoreAdjacentGoal = 0;
        scoreCommonGoal1 = 0;
        scoreCommonGoal2 = 0;
        scoreEndGame = 0;
        scorePersonalGoal = 0;
        onPointsUpdatedListeners = new LinkedList<>();
    }


    /**
     * Returns the score for common goal 1
     * @return the score for common goal 1
     * @see State#getCommonGoal1()
     * @see #scoreCommonGoal1
     */
    public int getScoreCommonGoal1() {
        return scoreCommonGoal1;
    }


    /**
     * Sets the score for common goal 1 and notifies the listeners of the point update.
     * @param scoreCommonGoal1 the score for common goal 1 to set
     * @see State#getCommonGoal1()
     * @see #scoreCommonGoal1
     */
    public void setScoreCommonGoal1(int scoreCommonGoal1) {
        this.scoreCommonGoal1 = scoreCommonGoal1;
        notifyOnPointUpdated();
    }


    /**
     * Returns the score for common goal 2.
     * @return the score for common goal 2
     * @see State#getCommonGoal2()
     * @see #scoreCommonGoal2
     */
    public int getScoreCommonGoal2() {
        return scoreCommonGoal2;
    }


    /**
     * Sets the score for common goal 2 and notifies the listeners of the point update.
     * @param scoreCommonGoal2 the score for common goal 2 to set
     * @see State#getCommonGoal2()
     * @see #scoreCommonGoal2
     */
    public void setScoreCommonGoal2(int scoreCommonGoal2) {
        this.scoreCommonGoal2 = scoreCommonGoal2;
        notifyOnPointUpdated();
    }


    /**
     * Returns the score for end game.
     * @return the score for end game
     * @see #scoreEndGame
     */
    public int getScoreEndGame() {
        return scoreEndGame;
    }


    /**
     * Sets the score for end game and notifies the listeners of the point update.
     * @param scoreEndGame the score for end game to set
     * @see #scoreEndGame
     */
    public void setScoreEndGame(int scoreEndGame) {
        this.scoreEndGame = scoreEndGame;
        notifyOnPointUpdated();
    }


    /**
     * Returns the score for personal goal.
     * @return the score for personal goal
     * @see #scorePersonalGoal
     */
    public int getScorePersonalGoal() {
        return scorePersonalGoal;
    }


    /**
     * Sets the score for personal goal and notifies the listeners of the point update.
     * @param scorePersonalGoal the score for personal goal to set
     * @see #scorePersonalGoal
     */
    public void setScorePersonalGoal(int scorePersonalGoal) {
        this.scorePersonalGoal = scorePersonalGoal;
        notifyOnPointUpdated();
    }


    /**
     * Returns the score for adjacent goal.
     * @return the score for adjacent goal
     * @see #scoreAdjacentGoal
     */
    public int getScoreAdjacentGoal() {
        return scoreAdjacentGoal;
    }

    /**
     * Sets the score for adjacent goal and notifies the listeners of the point update.
     * @param scoreAdjacentGoal the score for adjacent goal to set
     * @see #scoreAdjacentGoal
     */
    public void setScoreAdjacentGoal(int scoreAdjacentGoal) {
        this.scoreAdjacentGoal = scoreAdjacentGoal;
        notifyOnPointUpdated();
    }


    /**
     * Method that return the total points granted to the player.
     * @return the total point of the player.
     */
    public Integer getTotalScore(){
        return scoreAdjacentGoal + scoreEndGame + scorePersonalGoal + scoreCommonGoal1 + scoreCommonGoal2;
    }


    /**
     * Returns the player associated with this PointPlayer.
     * @return the player associated with this PointPlayer
     * @see #player
     * @see Player
     */
    public Player getPlayer() {
        return player;
    }


    /**
     * Sets the player associated with this PointPlayer.
     * @param player the player to set
     * @see Player
     * @see #player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }


    /**
     * Notifies the listeners of the point update by invoking the onPointsUpdated method on each listener.
     * The updated point values, along with the player's nickname, are passed as parameters to the listeners.
     * @see #onPointsUpdatedListeners
     * @see OnPointsUpdatedListener
     */
    public void notifyOnPointUpdated() {
        for(OnPointsUpdatedListener onPointsUpdatedListener : onPointsUpdatedListeners) {
            onPointsUpdatedListener.onPointsUpdated(player.getNickName(), scoreAdjacentGoal, scoreCommonGoal1, scoreCommonGoal2, scoreEndGame, scorePersonalGoal);
        }
    }


    /**
     * Sets the listener for points updated event.
     * @param onPointsUpdatedListener the listener to set
     * @see OnPointsUpdatedListener
     */
    public void setOnPointsUpdatedListener(OnPointsUpdatedListener onPointsUpdatedListener) {
        onPointsUpdatedListeners.add(onPointsUpdatedListener);
    }

    /**
     * Removes the specified listener for points updated event.
     * @param onPointsUpdatedListener the listener to remove
     * @see OnPointsUpdatedListener
     */
    public void removeOnPointsUpdatedListener(OnPointsUpdatedListener onPointsUpdatedListener) {
        onPointsUpdatedListeners.remove(onPointsUpdatedListener);
    }

    /**
     * Overriding equals() default method.
     * This method checks if the {@code object} passed as parameter is equals to the instance that is calling the method
     * @param o the reference object with which to compare
     * @return {@code true} if and only if this object is the same as the object argument; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        PointPlayer that = (PointPlayer) o;

        return scoreCommonGoal1 == that.scoreCommonGoal1 &&
                scoreCommonGoal2 == that.scoreCommonGoal2 &&
                scoreEndGame == that.scoreEndGame &&
                scorePersonalGoal == that.scorePersonalGoal &&
                scoreAdjacentGoal == that.scoreAdjacentGoal &&
                Objects.equals(onPointsUpdatedListeners, that.onPointsUpdatedListeners) && Objects.equals(player, that.player);
    }

    /**
     * Overriding hashCode() default method.
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(scoreCommonGoal1, scoreCommonGoal2, scoreEndGame, scorePersonalGoal, scoreAdjacentGoal, onPointsUpdatedListeners, player);
    }

    /**
     *
     * @param player the player that needs an update.
     */
    @Override
    public void onUpdateNeededListener(Player player) {
        onPointsUpdatedListeners.stream()
                .forEach(v->v.onPointsUpdated(this.player.getNickName(), scoreAdjacentGoal, scoreCommonGoal1, scoreCommonGoal2, scoreEndGame, scorePersonalGoal)/*,()->System.err.println("unable to notify about points updated")*/);
    }
}
