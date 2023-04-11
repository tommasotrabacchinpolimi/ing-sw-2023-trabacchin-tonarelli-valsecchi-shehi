package it.polimi.ingsw.controller.listeners;

import it.polimi.ingsw.model.EntryPatternGoal;

import java.util.List;
import java.util.Map;

public interface OnAssignedPersonalGoalListener {
    void onAssignedPersonalGoal(String nickname, List<EntryPatternGoal> goalPattern, Map<Integer, Integer> scoreMap);
}
