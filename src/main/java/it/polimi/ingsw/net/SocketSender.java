package it.polimi.ingsw.net;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class SocketSender<L extends RemoteInterface, R extends RemoteInterface> implements InvocationHandler {
    private final ObjectOutputStream objectOutputStream;
    private final SocketConnectionManager<L,R> socketConnectionManager;

    public SocketSender(ObjectOutputStream objectOutputStream,SocketConnectionManager<L,R> socketConnectionManager)  {
        this.objectOutputStream = objectOutputStream;
        this.socketConnectionManager = socketConnectionManager;

    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args)  {
        try {
            NetMessage message = new NetMessage(method.getName(), args);
            objectOutputStream.writeObject(message);
        }catch(IOException ex){socketConnectionManager.connectionDown();}
        return null;
    }
}
