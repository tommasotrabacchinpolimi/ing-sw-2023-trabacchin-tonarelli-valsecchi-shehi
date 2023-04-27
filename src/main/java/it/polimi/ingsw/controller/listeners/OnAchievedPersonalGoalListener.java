package it.polimi.ingsw.controller.listeners;

import it.polimi.ingsw.utils.Coordinate;

import java.util.List;

public interface OnAchievedPersonalGoalListener {
    void onAchievedPersonalGoal(String nickname, List<Coordinate> tiles);
}
