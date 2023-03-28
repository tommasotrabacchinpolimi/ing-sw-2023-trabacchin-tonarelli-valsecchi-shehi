package it.polimi.ingsw.net;

public class RmiHeartBeater<L extends RemoteInterface,R extends RemoteInterface> implements Runnable{
    private final R remoteObject;
    private final long delay;
    RmiConnectionManager<L,R> rmiConnectionManager;
    public RmiHeartBeater(R remoteObject, long delay, RmiConnectionManager<L,R> rmiConnectionManager){
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
