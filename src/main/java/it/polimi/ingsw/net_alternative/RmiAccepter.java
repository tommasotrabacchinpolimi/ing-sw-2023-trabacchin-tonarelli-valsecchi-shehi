package it.polimi.ingsw.net_alternative;

import it.polimi.ingsw.controller.LobbyController;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RmiAccepter extends UnicastRemoteObject implements RmiAccepterInterface{

    private OnServerConnectionLostListener onServerConnectionLostListener;

    private ServerDispatcher serverDispatcher;
    public RmiAccepter(OnServerConnectionLostListener onServerConnectionLostListener, ServerDispatcher serverDispatcher) throws RemoteException {
        super();
        this.onServerConnectionLostListener = onServerConnectionLostListener;
        this.serverDispatcher = serverDispatcher;
    }

    public RmiServerInterface registerClient(RmiClientInterface clientSocket) throws RemoteException{
        ServerRmiAdapter serverRmiAdapter = new ServerRmiAdapter(clientSocket, onServerConnectionLostListener);
        RmiServerImpl rmiServer = new RmiServerImpl(serverDispatcher, serverRmiAdapter);
        ServerHeartBeater serverHeartBeater = new ServerHeartBeater(serverRmiAdapter, 1000, onServerConnectionLostListener);
        new Thread(serverHeartBeater).start();
        return rmiServer;
    }
}
