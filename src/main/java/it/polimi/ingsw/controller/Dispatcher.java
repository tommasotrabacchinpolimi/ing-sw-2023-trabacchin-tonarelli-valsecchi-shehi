package it.polimi.ingsw.controller;
import java.beans.Statement;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Dispatcher implements InvocationHandler {

    private LobbyController lobbyController;

    private final Map<ClientInterface, Controller> viewToControllerMap;

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
    public Object invoke(Object proxy, Method method, Object[] args) throws Exception {
        if(Arrays.stream(LobbyControllerInterface.class.getMethods()).map(Method::getName).anyMatch(n->n.equals(method.getName()))) {
            java.beans.Statement st = new Statement(lobbyController, method.getName(), args);
            st.execute();
        }
        else if (Arrays.stream(ControllerInterface.class.getMethods()).map(Method::getName).anyMatch(n->n.equals(method.getName()))) {
            if(viewToControllerMap.get(args[0])!=null) {
                java.beans.Statement st = new Statement(viewToControllerMap.get(args[0]), method.getName(), args);
                st.execute();
            }
        }
        else {
            System.err.println("ignored call in dispatcher "+method.getName());
        }
        return null;
    }
}
