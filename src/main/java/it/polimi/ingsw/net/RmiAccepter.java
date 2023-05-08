package it.polimi.ingsw.net;

import java.io.IOException;
import java.lang.reflect.Proxy;
import java.rmi.RemoteException;
import java.util.function.Supplier;
import com.google.gson.reflect.TypeToken;

/**
 *<p>This class is responsible for accepting remote objects over RMI and returning a proxy object to the client.
 * It implements the RemoteAccepterInterface interface and is parameterized by two type parameters, L and R, which
 * represent the local and remote interfaces, respectively.</p>
 *
 * @param <L> the type of the local target object
 * @param <R> the type of the remote object
 *
 * @see RemoteInterface
 * @see RemoteAccepterInterface
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 * @version 3.0
 * @since 23/04/2023
 */
public class RmiAccepter<L extends RemoteInterface, R extends RemoteInterface> implements RemoteAccepterInterface {
    /**
     * <p>This field is a reference to an actor that manage and notice if a potential user can play</p>
     *
     * @see it.polimi.ingsw.controller.LobbyController
     */
    private final UserAccepter<R> userAccepter;

    /**
     * <p>This field is a local target object on which invoke actions</p>
     *
     * @see it.polimi.ingsw.controller.Dispatcher
     */
    private final Object localTarget;

    /**
     * <p>Represents a generic type {@linkplain RemoteInterface remote object}</p>
     * <p>Forces clients to create a subclass of this class which enables retrieval the type information
     *    even at runtime</p>
     */
    private final TypeToken<R> remoteTargetClass;

    /**
     * <p>Represents a generic type {@linkplain RemoteInterface local object}</p>
     * <p>Forces clients to create a subclass of this class which enables retrieval the type information even at
     *    runtime</p>
     */
    private final TypeToken<L> localTargetClass;

    /**
     * <p>This fields specifies the port number for connection</p>
     */
    private final int portNumber;

    /**
     * <p>{@link Supplier} responsible for adapting users to the local target object</p>
     * <p>Thanks to this supplier an {@linkplain UserAdapterInterface user adapter interface} is generated "lazily"
     * and asynchronously</p>
     *
     * @see UserAdapterInterface
     */
    private final Supplier<UserAdapterInterface<R>> userAdapterSupplier;

    /**
     * Construct a new RmiAccepter object with the given parameter
     *
     * @param portNumber this is the {@linkplain #portNumber port number} used for connection
     * @param userAccepter {@linkplain #userAccepter manager} that decide if a user can play
     * @param localTarget {@linkplain #localTarget local target} for actions
     * @param remoteTargetClass represents a generic {@linkplain RemoteInterface remote} type
     * @param localTargetClass represents a generic {@linkplain RemoteInterface local} type
     */
    public RmiAccepter(int portNumber, UserAccepter<R> userAccepter, Object localTarget, TypeToken<R> remoteTargetClass, TypeToken<L> localTargetClass){
        this.localTarget = localTarget;
        this.userAccepter = userAccepter;
        this.remoteTargetClass = remoteTargetClass;
        this.localTargetClass = localTargetClass;
        this.portNumber = portNumber;
        this.userAdapterSupplier = null;
    }

    /**
     * Construct a new RmiAccepter object with the given parameter
     *
     * @param portNumber the {@linkplain #portNumber port number} used for connection
     * @param userAccepter {@linkplain #userAccepter manager} that decide if a user can play
     * @param localTarget {@linkplain #localTarget local target} for actions
     * @param remoteTargetClass represents a generic {@linkplain RemoteInterface remote} type
     * @param localTargetClass represents a generic {@linkplain RemoteInterface local} type
     * @param userAdapterSupplier used for adapting users to the local target object
     */
    public RmiAccepter(int portNumber, UserAccepter<R> userAccepter, Object localTarget, TypeToken<R> remoteTargetClass, TypeToken<L> localTargetClass, Supplier<UserAdapterInterface<R>> userAdapterSupplier){
        this.localTarget = localTarget;
        this.userAccepter = userAccepter;
        this.remoteTargetClass = remoteTargetClass;
        this.localTargetClass = localTargetClass;
        this.portNumber = portNumber;
        this.userAdapterSupplier = userAdapterSupplier;
    }

    /**
     * {@inheritDoc}
     * <p>More precisely this method:
     * <ul>
     *     <li>registers a new {@linkplain User user} to an RMI connection</li>
     *     <li>setup the {@linkplain #localTarget local target} on which the user will execute action</li>
     *     <li>setup a controller for the connection loss between {@linkplain User user} and server</li>
     * </ul>
     * </p>
     * @param remoteObject {@inheritDoc}
     * @return <p>{@code null} if the {@linkplain User user} can't play (refer to
     * {@link it.polimi.ingsw.controller.LobbyController#acceptUser(User u)} for more information).</p>
     * <p>{@linkplain RemoteInterface remote proxy object} if the {@linkplain User user} can play, in this last case
     * the RMI connection is also set up</p>
     *
     * @throws RemoteException {@inheritDoc}
     * @throws IOException {@inheritDoc}
     * @throws ClassNotFoundException {@inheritDoc}
     *
     * @see User
     * @see RemoteInterface
     * @see RemoteAccepterInterface
     */
    @Override
    public RemoteInterface register(RemoteInterface remoteObject)
            throws RemoteException, IOException, ClassNotFoundException {

        User<R> user = new User<R>();

        if(userAccepter.acceptUser(user)){
            // If the user can play, an instance of connection manager is created for that player
            RmiConnectionManager<L, R> rmiConnectionManager = new RmiConnectionManager<>();

            if(userAdapterSupplier == null) {
                // If the supplier is not created yet, a new RMI connection is set up
                rmiConnectionManager.init(portNumber, user, remoteTargetClass, localTargetClass, (L)localTarget, remoteObject);
            } else {
                /* If the supplier has been created in the past, the UserAdapterInterface is retrieved from the supplier
                 * At the UserAdapterInterface instance is connected the user created and the local target
                 * on which call action is bound.
                 * A new local target proxy is also created that will handle new calls coming from the new user added.
                 * In the end, a new RMI connection manager creates a new connection
                 */
                UserAdapterInterface<R> userAdapterInterface = userAdapterSupplier.get();
                userAdapterInterface.setUser(user);
                userAdapterInterface.setTarget(localTarget);

                /* Returns a proxy instance for the specified interfaces that dispatches method invocations to
                 * the specified invocation handler.
                 */
                L newLocalTarget = (L) Proxy.newProxyInstance(localTargetClass.getRawType().getClassLoader(),
                        new Class[]{localTargetClass.getRawType()}, userAdapterInterface);

                rmiConnectionManager.init(portNumber, user, remoteTargetClass, localTargetClass, newLocalTarget, remoteObject);
            }

            user.setConnectionManager(rmiConnectionManager);
            userAccepter.registerConnectionDownListener(user);

            return rmiConnectionManager.getLocalRemoteObject();
        } else {
            return null;
        }
    }
}