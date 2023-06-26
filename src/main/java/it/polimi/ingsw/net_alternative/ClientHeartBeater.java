package it.polimi.ingsw.net_alternative;

import it.polimi.ingsw.controller.ServerInterface;

public class ClientHeartBeater implements Runnable{

    private ServerInterface server;
    private long delay;
    private OnClientConnectionLostListener clientConnectionLostListener;

    public ClientHeartBeater(OnClientConnectionLostListener clientConnectionLostListener, ServerInterface server, long delay) {
        this.server = server;
        this.delay = delay;
        this.clientConnectionLostListener = clientConnectionLostListener;
    }
    @Override
    public void run() {
        while(true) {
            server.nop();
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                clientConnectionLostListener.onConnectionLost();
            }
        }
    }
}
