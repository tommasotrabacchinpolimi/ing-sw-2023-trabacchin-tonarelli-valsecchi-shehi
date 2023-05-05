package it.polimi.ingsw.net;

import java.beans.Statement;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
/**
 * The RmiSender class implements the InvocationHandler interface and is used to send remote method invocation requests
 * on the client side of an RMI (Remote Method Invocation) system.
 * It contains type parameters L and R which are required to extend the RemoteInterface interface.
 * The class contains an ExecutorService object that creates a thread pool with a fixed number of threads,
 * one in this case. The remoteObject variable holds a reference to the remote object that will be invoked remotely.
 * The RmiConnectionManager object is used to manage the RMI connections.
 * The invoke method is called by the RMI system when a remote method invocation is received.
 * This method uses a java.beans.Statement object to execute the method call on the remote object in a separate thread
 * managed by the executor service. If a RemoteException occurs during the method invocation, the connectionDown method
 * is called on the RMI connection manager to notify it that the connection has been lost.
 * Overall, this class is an essential part of an RMI system that allows remote method calls to be handled on the client side.
 * @param <L> The type of the local object.
 * @param <R> The type of the remote object.
 *
 *
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 * @version 3.0
 * @since 23/04/2023
 */
public class RmiSender<L extends RemoteInterface,R extends RemoteInterface> implements InvocationHandler {


    private final RemoteInterface remoteObject;
    private final RmiConnectionManager<L,R> rmiConnectionManager;

    private final ExecutorService executorService;

    public RmiSender(RemoteInterface remoteObject,RmiConnectionManager<L,R> rmiConnectionManager){
        this.remoteObject = remoteObject;
        this.rmiConnectionManager = rmiConnectionManager;
        this.executorService = Executors.newFixedThreadPool(1);
    }

    /**
     *
     * @param proxy the proxy instance that the method was invoked on
     *
     * @param method the {@code Method} instance corresponding to
     * the interface method invoked on the proxy instance.  The declaring
     * class of the {@code Method} object will be the interface that
     * the method was declared in, which may be a superinterface of the
     * proxy interface that the proxy class inherits the method through.
     *
     * @param args an array of objects containing the values of the
     * arguments passed in the method invocation on the proxy instance,
     * or {@code null} if interface method takes no arguments.
     * Arguments of primitive types are wrapped in instances of the
     * appropriate primitive wrapper class, such as
     * {@code java.lang.Integer} or {@code java.lang.Boolean}.
     *
     * @return null since the method call is being executed asynchronously
     */
    @Override
    public synchronized Object invoke(Object proxy, Method method, Object[] args)  {
        try {
            java.beans.Statement st = new Statement(remoteObject, method.getName(), args);
            executorService.submit(() -> {
                try {
                    st.execute();
                } catch (RemoteException e) {
                    rmiConnectionManager.connectionDown();
                }
                catch(Exception ex) {
                    throw new RuntimeException(ex);
                }
            });
        }catch(Exception ex){
            rmiConnectionManager.connectionDown();
        }
        return null;
    }
}
