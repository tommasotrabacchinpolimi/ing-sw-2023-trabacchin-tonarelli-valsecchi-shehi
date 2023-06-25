package it.polimi.ingsw.net;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Proxy;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.function.Supplier;

/**
 *
 * @param <L> the type of the local target object
 * @param <R> the type of the remote object
 *
 * @see RemoteInterface
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 * @version 3.0
 * @since 06/04/2023
 */
public class SocketAccepter<L extends RemoteInterface, R extends RemoteInterface> implements Runnable {
    /**
     * <p>This fields specifies the port number for connection</p>
     */
    private final int portNumber;

    /**
     * <p>This field is a local target object on which invoke actions</p>
     *
     * @see Dispatcher
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
     * <p>This field is a reference to an actor that manage and notice if a potential user can play</p>
     *
     * @see it.polimi.ingsw.controller.LobbyController
     */
    private final UserAccepter<R> userAccepter;

    /**
     * <p>{@link Supplier} responsible for adapting users to the local target object</p>
     * <p>Thanks to this supplier an {@linkplain UserAdapterInterface user adapter interface} is generated "lazily"
     * and asynchronously</p>
     *
     * @see UserAdapterInterface
     */
    private final Supplier<UserAdapterInterface<R>> userAdapterSupplier;

    /**
     * Construct a new SocketAccepter with the given parameter
     *
     * @param portNumber the {@linkplain #portNumber port number} used for connection
     * @param userAccepter {@linkplain #userAccepter manager} that decide if a user can play
     * @param localTarget {@linkplain #localTarget local target} for actions
     * @param remoteTargetClass represents a generic {@linkplain RemoteInterface remote} type
     * @param executorService the executor service for asynchronous execution
     */
    public SocketAccepter(int portNumber,
                          UserAccepter<R> userAccepter,
                          L localTarget,
                          TypeToken<R> remoteTargetClass,
                          ExecutorService executorService) {
        this.portNumber = portNumber;
        this.localTarget = localTarget;
        this.remoteTargetClass = remoteTargetClass;
        this.userAccepter = userAccepter;
        this.userAdapterSupplier = null;
        this.localTargetClass = null;
    }

    /**
     * Construct a new SocketAccepter with the given parameter
     *
     * @param portNumber the {@linkplain #portNumber port number} used for connection
     * @param userAccepter {@linkplain #userAccepter manager} that decide if a user can play
     * @param localTarget {@linkplain #localTarget local target} for actions
     * @param remoteTargetClass represents a generic {@linkplain RemoteInterface remote} type
     * @param localTargetClass represents a generic {@linkplain RemoteInterface remote} type
     * @param executorService the executor service for asynchronous execution
     * @param userAdapterSupplier used for adapting users to the local target object
     */
    public SocketAccepter(int portNumber,
                          UserAccepter<R> userAccepter,
                          Object localTarget,
                          TypeToken<R> remoteTargetClass,
                          TypeToken<L> localTargetClass,
                          ExecutorService executorService,
                          Supplier<UserAdapterInterface<R>> userAdapterSupplier) {
        this.portNumber = portNumber;
        this.localTarget = localTarget;
        this.remoteTargetClass = remoteTargetClass;
        this.userAccepter = userAccepter;
        this.userAdapterSupplier = userAdapterSupplier;
        this.localTargetClass = localTargetClass;
    }

    /**
     * <p>Creates a server socket object that is listening on {@linkplain #portNumber port number}.</p>
     * <p>More precisely this method:
     * <ul>
     *     <li>registers a new {@linkplain User user} to an RMI connection</li>
     *     <li>setup the {@linkplain #localTarget local target} on which the user will execute action</li>
     *     <li>setup a controller for the connection loss between {@linkplain User user} and server</li>
     * </ul>
     * </p>
     *
     * @apiNote A {@link RuntimeException} is thrown if:
     * <ul>
     *     <li>error occurs while registering the remote object</li>
     *     <li>I/O error occur</li>
     *     <li>class of the remote object could not be found</li>
     * </ul>
     */
    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);

            while(true){
                Socket socket = serverSocket.accept();
                User<R> user = new User<R>();

                if(userAccepter.acceptUser(user)){
                    // If the user can play, an instance of connection manager is created for that player
                    SocketConnectionManager<L, R> socketConnectionManager = new SocketConnectionManager<>();

                    if(userAdapterSupplier == null){
                        // If the supplier is not created yet, a new RMI connection is set up
                        socketConnectionManager.init(socket,user,(L)localTarget, remoteTargetClass);
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
                        L newLocalTarget = (L) Proxy.newProxyInstance(localTargetClass.getRawType().getClassLoader(),new Class[]{localTargetClass.getRawType()},userAdapterInterface);
                        socketConnectionManager.init(socket,user,newLocalTarget, remoteTargetClass);
                    }

                    user.setConnectionManager(socketConnectionManager);
                    userAccepter.registerConnectionDownListener(user);
                }
                else{
                    try {
                        socket.close();
                    }catch(IOException ignored){
                        ignored.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
