package it.polimi.ingsw;

import it.polimi.ingsw.controller.ControllerDispatcher;
import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.net.RmiAccepter;
import it.polimi.ingsw.net.ServerDispatcher;
import it.polimi.ingsw.net.SocketAccepter;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * The main class for the server application.
 */
public class AppServer {

    /**
     * The entry point of the server application.
     *
     * @param args The command-line arguments.
     * @throws RemoteException        if a remote error occurs.
     * @throws AlreadyBoundException  if the RMI object is already bound.
     */
    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
        System.setProperty("java.rmi.server.hostname", "192.168.142.15");
        System.setProperty("sun.rmi.transport.connectionTimeout", "10000");
        LobbyController lobbyController = new LobbyController(1000*60);
        ControllerDispatcher controllerDispatcher = new ControllerDispatcher(lobbyController);
        ServerDispatcher serverDispatcher = new ServerDispatcher(controllerDispatcher);
        SocketAccepter socketAccepter = new SocketAccepter(serverDispatcher, 1234, lobbyController);
        lobbyController.setDispatcher(controllerDispatcher);
        new Thread(socketAccepter).start();
        RmiAccepter rmiAccepter = new RmiAccepter(lobbyController, serverDispatcher);
        Registry registry = LocateRegistry.createRegistry(4321);
        registry.bind("default", rmiAccepter);
    }


}
