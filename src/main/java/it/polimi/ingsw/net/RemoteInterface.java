package it.polimi.ingsw.net;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteInterface extends Remote {
    void nop() throws RemoteException;
}
