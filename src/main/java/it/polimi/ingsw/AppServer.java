package it.polimi.ingsw;

import it.polimi.ingsw.controller.ControllerDispatcher;
import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.net_alternative.RmiAccepter;
import it.polimi.ingsw.net_alternative.ServerDispatcher;
import it.polimi.ingsw.net_alternative.SocketAccepter;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class AppServer {

    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
        System.setProperty("java.rmi.server.hostname", "192.168.1.106");
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
