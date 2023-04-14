package it.polimi.ingsw.controller.listeners;

import it.polimi.ingsw.model.EntryPatternGoal;

import java.util.List;

public interface OnAdjacentTilesUpdatedListener {
    void onAdjacentTilesUpdated(String nickname, List<EntryPatternGoal> tiles);
}
