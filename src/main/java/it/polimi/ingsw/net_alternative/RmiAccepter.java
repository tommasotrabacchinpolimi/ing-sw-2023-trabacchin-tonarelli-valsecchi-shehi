package it.polimi.ingsw.net_alternative;

import it.polimi.ingsw.controller.LobbyController;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * The {@code RmiAccepter} class is responsible for accepting RMI client connections and registering them with the server.
 * It implements the {@link RmiAccepterInterface} and extends {@link UnicastRemoteObject} to enable remote method invocation.
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public class RmiAccepter extends UnicastRemoteObject implements RmiAccepterInterface {

    private final OnServerConnectionLostListener onServerConnectionLostListener;
    private final ServerDispatcher serverDispatcher;

    /**
     * Constructs a {@code RmiAccepter} object with the specified parameters.
     *
     * @param onServerConnectionLostListener the listener to notify when a server connection is lost.
     * @param serverDispatcher               the server dispatcher to handle incoming RMI client requests.
     * @throws RemoteException if a remote exception occurs during object export.
     */
    public RmiAccepter(OnServerConnectionLostListener onServerConnectionLostListener, ServerDispatcher serverDispatcher) throws RemoteException {
        super();
        this.onServerConnectionLostListener = onServerConnectionLostListener;
        this.serverDispatcher = serverDispatcher;
    }

    /**
     * Registers the RMI client with the server by creating the necessary adapter and server instances.
     * Additionally, a server heartbeater thread is started to monitor the client's connection status.
     *
     * @param clientSocket the RMI client interface object to register.
     * @return the RMI server interface object to be used by the client.
     * @throws RemoteException if a remote exception occurs during RMI operation.
     */
    public RmiServerInterface registerClient(RmiClientInterface clientSocket) throws RemoteException {
        ServerRmiAdapter serverRmiAdapter = new ServerRmiAdapter(clientSocket, onServerConnectionLostListener);
        RmiServerImpl rmiServer = new RmiServerImpl(serverDispatcher, serverRmiAdapter);
        ServerHeartBeater serverHeartBeater = new ServerHeartBeater(serverRmiAdapter, 1000, onServerConnectionLostListener);
        new Thread(serverHeartBeater).start();
        return rmiServer;
    }
}
