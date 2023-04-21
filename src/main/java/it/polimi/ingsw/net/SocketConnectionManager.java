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
    private R remoteTarget;
    private User<R> user;

    private final List<OnConnectionLostListener<R>> onConnectionLostListeners;

    public SocketConnectionManager() {
        onConnectionLostListeners = new LinkedList<>();
    }
    public void init(Socket socket, User<R> user, L localTarget, TypeToken<R> remoteTargetClass, ExecutorService executorService) throws IOException {
        init_base(socket, localTarget, (TypeToken<R>) remoteTargetClass, executorService);
        this.user = user;
    }

    public void init(Socket socket, L localTarget, TypeToken<R> remoteTargetClass, ExecutorService executorService) throws IOException {
        init_base(socket, localTarget, (TypeToken<R>) remoteTargetClass, executorService);
        this.user = null;
    }

    private synchronized void init_base(Socket socket, L localTarget, TypeToken<R> remoteTargetClass, ExecutorService executorService) throws IOException {
        SocketReceiver<L, R> socketReceiver = new SocketReceiver<L, R>(socket.getInputStream(), executorService, localTarget, this);

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

        SocketSender<L, R> socketSender = new SocketSender<L, R>(new SynchronizedObjectOutputStream(objectOutputStream), this, executorService);

        this.remoteTarget = (R) Proxy.newProxyInstance(remoteTargetClass.getRawType().getClassLoader(), new Class[] { remoteTargetClass.getRawType() }, new BaseInvocationHandler(socketSender));

        //SocketHeartBeater<L, R> socketHeartBeater = new SocketHeartBeater<>(this.objectOutputStream, this, 5000);

        //new Thread(socketHeartBeater).start();
        new Thread(socketReceiver).start();
    }

    public synchronized R getRemoteTarget(){
        return remoteTarget;
    }

    synchronized public void connectionDown(){
        notifyConnectionLost();
    }

    private synchronized void notifyConnectionLost() {
        for(OnConnectionLostListener<R> onConnectionLostListener : onConnectionLostListeners) {
            onConnectionLostListener.onConnectionLost(this.user.getConnectionManager().getRemoteTarget());
        }
    }
    public synchronized void setOnConnectionLostListener(OnConnectionLostListener<R> onConnectionLostListener) {
        this.onConnectionLostListeners.add(onConnectionLostListener);
    }

    public synchronized void removeOnConnectionLostListener(OnConnectionLostListener<R> onConnectionLostListener) {
        this.onConnectionLostListeners.remove(onConnectionLostListener);
    }

}
