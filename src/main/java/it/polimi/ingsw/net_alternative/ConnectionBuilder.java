package it.polimi.ingsw.net_alternative;

import it.polimi.ingsw.controller.ClientInterface;
import it.polimi.ingsw.controller.ServerInterface;

import java.io.IOException;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ConnectionBuilder {
    static public ServerInterface buildSocketConnection(int port, String host,  ClientDispatcher clientDispatcher,OnClientConnectionLostListener onClientConnectionLostListener) throws IOException {
        Socket socket = new Socket(host, port);
        ServerSocketImpl serverSocket = new ServerSocketImpl(socket, clientDispatcher, onClientConnectionLostListener);
        ClientHeartBeater clientHeartBeater = new ClientHeartBeater(onClientConnectionLostListener, serverSocket, 5000);
        new Thread(serverSocket).start();
        new Thread(clientHeartBeater).start();
        return serverSocket;
    }

    static public ServerInterface buildRmiConnection(int port, String host, ClientDispatcher clientDispatcher, OnClientConnectionLostListener onClientConnectionLostListener) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(host,port);
        RmiAccepterInterface rmiAccepter = (RmiAccepterInterface) registry.lookup("default");
        RmiClientInterface rmiClient = new RmiClientImpl(clientDispatcher);
        ServerInterface serverInterface = new ClientRmiAdapter(rmiAccepter.registerClient(rmiClient), onClientConnectionLostListener);
        ClientHeartBeater clientHeartBeater = new ClientHeartBeater(onClientConnectionLostListener, serverInterface, 5000);
        new Thread(clientHeartBeater).start();
        return serverInterface;
    }
}
