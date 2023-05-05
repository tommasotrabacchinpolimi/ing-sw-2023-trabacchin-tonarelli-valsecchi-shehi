package it.polimi.ingsw.controller;

import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.net.*;

import java.lang.reflect.Proxy;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public class App {
    static private Registry registry;
    static private RmiAccepter<ServerInterface,ClientInterface> rmiAccepter;
    static private RemoteAccepterInterface stub;
    public static void main(String[] args) throws AlreadyBoundException, RemoteException {
        test();
    }

    private static void test() throws RemoteException, AlreadyBoundException {
        LobbyController lobbyController = new LobbyController();
        Dispatcher dispatcherInvocationHandler = new Dispatcher();
        dispatcherInvocationHandler.setLobbyController(lobbyController);

        Object dispatcher =  Proxy.newProxyInstance(Dispatcher.class.getClassLoader(), new Class[] {LobbyControllerInterface.class, ControllerInterface.class}, dispatcherInvocationHandler);

        lobbyController.setDispatcher(dispatcherInvocationHandler);
        TypeToken<ClientInterface> typeToken = new TypeToken<>() {};
        TypeToken<ServerInterface> typeToken1 = new TypeToken<>() {};

        ExecutorService executorService = Executors.newCachedThreadPool();
        Supplier<UserAdapterInterface<ClientInterface>> userAdapterInterfaceSupplier = UserAdapter::new;
        rmiAccepter = new RmiAccepter<ServerInterface, ClientInterface>(2147,lobbyController,dispatcher,typeToken,typeToken1,executorService,userAdapterInterfaceSupplier);
        registry = LocateRegistry.createRegistry(2147);
        stub = (RemoteAccepterInterface) UnicastRemoteObject.exportObject(rmiAccepter,2147);
        registry.bind("default",stub);
        SocketAccepter<ServerInterface, ClientInterface> socketAccepter = new SocketAccepter<>(1234, lobbyController, dispatcher, typeToken, executorService, userAdapterInterfaceSupplier, typeToken1);
        new Thread(socketAccepter).start();
        System.out.println("Server pronto");
    }
}
