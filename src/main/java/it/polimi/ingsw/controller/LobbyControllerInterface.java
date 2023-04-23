package it.polimi.ingsw.controller;

import java.io.FileNotFoundException;
import java.rmi.RemoteException;

public interface LobbyControllerInterface {
    void joinGame(ClientInterface view, String nickname);
    void createGame(ClientInterface view, String nickname, int numberOfPlayer) throws FileNotFoundException;

    void nop(ClientInterface view) throws RemoteException;
}
