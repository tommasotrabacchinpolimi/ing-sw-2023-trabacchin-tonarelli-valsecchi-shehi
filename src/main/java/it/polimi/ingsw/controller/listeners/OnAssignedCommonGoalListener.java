package it.polimi.ingsw.controller.listeners;

/**
 * A listener interface for notifying when a common goal is assigned at the start of the game.
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public interface OnAssignedCommonGoalListener {
    /**
     * Called when a common goal is assigned.
     * @param description the description of the assigned common goal.
     * @param n the number associated with the assigned common goal.
     */
    void onAssignedCommonGoal(String description, int n);
}
