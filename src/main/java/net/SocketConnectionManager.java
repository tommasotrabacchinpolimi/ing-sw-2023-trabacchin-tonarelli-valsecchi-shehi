package net;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Proxy;
import java.net.Socket;
import java.rmi.Remote;
import java.util.concurrent.ExecutorService;

public class SocketConnectionManager<L extends RemoteInterface, R extends RemoteInterface>  extends ConnectionManager<L,R>{
    private SocketReceiver<L,R> socketReceiver;
    private R remoteTarget;
    private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private NetworkMonitor<R> networkMonitor;
    private User<R> user;

    public void init(NetworkMonitor<R> networkMonitor, Socket socket, User<R> user, L localTarget, TypeToken<R> remoteTargetClass, ExecutorService executorService) throws IOException {
        init_base((NetworkMonitor<R>) networkMonitor, socket, localTarget, (TypeToken<R>) remoteTargetClass, executorService);
        this.user = user;
    }

    public void init(NetworkMonitor<R> networkMonitor, Socket socket, L localTarget, TypeToken<R> remoteTargetClass, ExecutorService executorService) throws IOException {
        init_base((NetworkMonitor<R>) networkMonitor, socket, localTarget, (TypeToken<R>) remoteTargetClass, executorService);
        this.user = null;
    }

    private void init_base(NetworkMonitor<R> networkMonitor, Socket socket, L localTarget, TypeToken<R> remoteTargetClass, ExecutorService executorService) throws IOException {
        this.socketReceiver = new SocketReceiver<L,R>(socket.getInputStream(),executorService, localTarget,this);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        this.objectOutputStream = objectOutputStream;
        SocketSender<L,R> socketSender = new SocketSender<L,R>(objectOutputStream,this);
        this.remoteTarget = (R) Proxy.newProxyInstance(remoteTargetClass.getRawType().getClassLoader(), new Class[] { remoteTargetClass.getRawType() }, socketSender);
        this.socket = socket;
        this.networkMonitor = networkMonitor;
        SocketHeartBeater<L, R> socketHeartBeater = new SocketHeartBeater<>(this.objectOutputStream, this, 5000);
        new Thread(socketHeartBeater).start();
        new Thread(this.socketReceiver).start();
    }

    public R getRemoteTarget(){
        return remoteTarget;
    }

    synchronized public void connectionDown(){
        try {
            this.socket.close();
        }catch(Exception ignored){}
        finally {
            if(this.networkMonitor!=null){
                networkMonitor.connectionDown(this.user);
                this.networkMonitor = null;
            }
        }
    }

}
