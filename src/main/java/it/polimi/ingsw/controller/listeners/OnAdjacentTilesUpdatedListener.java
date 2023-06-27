package it.polimi.ingsw.controller.listeners;

import it.polimi.ingsw.utils.Coordinate;

import java.util.List;

/**
 * A listener interface for notifying when the adjacent tiles in the bookshelf are updated.
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public interface OnAdjacentTilesUpdatedListener {
    /**
     * Called when the adjacent tiles in the bookshelf are updated for a player.
     * @param nickname the nickname of the player for whom the adjacent tiles are updated.
     * @param tiles the list of {@linkplain Coordinate coordinates} representing the updated adjacent tiles.
     */
    void onAdjacentTilesUpdated(String nickname, List<Coordinate> tiles);
}
