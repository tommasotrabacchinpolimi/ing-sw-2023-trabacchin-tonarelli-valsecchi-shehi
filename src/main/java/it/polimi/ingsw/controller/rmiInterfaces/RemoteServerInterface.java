package it.polimi.ingsw.controller.rmiInterfaces;
import java.rmi.RemoteException;

import it.polimi.ingsw.net.RemoteInterface;
public interface RemoteServerInterface extends RemoteInterface {
public void createGame(java.lang.String p0, int p1) throws RemoteException ;
public void joinGame(java.lang.String p0) throws RemoteException ;
public void dragTilesToBookShelf(int[] p0, int p1) throws RemoteException ;
public void quitGame() throws RemoteException ;
public void sentMessage(java.lang.String p0, java.lang.String[] p1) throws RemoteException ;
public void nop() throws RemoteException ;
}
