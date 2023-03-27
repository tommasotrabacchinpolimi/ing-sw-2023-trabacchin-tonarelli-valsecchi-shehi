package net;

import java.lang.reflect.Proxy;
import java.rmi.Remote;
import java.rmi.RemoteException;
import com.google.gson.reflect.TypeToken;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ExecutorService;

public class RmiConnectionManager<L extends RemoteInterface,R extends RemoteInterface> extends ConnectionManager<L,R>{
    private RmiReceiver<L,R> rmiReceiver;
    private R remoteTarget;
    private NetworkMonitor<R> networkMonitor;
    private User<R> user;
    private R remoteObject;
    private L localRemoteObject;


    public void init(int portNumber, NetworkMonitor<R> networkMonitor, User<R> user, TypeToken<R> remoteTargetClass, TypeToken<L> localTargetClass, L localTarget, R remoteObject, ExecutorService executorService) throws RemoteException {
        this.rmiReceiver = new RmiReceiver<>(executorService,localTarget);
        this.rmiReceiver.setRmiConnectionManager(this);
        RmiSender<L,R> rmiSender = new RmiSender<>(remoteObject,this);
        this.remoteTarget = (R) Proxy.newProxyInstance(remoteTargetClass.getRawType().getClassLoader(),new Class[] { remoteTargetClass.getRawType() },rmiSender);
        this.localRemoteObject = (L)Proxy.newProxyInstance(localTarget.getClass().getClassLoader(), new Class[] { localTargetClass.getRawType() },rmiReceiver);
        UnicastRemoteObject.exportObject(localRemoteObject,portNumber);
        this.user = user;
        this.networkMonitor = networkMonitor;
        this.remoteObject = remoteObject;
        RmiHeartBeater<L,R> rmiHeartBeater = new RmiHeartBeater<>(remoteObject,5000,this);
        new Thread(rmiHeartBeater).start();
    }

    public void init(NetworkMonitor<R> networkMonitor, RmiReceiver<L,R> rmiReceiver, Class<R> remoteTargetClass, L localRemoteObject, R remoteObject){
        RmiSender<L,R> rmiSender = new RmiSender<>(remoteObject,this);
        this.rmiReceiver = rmiReceiver;
        this.remoteTarget = (R) Proxy.newProxyInstance(remoteTargetClass.getClassLoader(),new Class[] { remoteTargetClass },rmiSender);
        this.localRemoteObject = localRemoteObject;
        this.networkMonitor = networkMonitor;
        this.user = null;
        this.remoteObject = remoteObject;
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
        if(this.networkMonitor!=null){
            networkMonitor.connectionDown(this.user);
            this.networkMonitor = null;
        }

    }

}
