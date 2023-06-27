package it.polimi.ingsw.net_alternative;

import it.polimi.ingsw.controller.ClientInterface;
import it.polimi.ingsw.controller.ServerInterface;

import java.io.IOException;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * The ConnectionBuilder class is responsible for building connections between clients and servers using different protocols,
 * such as sockets and RMI (Remote Method Invocation).
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public class ConnectionBuilder {
    /**
     * Builds a socket connection with the server.
     *
     * @param port                        The port number to connect to.
     * @param host                        The host address of the server.
     * @param clientDispatcher            An instance of the ClientDispatcher class that handles message dispatching.
     * @param onClientConnectionLostListener  An instance of the OnClientConnectionLostListener interface to handle connection loss events.
     * @return The ServerInterface object representing the socket connection.
     * @throws IOException if an I/O error occurs during the socket connection.
     */
    static public ServerInterface buildSocketConnection(int port, String host,  ClientDispatcher clientDispatcher,OnClientConnectionLostListener onClientConnectionLostListener) throws IOException {
        Socket socket = new Socket(host, port);
        ServerSocketImpl serverSocket = new ServerSocketImpl(socket, clientDispatcher, onClientConnectionLostListener);
        ClientHeartBeater clientHeartBeater = new ClientHeartBeater(onClientConnectionLostListener, serverSocket, 1000);
        new Thread(serverSocket).start();
        new Thread(clientHeartBeater).start();
        return serverSocket;
    }

    /**
     * Builds an RMI connection with the server.
     *
     * @param port                        The port number to connect to.
     * @param host                        The host address of the server.
     * @param clientDispatcher            An instance of the ClientDispatcher class that handles message dispatching.
     * @param onClientConnectionLostListener  An instance of the OnClientConnectionLostListener interface to handle connection loss events.
     * @return The ServerInterface object representing the RMI connection.
     * @throws RemoteException    if a remote communication error occurs during the RMI connection.
     * @throws NotBoundException  if the RMI object is not bound in the registry.
     */

    static public ServerInterface buildRmiConnection(int port, String host, ClientDispatcher clientDispatcher, OnClientConnectionLostListener onClientConnectionLostListener) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(host,port);
        RmiAccepterInterface rmiAccepter = (RmiAccepterInterface) registry.lookup("default");
        RmiClientInterface rmiClient = new RmiClientImpl(clientDispatcher);
        ServerInterface serverInterface = new ClientRmiAdapter(rmiAccepter.registerClient(rmiClient), onClientConnectionLostListener);
        ClientHeartBeater clientHeartBeater = new ClientHeartBeater(onClientConnectionLostListener, serverInterface, 1000);
        new Thread(clientHeartBeater).start();
        return serverInterface;
    }
}
