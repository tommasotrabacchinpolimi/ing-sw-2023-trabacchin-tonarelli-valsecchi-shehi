package it.polimi.ingsw.controller.listeners;

import it.polimi.ingsw.utils.Coordinate;

import java.util.List;

/**
 * A listener interface for notifying when a personal goal is achieved.
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public interface OnAchievedPersonalGoalListener {
    /**
     * Called when a personal goal is achieved by a player.
     * @param nickname the nickname of the player who achieved the personal goal.
     * @param tiles the list of {@linkplain Coordinate coordinates} representing the tiles involved in the personal goal.
     */
    void onAchievedPersonalGoal(String nickname, List<Coordinate> tiles);
}