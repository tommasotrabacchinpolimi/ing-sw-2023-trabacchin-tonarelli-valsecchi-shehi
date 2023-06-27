package it.polimi.ingsw.controller.listeners;

/**
 * A listener interface for notifying when the available score for common goals changes.
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public interface OnChangedCommonGoalAvailableScoreListener {
    /**
     * Called when the available score for common goals changes.
     * @param score The updated score value.
     * @param numberOfCommonGoal The number of the common goal associated with the updated available score.
     */
    void onChangedCommonGoalAvailableScore(int score, int numberOfCommonGoal);
}
