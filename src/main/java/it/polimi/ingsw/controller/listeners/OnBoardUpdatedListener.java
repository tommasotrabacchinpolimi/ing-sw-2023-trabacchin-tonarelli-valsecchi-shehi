package it.polimi.ingsw.controller.listeners;

import it.polimi.ingsw.model.TileSubject;

/**
 * A listener interface for notifying when the game board is updated.
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public interface OnBoardUpdatedListener {
    /**
     * Called when the game board is updated.
     * @param tileSubjects The updated matrix of {@linkplain  TileSubject tile subjects} representing the game board.
     */
    void onBoardUpdated(TileSubject[][] tileSubjects);
}
