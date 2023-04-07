package it.polimi.ingsw.net;

import java.lang.reflect.Proxy;
import java.rmi.RemoteException;
import com.google.gson.reflect.TypeToken;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class RmiConnectionManager<L extends RemoteInterface,R extends RemoteInterface> extends ConnectionManager<L,R>{
    private RmiReceiver<L,R> rmiReceiver;
    private R remoteTarget;
    private User<R> user;
    private L localRemoteObject;
    private final List<OnConnectionLostListener<R>> onConnectionLostListeners;

    public RmiConnectionManager() {
        this.onConnectionLostListeners = new LinkedList<>();
    }
    public void init(int portNumber, User<R> user, TypeToken<R> remoteTargetClass, TypeToken<L> localTargetClass, L localTarget, R remoteObject, ExecutorService executorService) throws RemoteException {
        this.rmiReceiver = new RmiReceiver<>(executorService,localTarget);
        this.rmiReceiver.setRmiConnectionManager(this);
        RmiSender<L,R> rmiSender = new RmiSender<>(remoteObject,this, executorService);
        this.remoteTarget = (R) Proxy.newProxyInstance(remoteTargetClass.getRawType().getClassLoader(),new Class[] { remoteTargetClass.getRawType() },new BaseInvocationHandler(rmiSender));
        this.localRemoteObject = (L)Proxy.newProxyInstance(localTarget.getClass().getClassLoader(), new Class[] { localTargetClass.getRawType() },new BaseInvocationHandler(rmiReceiver));
        UnicastRemoteObject.exportObject(localRemoteObject,portNumber);
        this.user = user;
        RmiHeartBeater<L,R> rmiHeartBeater = new RmiHeartBeater<>(remoteObject,5000,this);
        new Thread(rmiHeartBeater).start();
    }

    public void init(RmiReceiver<L,R> rmiReceiver, Class<R> remoteTargetClass, L localRemoteObject, R remoteObject, ExecutorService executorService){
        RmiSender<L,R> rmiSender = new RmiSender<>(remoteObject,this, executorService);
        this.rmiReceiver = rmiReceiver;
        this.remoteTarget = (R) Proxy.newProxyInstance(remoteTargetClass.getClassLoader(),new Class[] { remoteTargetClass },new BaseInvocationHandler(rmiSender));
        this.localRemoteObject = localRemoteObject;
        this.user = null;
        RmiHeartBeater<L,R> rmiHeartBeater = new RmiHeartBeater<>(remoteObject,5000,this);
        new Thread(rmiHeartBeater).start();
    }
    public R getRemoteTarget(){
        return remoteTarget;
    }
    public  L getLocalRemoteObject(){
        return  localRemoteObject;
    }
    synchronized public void connectionDown(){
        notifyConnectionLost();
    }

    private void notifyConnectionLost() {
        for(OnConnectionLostListener<R> onConnectionLostListener : onConnectionLostListeners) {
            onConnectionLostListener.onConnectionLost(this.user.connectionManager.getRemoteTarget());
        }
    }
    public void setOnConnectionLostListener(OnConnectionLostListener<R> onConnectionLostListener) {
        this.onConnectionLostListeners.add(onConnectionLostListener);
    }

    public void removeOnConnectionLostListener(OnConnectionLostListener<R> onConnectionLostListener) {
        this.onConnectionLostListeners.remove(onConnectionLostListener);
    }

}
