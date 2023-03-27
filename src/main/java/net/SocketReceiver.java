package net;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.rmi.Remote;
import java.util.concurrent.ExecutorService;
import java.beans.Statement;

public class SocketReceiver<L extends RemoteInterface, R extends RemoteInterface> implements Runnable {
    private final InputStream inputStream;
    private final ExecutorService executorService;
    private final L localTarget;
    private final SocketConnectionManager<L,R> socketConnectionManager;

    public SocketReceiver(InputStream inputStream, ExecutorService executorService, L localTarget,SocketConnectionManager<L,R> socketConnectionManager) {
        this.inputStream = inputStream;
        this.executorService = executorService;
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
                        } catch (Exception ex) {
                            socketConnectionManager.connectionDown();
                        }
                    });
                }catch(Exception ex){
                    socketConnectionManager.connectionDown();
                    break;

                }
            }

        } catch (Exception ex) {
            socketConnectionManager.connectionDown();
        }
    }
}

