package it.polimi.ingsw.controller;

import it.polimi.ingsw.net.RemoteInterface;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Dispatcher<R extends RemoteInterface> implements InvocationHandler {

    private LobbyController<R> lobbyController;

    private final Map<R,Controller<R>> viewToControllerMap;

    public Dispatcher() {
        viewToControllerMap = new ConcurrentHashMap<>();
    }

    public LobbyController<R> getLobbyController() {
        return lobbyController;
    }

    public void setLobbyController(LobbyController<R> lobbyController) {
        this.lobbyController = lobbyController;
    }

    public void setController(R view, Controller<R> controller) {
        viewToControllerMap.put(view, controller);
    }

    public void removeController(R view, Controller<R> controller) {
        viewToControllerMap.remove(view, controller);
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {

        if(Arrays.stream(LobbyController.class.getMethods()).map(Method::getName).anyMatch(n->n.equals(method.getName()))) {
            method.invoke(lobbyController, args);
        }
        else if (Arrays.stream(Controller.class.getMethods()).map(Method::getName).anyMatch(n->n.equals(method.getName()))) {
            method.invoke(viewToControllerMap.get(args[0]), args);
        }
        else {
            System.err.println("ignored call in dispatcher "+method.getName());
        }
        return null;
    }
}
