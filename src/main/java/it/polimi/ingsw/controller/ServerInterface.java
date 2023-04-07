package it.polimi.ingsw.controller;

import it.polimi.ingsw.net.RemoteInterface;

public interface ServerInterface extends RemoteInterface {
    void dragTilesToBookShelf(int[] chosenTiles, int chosenColumn);
    void joinGame(String nickname);
    void createGame(String nickname, int numberOfPlayer);
    void sentMessage(String text,  String[] receiversNickname);
}
