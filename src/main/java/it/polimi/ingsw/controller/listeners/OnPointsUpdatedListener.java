package it.polimi.ingsw.controller.listeners;

/**
 * A listener interface for notifying when the points of a player have been updated.
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public interface OnPointsUpdatedListener {
    /**
     * Called when the points of a player have been updated.
     * @param nickName The nickname of the player.
     * @param scoreAdjacentGoal The score earned from groups of adjacent tiles of the same type in the bookshelf.
     * @param scoreCommonGoal1 The score earned from the first common goal.
     * @param scoreCommonGoal2 The score earned from the second common goal.
     * @param scoreEndGame The score earned if the player is the first one to fill his bookshelf completely.
     * @param scorePersonalGoal The score earned from the personal goal.
     */
    void onPointsUpdated(String nickName, int scoreAdjacentGoal, int scoreCommonGoal1, int scoreCommonGoal2, int scoreEndGame, int scorePersonalGoal);
}
