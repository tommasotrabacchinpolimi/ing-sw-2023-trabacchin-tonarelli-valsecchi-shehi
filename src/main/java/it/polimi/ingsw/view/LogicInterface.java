package it.polimi.ingsw.view;

import it.polimi.ingsw.model.TileSubject;
import it.polimi.ingsw.model.TileType;
import it.polimi.ingsw.utils.Coordinate;

import java.util.List;

public interface LogicInterface {
    void joinGame(String nickname);
    void createGame(String nickname, int numberOfPlayer);
    void quitGame();
    void sentMessage(String text,  String[] receiversNickname);
    void dragTilesToBookShelf(List<Coordinate> chosenTiles, int chosenColumn);
    String getThisPlayer();
    String getCurrentPlayer();
    List<String> getPlayersNames();
    List<Integer> getPlayerPoint(String nickname);
    TileSubject[][] getBoard();
    TileSubject[][] getBookShelfByNickname(String nickname);
    TileType[][] getPersonalGoal();
    String getCommonGoal1();
    String getCommonGoal2();
    Integer getAvailableScoreGoal1();
    Integer getAvailableScoreGoal2();
    String getPlayerState(String nickname);
    String getGameState();
}
