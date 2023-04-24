package it.polimi.ingsw.net;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteAccepterInterface extends Remote {
    RemoteInterface register(RemoteInterface remoteObject) throws RemoteException, IOException, ClassNotFoundException;
}
