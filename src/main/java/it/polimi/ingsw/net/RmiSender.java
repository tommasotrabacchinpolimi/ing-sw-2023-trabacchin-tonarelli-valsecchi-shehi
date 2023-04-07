package it.polimi.ingsw.net;

import java.beans.Statement;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;

public class RmiSender<L extends RemoteInterface,R extends RemoteInterface> implements InvocationHandler {


    private final R remoteObject;
    private final RmiConnectionManager<L,R> rmiConnectionManager;
    private final ExecutorService executorService;
    public RmiSender(R remoteObject,RmiConnectionManager<L,R> rmiConnectionManager, ExecutorService executorService){
        this.remoteObject = remoteObject;
        this.rmiConnectionManager = rmiConnectionManager;
        this.executorService = executorService;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        try {
            java.beans.Statement st = new Statement(remoteObject, method.getName(), args);
            executorService.submit(() -> {
                try {
                    st.execute();
                } catch (Exception e) {
                    rmiConnectionManager.connectionDown();
                }
            });
            st.execute();
        }catch(Exception ex){
            rmiConnectionManager.connectionDown();
        }
        return null;
    }
}
