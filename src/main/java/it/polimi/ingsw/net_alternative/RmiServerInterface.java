package it.polimi.ingsw.net_alternative;

import it.polimi.ingsw.net_alternative.servermessages.*;
import it.polimi.ingsw.utils.Coordinate;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface RmiServerInterface extends Remote {
    public void dragTilesToBookShelf(List<Coordinate> chosenTiles, int chosenColumn) throws RemoteException;

    public void joinGame(String nickname) throws RemoteException;

    public void createGame(String nickname, int numberOfPlayer) throws RemoteException;

    public void quitGame() throws RemoteException;

    public void sentMessage(String text, String[] receiversNickname) throws RemoteException;

    public void nop() throws RemoteException;
}
