package it.polimi.ingsw.net;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

public class SocketSender<L extends RemoteInterface, R extends RemoteInterface> implements InvocationHandler {

    private final SynchronizedObjectOutputStream objectOutputStream;
    private final SocketConnectionManager<L,R> socketConnectionManager;

    private final ExecutorService executorService;
    public SocketSender(SynchronizedObjectOutputStream objectOutputStream, SocketConnectionManager<L,R> socketConnectionManager)  {
        this.objectOutputStream = objectOutputStream;
        this.socketConnectionManager = socketConnectionManager;
        this.executorService = Executors.newFixedThreadPool(1);
    }


    @Override
    public synchronized Object invoke(Object proxy, Method method, Object[] args) {
        NetMessage message = new NetMessage(method.getName(), args);
        executorService.submit(() -> {
            try {
                objectOutputStream.writeObject(message);
            } catch (Exception e) {
                socketConnectionManager.connectionDown();
                e.printStackTrace();
            }
        });
        return null;
    }
}
