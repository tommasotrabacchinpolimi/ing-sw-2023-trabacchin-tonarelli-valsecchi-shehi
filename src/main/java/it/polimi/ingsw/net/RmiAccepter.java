package it.polimi.ingsw.net;

import java.lang.reflect.Proxy;
import java.rmi.RemoteException;
import java.util.concurrent.ExecutorService;
import java.util.function.Supplier;

import com.google.gson.reflect.TypeToken;
public class RmiAccepter<L extends RemoteInterface,R extends RemoteInterface> implements RemoteAccepterInterface<L,R>{
    private final ExecutorService executorService;
    private final UserAccepter<R> userAccepter;
    private final Object localTarget;
    private final TypeToken<R> remoteTargetClass;
    private final TypeToken<L> localTargetClass;
    private final int portNumber;
    private final Supplier<UserAdapterInterface<R>> userAdapterSupplier;
    public RmiAccepter(int portNumber, UserAccepter<R> userAccepter, L localTarget, TypeToken<R> remoteTargetClass, TypeToken<L> localTargetClass, ExecutorService executorService){
        this.localTarget = localTarget;
        this.userAccepter = userAccepter;
        this.executorService = executorService;
        this.remoteTargetClass = remoteTargetClass;
        this.localTargetClass = localTargetClass;
        this.portNumber = portNumber;
        this.userAdapterSupplier = null;
    }

    public RmiAccepter(int portNumber, UserAccepter<R> userAccepter, Object localTarget, TypeToken<R> remoteTargetClass, TypeToken<L> localTargetClass,ExecutorService executorService,Supplier<UserAdapterInterface<R>> userAdapterSupplier){
        this.localTarget = localTarget;
        this.userAccepter = userAccepter;
        this.executorService = executorService;
        this.remoteTargetClass = remoteTargetClass;
        this.localTargetClass = localTargetClass;
        this.portNumber = portNumber;
        this.userAdapterSupplier = userAdapterSupplier;
    }
    @Override
    public L register(R remoteObject) throws RemoteException {
        User<R> user = new User<R>();
        if(userAccepter.acceptUser(user)){
            RmiConnectionManager<L,R> rmiConnectionManager = new RmiConnectionManager<>();
            if(userAdapterSupplier==null){
                rmiConnectionManager.init(portNumber,user,remoteTargetClass,localTargetClass,(L)localTarget,remoteObject,executorService);
            }
            else{
                UserAdapterInterface<R> userAdapterInterface = userAdapterSupplier.get();
                userAdapterInterface.setUser(user);
                userAdapterInterface.setTarget(localTarget);
                L newLocalTarget = (L) Proxy.newProxyInstance(localTargetClass.getRawType().getClassLoader(),new Class[]{localTargetClass.getRawType()},userAdapterInterface);
                rmiConnectionManager.init(portNumber,user,remoteTargetClass,localTargetClass,newLocalTarget,remoteObject,executorService);

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
