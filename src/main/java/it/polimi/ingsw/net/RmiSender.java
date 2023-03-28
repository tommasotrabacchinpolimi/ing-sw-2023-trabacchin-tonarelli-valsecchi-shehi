package it.polimi.ingsw.net;

import java.beans.Statement;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class RmiSender<L extends RemoteInterface,R extends RemoteInterface> implements InvocationHandler {
    private final R remoteObject;
    private final RmiConnectionManager<L,R> rmiConnectionManager;
    public RmiSender(R remoteObject,RmiConnectionManager<L,R> rmiConnectionManager){
        this.remoteObject = remoteObject;
        this.rmiConnectionManager = rmiConnectionManager;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        try {
            java.beans.Statement st = new Statement(remoteObject, method.getName(), args);
            st.execute();
        }catch(Exception ex){
            rmiConnectionManager.connectionDown();
        }
        return null;
    }
}
