package it.polimi.ingsw.net;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Proxy;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class SocketConnectionManager<L extends RemoteInterface, R extends RemoteInterface>  extends ConnectionManager<L,R>{


    public SocketConnectionManager() {
        super();
    }
    public void init(Socket socket, User<R> user, L localTarget, TypeToken<R> remoteTargetClass, ExecutorService executorService) throws IOException {
        init_base(socket, localTarget, remoteTargetClass, executorService);
        setUser(user);
    }

    public void init(Socket socket, L localTarget, TypeToken<R> remoteTargetClass, ExecutorService executorService) throws IOException {
        init_base(socket, localTarget, remoteTargetClass, executorService);
        setUser(null);
    }

    private synchronized void init_base(Socket socket, L localTarget, TypeToken<R> remoteTargetClass, ExecutorService executorService) throws IOException {
        SocketReceiver<L, R> socketReceiver = new SocketReceiver<L, R>(socket.getInputStream(), executorService, localTarget, this);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

        SocketSender<L, R> socketSender = new SocketSender<L, R>(new SynchronizedObjectOutputStream(objectOutputStream), this, executorService);
        setRemoteTarget((R) Proxy.newProxyInstance(remoteTargetClass.getRawType().getClassLoader(), new Class[] { remoteTargetClass.getRawType() }, new BaseInvocationHandler(socketSender)));
        SocketHeartBeater<L, R> socketHeartBeater = new SocketHeartBeater<>(new SynchronizedObjectOutputStream(objectOutputStream), this, 5000);
        setConnectionStatus(ConnectionStatus.CONNECTED);
        new Thread(socketHeartBeater).start();
        new Thread(socketReceiver).start();
    }

}
