package it.polimi.ingsw.controller;

import java.io.FileNotFoundException;

public interface LobbyControllerInterface {
    void joinGame(ClientInterface view, String nickname);
    void createGame(ClientInterface view, String nickname, int numberOfPlayer) throws FileNotFoundException;
}
