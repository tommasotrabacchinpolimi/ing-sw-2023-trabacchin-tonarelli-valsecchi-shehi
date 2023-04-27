package it.polimi.ingsw.controller;

import it.polimi.ingsw.net.RemoteInterface;
import it.polimi.ingsw.utils.Coordinate;

public interface ServerInterface extends RemoteInterface {
    void dragTilesToBookShelf(Coordinate[] chosenTiles, int chosenColumn);
    void joinGame(String nickname);
    void createGame(String nickname, int numberOfPlayer);
    void quitGame();
    void sentMessage(String text,  String[] receiversNickname);
}
