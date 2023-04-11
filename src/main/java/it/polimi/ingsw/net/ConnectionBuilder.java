package it.polimi.ingsw.net;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ExecutorService;
import java.lang.reflect.Proxy;

public class ConnectionBuilder {

    public static <L extends RemoteInterface, R extends RemoteInterface> SocketConnectionManager<L,R> buildSocketConnection(String host, int portNumber, L localTarget, TypeToken<R> remoteTargetClass, ExecutorService executorService) throws IOException {
        Socket socket = new Socket(host,portNumber);
        SocketConnectionManager<L,R> socketConnectionManager = new SocketConnectionManager<>();
        socketConnectionManager.init(socket,localTarget,remoteTargetClass,executorService);
        return socketConnectionManager;
    }

    public static <L extends RemoteInterface, R extends RemoteInterface> RmiConnectionManager<L,R> buildRmiConnection(String host, int portNumber, TypeToken<R> remoteTargetClass, TypeToken<L> localTargetClass, L localTarget, ExecutorService executorService) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(host,2147);
        RmiReceiver<L,R> rmiReceiver = new RmiReceiver<>(executorService,localTarget);
        RemoteAccepterInterface<R,L> remoteAccepterInterfaceInterface = (RemoteAccepterInterface<R,L>) registry.lookup("default");
        L localRemoteObject = (L)Proxy.newProxyInstance(localTargetClass.getRawType().getClassLoader(),new Class[]{localTargetClass.getRawType()},new BaseInvocationHandler(rmiReceiver));
        UnicastRemoteObject.exportObject(localRemoteObject,portNumber);
        R remoteObject = remoteAccepterInterfaceInterface.register(localRemoteObject);
        RmiConnectionManager<L,R> rmiConnectionManager = new RmiConnectionManager<>();
        rmiReceiver.setRmiConnectionManager(rmiConnectionManager);
        rmiConnectionManager.init(rmiReceiver, (Class<R>) remoteTargetClass.getRawType(),localRemoteObject,remoteObject, executorService);
        return rmiConnectionManager;

    }
}
