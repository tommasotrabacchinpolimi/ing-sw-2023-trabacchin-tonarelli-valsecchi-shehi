package it.polimi.ingsw.controller;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Dispatcher implements InvocationHandler {

    private LobbyController lobbyController;

    private final Map<ClientInterface,Controller> viewToControllerMap;

    public Dispatcher() {
        viewToControllerMap = new ConcurrentHashMap<>();
    }

    public LobbyController getLobbyController() {
        return lobbyController;
    }

    public void setLobbyController(LobbyController lobbyController) {
        this.lobbyController = lobbyController;
    }

    public void setController(ClientInterface view, Controller controller) {
        viewToControllerMap.put(view, controller);
    }

    public void removeController(ClientInterface view, Controller controller) {
        viewToControllerMap.remove(view, controller);
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
        System.out.println("check 1");
        if(Arrays.stream(LobbyController.class.getMethods()).map(Method::getName).anyMatch(n->n.equals(method.getName()))) {
            method.invoke(lobbyController, args);
            System.out.println("in lobby");
        }
        else if (Arrays.stream(Controller.class.getMethods()).map(Method::getName).anyMatch(n->n.equals(method.getName()))) {
            if(viewToControllerMap.get(args[0])!=null) {
                System.out.println("in controller");
                method.invoke(viewToControllerMap.get(args[0]), args);

            }

        }
        else {
            System.err.println("ignored call in dispatcher "+method.getName());
        }
        return null;
    }
}
