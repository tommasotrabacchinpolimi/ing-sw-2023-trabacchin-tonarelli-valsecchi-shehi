package net;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RemoteObject;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ExecutorService;
import java.lang.reflect.Proxy;

public class ConnectionBuilder {

    static <L extends RemoteInterface, R extends RemoteInterface> SocketConnectionManager<L,R> buildSocketConnection(String host, int portNumber, NetworkMonitor<R> networkMonitor, L localTarget, TypeToken<R> remoteTargetClass, ExecutorService executorService) throws IOException {
        Socket socket = new Socket(host,portNumber);
        SocketConnectionManager<L,R> socketConnectionManager = new SocketConnectionManager<>();
        socketConnectionManager.init(networkMonitor,socket,localTarget,remoteTargetClass,executorService);
        return socketConnectionManager;
    }

    static <L extends RemoteInterface, R extends RemoteInterface> RmiConnectionManager<L,R> buildRmiConnection(String host, int portNumber, NetworkMonitor<R> networkMonitor, TypeToken<R> remoteTargetClass, TypeToken<L> localTargetClass, L localTarget, ExecutorService executorService) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(host,2147);
        RmiReceiver<L,R> rmiReceiver = new RmiReceiver<>(executorService,localTarget);
        RemoteAccepterInterface<R,L> remoteAccepterInterfaceInterface = (RemoteAccepterInterface<R,L>) registry.lookup("default");
        L localRemoteObject = (L)Proxy.newProxyInstance(localTargetClass.getRawType().getClassLoader(),new Class[]{localTargetClass.getRawType()},rmiReceiver);
        UnicastRemoteObject.exportObject(localRemoteObject,portNumber);
        R remoteObject = remoteAccepterInterfaceInterface.register(localRemoteObject);
        RmiConnectionManager<L,R> rmiConnectionManager = new RmiConnectionManager<L,R>();
        rmiReceiver.setRmiConnectionManager(rmiConnectionManager);
        rmiConnectionManager.init(networkMonitor,rmiReceiver, (Class<R>) remoteTargetClass.getRawType(),localRemoteObject,remoteObject);
        return rmiConnectionManager;

    }
}
