package it.polimi.ingsw.net;

import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.controller.ClassRewriting;

import java.io.IOException;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.lang.reflect.Proxy;

public class ConnectionBuilder {

    public static <L extends RemoteInterface, R extends RemoteInterface> SocketConnectionManager<L,R> buildSocketConnection(String host, int portNumber, L localTarget, TypeToken<R> remoteTargetClass) throws IOException {
        Socket socket = new Socket(host,portNumber);
        SocketConnectionManager<L,R> socketConnectionManager = new SocketConnectionManager<>();
        socketConnectionManager.init(socket,localTarget,remoteTargetClass);
        return socketConnectionManager;
    }

    public static <L extends RemoteInterface, R extends RemoteInterface> RmiConnectionManager<L,R> buildRMIConnection(String host, int portNumber, TypeToken<R> remoteTargetClass, TypeToken<L> localTargetClass, L localTarget) throws IOException, NotBoundException, ClassNotFoundException {
        Registry registry = LocateRegistry.getRegistry(host,2147);
        RmiReceiver<L,R> rmiReceiver = new RmiReceiver<>(localTarget);
        RemoteAccepterInterface remoteAccepterInterfaceInterface = (RemoteAccepterInterface) registry.lookup("default");
        Class<?> localThrowingTargetClass = ClassRewriting.getThrowingClass(localTargetClass.getRawType());
        RemoteInterface localRemoteObject = (RemoteInterface) Proxy.newProxyInstance(localThrowingTargetClass.getClassLoader(),new Class[]{localThrowingTargetClass},new BaseInvocationHandler(rmiReceiver));
        UnicastRemoteObject.exportObject(localRemoteObject,portNumber);
        RemoteInterface remoteObject = remoteAccepterInterfaceInterface.register(localRemoteObject);
        RmiConnectionManager<L,R> rmiConnectionManager = new RmiConnectionManager<>();
        rmiReceiver.setRmiConnectionManager(rmiConnectionManager);
        rmiConnectionManager.init(rmiReceiver, (Class<R>) remoteTargetClass.getRawType(),localRemoteObject,remoteObject);
        return rmiConnectionManager;
    }
}
