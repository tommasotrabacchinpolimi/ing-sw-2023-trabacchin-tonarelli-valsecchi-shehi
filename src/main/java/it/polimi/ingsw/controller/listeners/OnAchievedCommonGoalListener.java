package it.polimi.ingsw.controller.listeners;

import it.polimi.ingsw.model.EntryPatternGoal;

import java.util.List;

public interface OnAchievedCommonGoalListener {
    public void onAchievedCommonGoal(String nicknamePlayer, List<EntryPatternGoal> tiles, int numberCommonGoal);
}
