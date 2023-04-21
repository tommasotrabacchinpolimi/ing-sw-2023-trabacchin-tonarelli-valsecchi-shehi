package it.polimi.ingsw.net;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;

public class SocketSender<L extends RemoteInterface, R extends RemoteInterface> implements InvocationHandler {

    private final SynchronizedObjectOutputStream objectOutputStream;
    private final SocketConnectionManager<L,R> socketConnectionManager;
    private final ExecutorService executorService;


    public SocketSender(SynchronizedObjectOutputStream objectOutputStream, SocketConnectionManager<L,R> socketConnectionManager,
                        ExecutorService executorService)  {
        this.objectOutputStream = objectOutputStream;
        this.socketConnectionManager = socketConnectionManager;
        this.executorService = executorService;
    }

    @Override
    public synchronized Object invoke(Object proxy, Method method, Object[] args)  {
        NetMessage message = new NetMessage(method.getName(), args);
        try {
            objectOutputStream.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
            socketConnectionManager.connectionDown();

        }
        return null;
    }
}
