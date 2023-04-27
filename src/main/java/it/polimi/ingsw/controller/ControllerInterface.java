package it.polimi.ingsw.controller;

import it.polimi.ingsw.utils.Coordinate;

import java.util.List;

public interface ControllerInterface {
    void dragTilesToBookShelf(ClientInterface view, List<Coordinate> chosenTiles, int chosenColumn);
    void registerPlayer(ClientInterface view, String nickname);
    void quitGame(ClientInterface view);
    void sentMessage(ClientInterface view, String text,  String[] receiversNickname);
}
