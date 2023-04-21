package it.polimi.ingsw.controller;

import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.net.*;

import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public class App {
    public static void main(String[] args) {
        LobbyController lobbyController = new LobbyController();
        Dispatcher dispatcherInvocationHandler = new Dispatcher();
        dispatcherInvocationHandler.setLobbyController(lobbyController);
        Object dispatcher =  Proxy.newProxyInstance(Dispatcher.class.getClassLoader(), new Class[] {LobbyControllerInterface.class, ControllerInterface.class}, dispatcherInvocationHandler);
        lobbyController.setDispatcher(dispatcherInvocationHandler);
        TypeToken<ClientInterface> typeToken = new TypeToken<>() {};
        TypeToken<ServerInterface> typeToken1 = new TypeToken<>() {};
        ExecutorService executorService = Executors.newCachedThreadPool();
        Supplier<UserAdapterInterface<ClientInterface>> userAdapterInterfaceSupplier = UserAdapter::new;
        SocketAccepter<ServerInterface, ClientInterface> socketAccepter = new SocketAccepter<>(1234, lobbyController, dispatcher, typeToken, executorService, userAdapterInterfaceSupplier, typeToken1);
        new Thread(socketAccepter).start();
        System.out.println("Server pronto");
    }
}
