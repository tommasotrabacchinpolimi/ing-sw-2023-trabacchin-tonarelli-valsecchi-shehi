package it.polimi.ingsw.controller;

import it.polimi.ingsw.utils.Coordinate;

public interface ControllerInterface {
    void dragTilesToBookShelf(ClientInterface view, Coordinate[] chosenTiles, int chosenColumn);
    void registerPlayer(ClientInterface view, String nickname);
    void quitGame(ClientInterface view);
    void sentMessage(ClientInterface view, String text,  String[] receiversNickname);
}
