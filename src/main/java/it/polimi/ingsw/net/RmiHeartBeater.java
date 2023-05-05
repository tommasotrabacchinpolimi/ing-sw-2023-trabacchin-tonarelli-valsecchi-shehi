package it.polimi.ingsw.net;

/**
 * <p>This is a class that constantly checks the life of the connection</p>
 *

 *
 * <p>The class also has a run() method that implements the {@linkplain Runnable} interface.
 * It sends a "nop" message to the remote object every delay milliseconds, and if an exception occurs during this process,
 * the rmiConnectionManager's connectionDown() method is called to indicate that the connection is no longer active.</p>
 *
 * @param <L> the type of the local target object
 * @param <R> the type of the remote target object
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 * @version 1.0
 * @since 27/03/2023
 */

public class RmiHeartBeater<L extends RemoteInterface,R extends RemoteInterface> implements Runnable{
    private final RemoteInterface remoteObject;
    private final long delay;
    RmiConnectionManager<L,R> rmiConnectionManager;
    public RmiHeartBeater(RemoteInterface remoteObject, long delay, RmiConnectionManager<L,R> rmiConnectionManager){
        this.delay = delay;
        this.remoteObject = remoteObject;
        this.rmiConnectionManager = rmiConnectionManager;
    }

    /**
     *
     * This method extends {@linkplain Runnable}
     * <p>It goes in an infinite loop and constantly checks for the aliveness of connection</p>
     * The run() method is executed when a new thread is started through an instance of RmiHeartBeater.
     * It runs an infinite loop that pauses the thread for a duration specified by the delay parameter,
     * and in each cycle, it calls the nop() method on the remoteObject passed to the class constructor.
     * If the nop() method throws an exception, the connectionDown() method is called on the rmiConnectionManager passed to the constructor,
     * and the loop is interrupted.
     *
     */
    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(delay);
                remoteObject.nop();
            } catch (Exception e) {
                rmiConnectionManager.connectionDown();
                break;
            }
        }
    }
}
