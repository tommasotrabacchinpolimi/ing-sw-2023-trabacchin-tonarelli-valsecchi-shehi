package it.polimi.ingsw.controller;

public interface ControllerInterface {
    void dragTilesToBookShelf(ClientInterface view, int[] chosenTiles, int chosenColumn);
    void registerPlayer(ClientInterface view, String nickname);
    void quitGame(ClientInterface view);
}
