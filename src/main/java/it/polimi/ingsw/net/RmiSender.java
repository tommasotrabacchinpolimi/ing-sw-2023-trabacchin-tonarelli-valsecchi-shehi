package it.polimi.ingsw.net;

import java.beans.Statement;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.rmi.Remote;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

public class RmiSender<L extends RemoteInterface,R extends RemoteInterface> implements InvocationHandler {


    private final RemoteInterface remoteObject;
    private final RmiConnectionManager<L,R> rmiConnectionManager;

    private final ExecutorService executorService;

    public RmiSender(RemoteInterface remoteObject,RmiConnectionManager<L,R> rmiConnectionManager){
        this.remoteObject = remoteObject;
        this.rmiConnectionManager = rmiConnectionManager;
        this.executorService = Executors.newFixedThreadPool(1);
    }

    @Override
    public synchronized Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            java.beans.Statement st = new Statement(remoteObject, method.getName(), args);
            executorService.submit(() -> {
                try {
                    st.execute();
                } catch (Exception e) {
                    rmiConnectionManager.connectionDown();
                }
            });
        }catch(Exception ex){
            rmiConnectionManager.connectionDown();
        }
        return null;
    }
}
