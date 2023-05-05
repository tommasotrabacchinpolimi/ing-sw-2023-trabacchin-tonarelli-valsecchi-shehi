package it.polimi.ingsw.net;

import java.io.ObjectOutputStream;
/**
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 * @version 2.0
 * @since 22/04/2023
 */

public class SocketHeartBeater<L extends RemoteInterface,R extends RemoteInterface> implements Runnable{
    private final SynchronizedObjectOutputStream objectOutputStream;
    private final SocketConnectionManager<L,R> socketConnectionManager;
    long delay;
    public SocketHeartBeater(SynchronizedObjectOutputStream objectOutputStream, SocketConnectionManager<L,R> socketConnectionManager, long delay){
        this.objectOutputStream = objectOutputStream;
        this.socketConnectionManager = socketConnectionManager;
        this.delay = delay;
    }

    @Override
    public void run() {
        NetMessage message = new NetMessage("nop",null);
        while(true){
            try {
                Thread.sleep(delay);
                objectOutputStream.writeObject(message);
            } catch (Exception ex) {
                socketConnectionManager.connectionDown();
                break;
            }
        }
    }
}
