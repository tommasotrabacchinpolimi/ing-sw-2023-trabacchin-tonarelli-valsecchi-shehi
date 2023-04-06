package it.polimi.ingsw.controller.listeners;

public interface OnPointsUpdatedListener {
    void onPointsUpdated(String nickName, int scoreAdjacentGoal, int scoreCommonGoal1, int scoreCommonGoal2, int scoreEndGame, int scorePersonalGoal);
}
