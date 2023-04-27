package it.polimi.ingsw.controller.listeners;

import it.polimi.ingsw.utils.Coordinate;

import java.util.List;

public interface OnAdjacentTilesUpdatedListener {
    void onAdjacentTilesUpdated(String nickname, List<Coordinate> tiles);
}
