package it.polimi.ingsw.controller.listeners;

import it.polimi.ingsw.model.TileSubject;

public interface OnBookShelfUpdatedListener {
    void onBookShelfUpdated(String nickname, TileSubject[][] bookShelf);
}
