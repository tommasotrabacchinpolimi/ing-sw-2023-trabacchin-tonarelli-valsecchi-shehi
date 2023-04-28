package it.polimi.ingsw.controller;

import it.polimi.ingsw.net.RemoteInterface;
import it.polimi.ingsw.utils.Coordinate;

import java.util.List;

public interface ServerInterface extends RemoteInterface {
    void dragTilesToBookShelf(List<Coordinate> chosenTiles, int chosenColumn);
    void joinGame(String nickname);
    void createGame(String nickname, int numberOfPlayer);
    void quitGame();
    void sentMessage(String text,  String[] receiversNickname);
}
