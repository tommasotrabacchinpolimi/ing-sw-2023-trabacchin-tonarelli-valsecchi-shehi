package net;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.RemoteObject;

public interface RemoteInterface extends Remote {
    void nop() throws RemoteException;
}
