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
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 *
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
        if(args.length != 5) {
            System.out.println("Parameters missing!");
            System.exit(-1);
        }
        int rmiPort = 0;
        int socketPort = 0;
        int turnDuration = 0;
        int timeOutDuration = 0;
        try{
            rmiPort = Integer.parseInt(args[1]);
            socketPort = Integer.parseInt(args[2]);
            turnDuration = Integer.parseInt(args[3]);
            timeOutDuration = Integer.parseInt(args[4]);
        } catch(NumberFormatException e) {
            System.out.println("Parameters are illegal!");
            System.exit(-1);
        }
        System.setProperty("java.rmi.server.hostname", args[0]);
        System.setProperty("sun.rmi.transport.connectionTimeout", String.valueOf(timeOutDuration));
        LobbyController lobbyController = new LobbyController(turnDuration);
        ControllerDispatcher controllerDispatcher = new ControllerDispatcher(lobbyController);
        ServerDispatcher serverDispatcher = new ServerDispatcher(controllerDispatcher);
        SocketAccepter socketAccepter = new SocketAccepter(serverDispatcher, socketPort, lobbyController);
        lobbyController.setDispatcher(controllerDispatcher);
        new Thread(socketAccepter).start();
        RmiAccepter rmiAccepter = new RmiAccepter(lobbyController, serverDispatcher);
        Registry registry = LocateRegistry.createRegistry(rmiPort);
        registry.bind("default", rmiAccepter);
    }


}
