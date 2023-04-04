package it.polimi.ingsw.controller;

import it.polimi.ingsw.net.RemoteInterface;
import it.polimi.ingsw.net.User;

public class GameManagerEnd<R extends RemoteInterface> extends GameManager<R> {


    @Override
    public void dragTilesToBookShelf(User<R> user, int[] chosenTiles, int chosenColumn) {
        return;
    }

    @Override
    public void registerPlayer(User<R> user, String nickname) {
        return;
    }

    @Override
    public void setPlayersNumber(User<R> user, int playersNumber) {
        return;
    }
}
