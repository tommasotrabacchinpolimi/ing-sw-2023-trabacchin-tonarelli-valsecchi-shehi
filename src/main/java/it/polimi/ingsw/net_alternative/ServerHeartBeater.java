package it.polimi.ingsw.net_alternative;

import it.polimi.ingsw.controller.ClientInterface;

public class ServerHeartBeater implements Runnable{

    private ClientInterface client;
    private long delay;
    private OnServerConnectionLostListener serverConnectionLostListener;

    public ServerHeartBeater(ClientInterface client, long delay, OnServerConnectionLostListener serverConnectionLostListener) {
        this.client = client;
        this.delay = delay;
        this.serverConnectionLostListener = serverConnectionLostListener;
    }

    @Override
    public void run() {
        while(true) {
            client.nop();
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                serverConnectionLostListener.onConnectionLost(client);
            }
        }
    }
}
