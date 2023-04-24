package it.polimi.ingsw.net;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Proxy;
import java.net.Socket;

public class SocketConnectionManager<L extends RemoteInterface, R extends RemoteInterface>  extends ConnectionManager<L,R>{


    public SocketConnectionManager() {
        super();
    }
    public void init(Socket socket, User<R> user, L localTarget, TypeToken<R> remoteTargetClass) throws IOException {
        init_base(socket, localTarget, remoteTargetClass);
        setUser(user);
    }

    public void init(Socket socket, L localTarget, TypeToken<R> remoteTargetClass) throws IOException {
        init_base(socket, localTarget, remoteTargetClass);
        setUser(null);
    }

    private synchronized void init_base(Socket socket, L localTarget, TypeToken<R> remoteTargetClass) throws IOException {
        SocketReceiver<L, R> socketReceiver = new SocketReceiver<L, R>(socket.getInputStream(), localTarget, this);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

        SocketSender<L, R> socketSender = new SocketSender<L, R>(new SynchronizedObjectOutputStream(objectOutputStream), this);
        setRemoteTarget((R) Proxy.newProxyInstance(remoteTargetClass.getRawType().getClassLoader(), new Class[] { remoteTargetClass.getRawType() }, new BaseInvocationHandler(socketSender)));
        SocketHeartBeater<L, R> socketHeartBeater = new SocketHeartBeater<>(new SynchronizedObjectOutputStream(objectOutputStream), this, 5000);
        setConnectionStatus(ConnectionStatus.CONNECTED);
        new Thread(socketHeartBeater).start();
        new Thread(socketReceiver).start();
    }

}
