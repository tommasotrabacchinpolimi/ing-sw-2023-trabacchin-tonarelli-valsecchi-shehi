package it.polimi.ingsw.controller.listeners;

import it.polimi.ingsw.model.EntryPatternGoal;
import it.polimi.ingsw.utils.Coordinate;

import java.util.List;

/**
 * A listener interface for notifying when a common goal is achieved.
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public interface OnAchievedCommonGoalListener {
    /**
     * Called when a common goal is achieved by a player.
     * @param nicknamePlayer the nickname of the player who achieved the common goal.
     * @param tiles the list of {@linkplain Coordinate coordinates} representing the tiles involved in the common goal.
     * @param numberCommonGoal the number of the common goal achieved
     *                         (in this case, {@code 1} for the first common goal and {@code 2} for the second common goal).
     */
    public void onAchievedCommonGoal(String nicknamePlayer, List<Coordinate> tiles, int numberCommonGoal);
}
