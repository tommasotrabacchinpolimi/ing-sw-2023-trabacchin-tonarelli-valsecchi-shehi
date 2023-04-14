package it.polimi.ingsw.controller.listeners;

import it.polimi.ingsw.model.EntryPatternGoal;

import java.util.List;

public interface OnAchievedPersonalGoalListener {
    void onAchievedPersonalGoal(String nickname, List<EntryPatternGoal> tiles);
}
