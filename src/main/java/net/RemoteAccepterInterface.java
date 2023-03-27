package net;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteAccepterInterface<L,R> extends Remote {
    L register(R remoteObject) throws RemoteException;
}
