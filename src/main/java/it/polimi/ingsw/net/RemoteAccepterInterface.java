package it.polimi.ingsw.net;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteAccepterInterface<L extends RemoteInterface,R extends RemoteInterface> extends Remote {
    L register(R remoteObject) throws RemoteException;
}
