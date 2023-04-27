package it.polimi.ingsw.controller.listeners;

import it.polimi.ingsw.model.EntryPatternGoal;
import it.polimi.ingsw.utils.Coordinate;

import java.util.List;

public interface OnAchievedCommonGoalListener {
    public void onAchievedCommonGoal(String nicknamePlayer, List<Coordinate> tiles, int numberCommonGoal);
}
