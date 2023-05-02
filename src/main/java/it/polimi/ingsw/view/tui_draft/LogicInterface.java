package it.polimi.ingsw.view.tui_draft;

import it.polimi.ingsw.utils.Coordinate;

import java.util.List;

public interface LogicInterface {
    void joinGame(String nickname);
    void createGame(String nickname, int numberOfPlayer);
    void quitGame();
    void sentMessage(String text,  String[] receiversNickname);
    void dragTilesToBookShelf(List<Coordinate> chosenTiles, int chosenColumn);
}
