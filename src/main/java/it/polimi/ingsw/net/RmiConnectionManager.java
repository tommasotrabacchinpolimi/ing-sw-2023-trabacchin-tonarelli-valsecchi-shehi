package it.polimi.ingsw.net;

import java.io.Serializable;
import java.lang.reflect.Proxy;
import java.rmi.RemoteException;
import com.google.gson.reflect.TypeToken;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class RmiConnectionManager<L extends RemoteInterface,R extends RemoteInterface> extends ConnectionManager<L,R>{

    private RmiReceiver<L,R> rmiReceiver;
    private L localRemoteObject;
    private final List<OnConnectionLostListener<R>> onConnectionLostListeners;

    public RmiConnectionManager() {
        this.onConnectionLostListeners = new LinkedList<>();
    }
    public synchronized void init(int portNumber, User<R> user, TypeToken<R> remoteTargetClass, TypeToken<L> localTargetClass, L localTarget, R remoteObject, ExecutorService executorService) throws RemoteException {
        this.rmiReceiver = new RmiReceiver<>(executorService,localTarget);
        this.rmiReceiver.setRmiConnectionManager(this);
        RmiSender<L,R> rmiSender = new RmiSender<>(remoteObject,this, executorService);
        setRemoteTarget((R) Proxy.newProxyInstance(remoteTargetClass.getRawType().getClassLoader(),new Class[] { remoteTargetClass.getRawType() },new BaseInvocationHandler(rmiSender)));
        this.localRemoteObject = (L)Proxy.newProxyInstance(localTarget.getClass().getClassLoader(), new Class[] { localTargetClass.getRawType() },new BaseInvocationHandler(rmiReceiver));
        UnicastRemoteObject.exportObject(localRemoteObject,portNumber);
        setUser(user);
        RmiHeartBeater<L,R> rmiHeartBeater = new RmiHeartBeater<>(remoteObject,5000,this);
        setConnectionStatus(ConnectionStatus.CONNECTED);
        new Thread(rmiHeartBeater).start();
    }

    public synchronized void init(RmiReceiver<L,R> rmiReceiver, Class<R> remoteTargetClass, L localRemoteObject, R remoteObject, ExecutorService executorService){
        RmiSender<L,R> rmiSender = new RmiSender<>(remoteObject,this, executorService);
        this.rmiReceiver = rmiReceiver;
        setRemoteTarget((R)Proxy.newProxyInstance(remoteTargetClass.getClassLoader(),new Class[] { remoteTargetClass },new BaseInvocationHandler(rmiSender)));
        this.localRemoteObject = localRemoteObject;
        setUser(null);
        RmiHeartBeater<L,R> rmiHeartBeater = new RmiHeartBeater<>(remoteObject,5000,this);
        setConnectionStatus(ConnectionStatus.CONNECTED);
        new Thread(rmiHeartBeater).start();
    }

    public  synchronized L getLocalRemoteObject(){
        return  localRemoteObject;
    }


}
