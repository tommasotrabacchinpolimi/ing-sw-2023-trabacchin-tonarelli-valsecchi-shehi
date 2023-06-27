package it.polimi.ingsw.controller.listeners;

import it.polimi.ingsw.model.EntryPatternGoal;

import java.util.List;
import java.util.Map;


/**
 * A listener interface for notifying when a personal goal is assigned to a player.
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public interface OnAssignedPersonalGoalListener {
    /**
     * Called when a personal goal is assigned.
     * @param nickname the nickname of the player to whom the personal goal is assigned.
     * @param goalPattern the list of {@linkplain EntryPatternGoal entry patterns} representing the assigned personal goal.
     * @param scoreMap the map containing the points associated with the number of tiles in the right position
     */
    void onAssignedPersonalGoal(String nickname, List<EntryPatternGoal> goalPattern, Map<Integer, Integer> scoreMap);
}
