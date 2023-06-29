package it.polimi.ingsw.net;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The RmiAccepterInterface defines the methods for accepting client connections via RMI.
 * It allows clients to register themselves and obtain a reference to the RMI server interface.
 */
public interface RmiAccepterInterface extends Remote {

    /**
     * Registers a client and returns a reference to the RMI server interface.
     *
     * @param client the client interface to register
     * @return the reference to the RMI server interface
     * @throws RemoteException if a remote error occurs during the registration process
     */
    public RmiServerInterface registerClient(RmiClientInterface client) throws RemoteException;
}
