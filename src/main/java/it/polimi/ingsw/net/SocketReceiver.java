package it.polimi.ingsw.net;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.concurrent.ExecutorService;
import java.beans.Statement;
import java.util.concurrent.Executors;

/**
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 * @version 1.0
 * @since 27/03/2023
 */

public class SocketReceiver<L extends RemoteInterface, R extends RemoteInterface> implements Runnable {
    private final InputStream inputStream;
    private final ExecutorService executorService;
    private final L localTarget;
    private final SocketConnectionManager<L, R> socketConnectionManager;

    public SocketReceiver(InputStream inputStream, L localTarget, SocketConnectionManager<L,R> socketConnectionManager) {
        this.inputStream = inputStream;
        this.executorService = Executors.newFixedThreadPool(1);
        this.localTarget = localTarget;
        this.socketConnectionManager = socketConnectionManager;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            while (true) {
                try {
                    NetMessage message = (NetMessage) objectInputStream.readObject();
                    java.beans.Statement st = new Statement(localTarget, message.getMethod(), message.getParams());
                    executorService.submit(() -> {
                        try {
                            st.execute();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }catch(Exception ex){
                    ex.printStackTrace();
                    socketConnectionManager.connectionDown();
                    break;
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            socketConnectionManager.connectionDown();
        }
    }
}

