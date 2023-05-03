package it.polimi.ingsw.view;

import it.polimi.ingsw.model.TileSubject;
import it.polimi.ingsw.model.TileType;
import it.polimi.ingsw.utils.Coordinate;
import it.polimi.ingsw.utils.Triple;

import java.util.List;

public interface LogicInterface {
    void joinGame(String nickname);
    void createGame(String nickname, int numberOfPlayer);
    void quitGame();
    void sentMessage(String text,  String[] receiversNickname);
    void dragTilesToBookShelf(List<Coordinate> chosenTiles, int chosenColumn);
}
