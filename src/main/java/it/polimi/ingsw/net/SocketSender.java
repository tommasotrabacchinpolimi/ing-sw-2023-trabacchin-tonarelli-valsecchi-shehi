package it.polimi.ingsw.net;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;

public class SocketSender<L extends RemoteInterface, R extends RemoteInterface> implements InvocationHandler {

    public static final Method EQUALS;

    static {
        try {
            EQUALS = Object.class.getDeclaredMethod("equals", Object.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private final ObjectOutputStream objectOutputStream;
    private final SocketConnectionManager<L,R> socketConnectionManager;
    private final ExecutorService executorService;


    public SocketSender(ObjectOutputStream objectOutputStream, SocketConnectionManager<L,R> socketConnectionManager,
                        ExecutorService executorService)  {
        this.objectOutputStream = objectOutputStream;
        this.socketConnectionManager = socketConnectionManager;
        this.executorService = executorService;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args)  {
        if(method.equals(EQUALS)){
            return args[0] == proxy;
        }
        NetMessage message = new NetMessage(method.getName(), args);
        executorService.submit(() -> {
            try {
                objectOutputStream.writeObject(message);
            } catch (IOException e) {
                socketConnectionManager.connectionDown();
            }
        });

        return null;
    }
}
