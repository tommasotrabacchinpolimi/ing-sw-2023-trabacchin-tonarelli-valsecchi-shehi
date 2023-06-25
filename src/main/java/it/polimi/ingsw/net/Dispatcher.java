package it.polimi.ingsw.net;
import it.polimi.ingsw.controller.*;

import java.beans.Statement;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>The dispatcher task is to {@linkplain #invoke(Object, Method, Object[]) invoke} the correct user initial action
 * that the appropriate {@linkplain LobbyController manager} can handle.</p>
 * <p>The request of a specific method comes from the network and the dispatcher has to manage it properly</p>
 *
 * @apiNote <p>Default value for its fields are set to permit
 * <ul>
 *     <li>the addition of a {@linkplain #viewToControllerMap correspondence} between {@linkplain ClientInterface users}
 *     and {@linkplain Controller controller} without caring about initialization.</li>
 *     <li>the bind to the "{@linkplain #lobbyController users initial actions manager}" in a second time</li>
 * </ul>
 * </p>
 * <p>{@linkplain LobbyController "Users initial actions manager"} is the handler for all initial actions that a player
 * can do (joining an existing game, create a new game match, set up its nickname, ...)</p>
 *
 * @see LobbyController
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 * @version 3.0
 * @since 23/04/2023
 */
public class Dispatcher implements InvocationHandler {

    /**
     * Reference to the class that manages the process of users joining in an existing game or that are trying to create
     * a new game match
     *
     * @see LobbyController
     */
    private LobbyController lobbyController;

    /**
     * <p>Holds a match between a {@linkplain ClientInterface user} and the corresponding controller that is handling
     * the game in which he is assigned</p>
     * <p>This fields is used to retrieve faster the {@linkplain Controller controller} given a specific
     * {@linkplain ClientInterface user} (that is used as "key")</p>
     */
    private final Map<ClientInterface, Controller> viewToControllerMap;

    /**
     * Default constructor to initialize fields at default value
     *
     * @see Dispatcher
     */
    public Dispatcher() {
        viewToControllerMap = new ConcurrentHashMap<>();
    }

    /**
     * Constructor to initialize attributes to a specific value
     *
     * @param lobbyController {@linkplain #lobbyController users initial actions manager}
     * @param viewToControllerMap correspondence between {@linkplain ClientInterface users}
     *                            and {@linkplain Controller controller}
     */
    public Dispatcher(LobbyController lobbyController, Map<ClientInterface, Controller> viewToControllerMap) {
        this.lobbyController = lobbyController;
        this.viewToControllerMap = viewToControllerMap;
    }

    /**
     * Retrieve the "{@linkplain #lobbyController users initial actions manager}" bound to the caller
     * @return {@linkplain #lobbyController users initial actions manager}
     */
    public LobbyController getLobbyController() {
        return lobbyController;
    }

    /**
     * Bind an "{@linkplain #lobbyController users initial actions manager}" to the caller
     *
     * @param lobbyController {@linkplain #lobbyController users initial actions manager} to be "connected"
     */
    public void setLobbyController(LobbyController lobbyController) {
        this.lobbyController = lobbyController;
    }

    /**
     * The purpose of this method is to create a correspondence between a {@linkplain ClientInterface user}
     * and a {@linkplain Controller controller}
     *
     * @param view the {@linkplain ClientInterface client} that needs to create a match with a controller
     * @param controller a {@linkplain Controller controller} of a game match in which the user is assigned
     *
     * @see ClientInterface
     * @see Controller
     */
    public void setController(ClientInterface view, Controller controller) {
        viewToControllerMap.put(view, controller);
    }

    /**
     * The purpose of this method is to destroy a correspondence between a {@linkplain ClientInterface user}
     * and a {@linkplain Controller controller}
     *
     * @param view the {@linkplain ClientInterface client} that needs to remove a match with a controller
     * @param controller a {@linkplain Controller controller} of a game match in which the user was assigned
     *
     * @see ClientInterface
     * @see Controller
     */
    public void removeController(ClientInterface view, Controller controller) {
        viewToControllerMap.remove(view, controller);
    }

    /**
     * {@inheritDoc}
     * <p>In this implementation the {@code method} parameter should be an action that the
     * {@linkplain LobbyController users initial actions manager} can handle.</p>
     *
     * @param proxy the proxy instance that the method was invoked on
     *
     * @param method <p>the {@code Method} instance corresponding to the interface method invoked on the proxy
     *               instance.</p>
     *               <p>The declaring class of the {@code Method} object will be the interface that the method was
     *               declared in, which may be a superinterface of the proxy interface that the proxy class inherits
     *               the method through.</p>
     *
     * @param args <p>an array of objects containing the values of the arguments passed in the method invocation on the
     *             proxy instance, or {@code null} if interface method takes no arguments.</p>
     *             <p>Arguments of primitive types are wrapped in instances of the appropriate primitive wrapper class,
     *             such as {@code java.lang.Integer} or {@code java.lang.Boolean}.</p>
     *
     * @return {@code null} because every method in {@linkplain LobbyController users initial actions manager}
     *         should not return an object
     *
     * @throws NullPointerException if the value of the {@code target} or {@code methodName} property is {@code null}
     * @throws NoSuchMethodException if a matching method is not found
     * @throws SecurityException if a security manager exists, and it denies the method invocation
     * @throws Exception that is thrown by the invoked method
     *
     * @see InvocationHandler
     * @see InvocationHandler#invoke(Object proxy, Method method, Object[] args)
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Exception {
        if (Arrays.stream(LobbyControllerInterface.class.getMethods())
                .map(Method::getName)
                .anyMatch(n -> n.equals(method.getName()))) {

            Statement st = new Statement(lobbyController, method.getName(), args);

            st.execute();
        } else if (Arrays.stream(ControllerInterface.class.getMethods())
                .map(Method::getName)
                .anyMatch(n -> n.equals(method.getName()))) {

            if(viewToControllerMap.get(args[0]) != null) {
                Statement st = new Statement(viewToControllerMap.get(args[0]), method.getName(), args);

                st.execute();
            }
        } else {
            System.err.println("ignored call in dispatcher " + method.getName());
        }

        return null;
    }
}