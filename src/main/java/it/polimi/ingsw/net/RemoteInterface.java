package it.polimi.ingsw.net;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteInterface extends Remote {
    public void nop() throws RemoteException;
}
