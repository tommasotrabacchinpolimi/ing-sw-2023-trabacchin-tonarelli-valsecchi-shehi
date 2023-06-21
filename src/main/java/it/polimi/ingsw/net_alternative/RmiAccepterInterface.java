package it.polimi.ingsw.net_alternative;

import it.polimi.ingsw.net.RemoteInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RmiAccepterInterface extends Remote {
    public RmiServerInterface registerClient(RmiClientInterface client) throws RemoteException;
}
