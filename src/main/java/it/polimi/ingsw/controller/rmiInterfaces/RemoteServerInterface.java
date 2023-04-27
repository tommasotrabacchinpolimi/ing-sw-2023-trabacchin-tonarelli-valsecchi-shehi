package it.polimi.ingsw.controller.rmiInterfaces;

import java.rmi.RemoteException;
import java.util.List;

import it.polimi.ingsw.net.RemoteInterface;
import it.polimi.ingsw.utils.Coordinate;

public interface RemoteServerInterface extends RemoteInterface {
    public void createGame(java.lang.String p0, int p1) throws RemoteException;

    public void joinGame(java.lang.String p0) throws RemoteException;

    public void dragTilesToBookShelf(List<Coordinate> p0, int p1) throws RemoteException;

    public void quitGame() throws RemoteException;

    public void sentMessage(java.lang.String p0, java.lang.String[] p1) throws RemoteException;
    @Override
    public void nop() throws RemoteException;
}
