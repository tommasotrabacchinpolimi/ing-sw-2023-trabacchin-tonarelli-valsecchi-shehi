package it.polimi.ingsw.net;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Proxy;
import java.rmi.Remote;
import java.rmi.RemoteException;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.controller.ClassRewriting;

import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class RmiConnectionManager<L extends RemoteInterface,R extends RemoteInterface> extends ConnectionManager<L,R>{

    private RmiReceiver<L,R> rmiReceiver;
    private RemoteInterface localRemoteObject;

    public synchronized void init(int portNumber, User<R> user, TypeToken<R> remoteTargetClass, TypeToken<L> localTargetClass, RemoteInterface localTarget, RemoteInterface remoteObject, ExecutorService executorService) throws IOException, ClassNotFoundException {
        this.rmiReceiver = new RmiReceiver<>(executorService,localTarget);
        this.rmiReceiver.setRmiConnectionManager(this);
        RmiSender<L,R> rmiSender = new RmiSender<>(remoteObject,this, executorService);
        //Class<?> remoteThrowingTargetClass = ClassRewriting.getThrowingClass(remoteTargetClass.getRawType());
        Class<?> localThrowingTargetClass = ClassRewriting.getThrowingClass(localTargetClass.getRawType());
        setRemoteTarget((R) Proxy.newProxyInstance(remoteTargetClass.getRawType().getClassLoader(),new Class[] { remoteTargetClass.getRawType() },new BaseInvocationHandler(rmiSender)));
        this.localRemoteObject = (L)Proxy.newProxyInstance(localThrowingTargetClass.getClassLoader(), new Class[] { localThrowingTargetClass },new BaseInvocationHandler(rmiReceiver));
        UnicastRemoteObject.exportObject(localRemoteObject,portNumber);
        setUser(user);
        RmiHeartBeater<L,R> rmiHeartBeater = new RmiHeartBeater<>(remoteObject,5000,this);
        setConnectionStatus(ConnectionStatus.CONNECTED);
        new Thread(rmiHeartBeater).start();
    }

    public synchronized void init(RmiReceiver<L,R> rmiReceiver, Class<R> remoteTargetClass, RemoteInterface localRemoteObject, RemoteInterface remoteObject, ExecutorService executorService) throws IOException, ClassNotFoundException {
        RmiSender<L,R> rmiSender = new RmiSender<>(remoteObject,this, executorService);
        this.rmiReceiver = rmiReceiver;
        //Class<?> remoteThrowingTargetClass = ClassRewriting.getThrowingClass(remoteTargetClass);
        setRemoteTarget((R)Proxy.newProxyInstance(remoteTargetClass.getClassLoader(),new Class[] { remoteTargetClass },new BaseInvocationHandler(rmiSender)));
        this.localRemoteObject = localRemoteObject;
        setUser(null);
        RmiHeartBeater<L,R> rmiHeartBeater = new RmiHeartBeater<>(remoteObject,5000,this);
        setConnectionStatus(ConnectionStatus.CONNECTED);
        new Thread(rmiHeartBeater).start();
    }

    public  synchronized RemoteInterface getLocalRemoteObject(){
        return  localRemoteObject;
    }


}
