package it.polimi.ingsw.controller.listeners;

import it.polimi.ingsw.model.TileSubject;

/**
 * A listener interface for notifying when a player's bookshelf is updated.
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public interface OnBookShelfUpdatedListener {
    /**
     * Called when a player's bookshelf is updated.
     * @param nickname The nickname of the player whose bookshelf is updated.
     * @param bookShelf The updated matrix of {@linkplain TileSubject tile subjects} representing the bookshelf.
     */
    void onBookShelfUpdated(String nickname, TileSubject[][] bookShelf);
}
