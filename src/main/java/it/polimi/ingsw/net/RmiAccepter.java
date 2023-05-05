package it.polimi.ingsw.net;

import java.io.IOException;
import java.lang.reflect.Proxy;
import java.rmi.RemoteException;
import java.util.concurrent.ExecutorService;
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
     * This field is used to manage and notice if a potential user can play
     */
    private final UserAccepter<R> userAccepter;

    /**
     * <p>This field is a local target object to invoke correct actions</p>
     */
    private final Object localTarget;

    /**
     * <p>Represents a generic type {@linkplain RemoteInterface remote object}</p>
     * <p>Forces clients to create a subclass of this class which enables retrieval the type information even at runtime</p>
     */
    private final TypeToken<R> remoteTargetClass;

    /**
     * <p>Represents a generic type {@linkplain RemoteInterface local object}</p>
     * <p>Forces clients to create a subclass of this class which enables retrieval the type information even at runtime</p>
     */
    private final TypeToken<L> localTargetClass;

    /**
     * <p>This parameter specifies the port for connection</p>
     */
    private final int portNumber;

    /**
     * <p>supplier responsible for adapting users to the local target object</p>
     */
    private final Supplier<UserAdapterInterface<R>> userAdapterSupplier;


    /**
     *
     * @param portNumber this is the port number used for connection
     * @param userAccepter Object to manage and notice if a potential user can play
     * @param localTarget local target object
     * @param remoteTargetClass Represents a generic type {@linkplain RemoteInterface remote object}
     * @param localTargetClass Represents a generic type {@linkplain RemoteInterface local object}
     * @param executorService The executor service for the asynchronous execution
     */
    public RmiAccepter(int portNumber, UserAccepter<R> userAccepter, Object localTarget, TypeToken<R> remoteTargetClass, TypeToken<L> localTargetClass, ExecutorService executorService){
        this.localTarget = localTarget;
        this.userAccepter = userAccepter;
        this.remoteTargetClass = remoteTargetClass;
        this.localTargetClass = localTargetClass;
        this.portNumber = portNumber;
        this.userAdapterSupplier = null;
    }

    /**
     *
     * @param portNumber this is the port number used for connection
     * @param userAccepter Object to manage and notice if a potential user can play
     * @param localTarget local target object
     * @param remoteTargetClass Represents a generic type {@linkplain RemoteInterface remote object}
     * @param localTargetClass Represents a generic type {@linkplain RemoteInterface local object}
     * @param executorService The executor service for the asynchronous execution
     * @param userAdapterSupplier Used for adapting users to the local target object
     */
    public RmiAccepter(int portNumber, UserAccepter<R> userAccepter, Object localTarget, TypeToken<R> remoteTargetClass, TypeToken<L> localTargetClass, ExecutorService executorService, Supplier<UserAdapterInterface<R>> userAdapterSupplier){
        this.localTarget = localTarget;
        this.userAccepter = userAccepter;
        this.remoteTargetClass = remoteTargetClass;
        this.localTargetClass = localTargetClass;
        this.portNumber = portNumber;
        this.userAdapterSupplier = userAdapterSupplier;
    }

    /**
     *
     * @param remoteObject
     * @return
     * @throws RemoteException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @Override
    public RemoteInterface register(RemoteInterface remoteObject) throws RemoteException, IOException, ClassNotFoundException {
        User<R> user = new User<R>();
        if(userAccepter.acceptUser(user)){
            RmiConnectionManager<L,R> rmiConnectionManager = new RmiConnectionManager<>();
            if(userAdapterSupplier==null){
                rmiConnectionManager.init(portNumber,user,remoteTargetClass,localTargetClass,(L)localTarget,remoteObject);
            }
            else{
                UserAdapterInterface<R> userAdapterInterface = userAdapterSupplier.get();
                userAdapterInterface.setUser(user);
                userAdapterInterface.setTarget(localTarget);
                L newLocalTarget = (L) Proxy.newProxyInstance(localTargetClass.getRawType().getClassLoader(),new Class[]{localTargetClass.getRawType()},userAdapterInterface);
                rmiConnectionManager.init(portNumber,user,remoteTargetClass,localTargetClass,newLocalTarget,remoteObject);
            }
            user.setConnectionManager(rmiConnectionManager);
            userAccepter.registerConnectionDownListener(user);
            return rmiConnectionManager.getLocalRemoteObject();
        }
        else{
            return null;
        }
    }
}
