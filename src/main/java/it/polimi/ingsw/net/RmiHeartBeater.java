package it.polimi.ingsw.net;

/**
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
