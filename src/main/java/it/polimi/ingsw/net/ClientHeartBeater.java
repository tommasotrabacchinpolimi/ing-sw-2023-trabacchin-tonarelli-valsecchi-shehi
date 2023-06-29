package it.polimi.ingsw.net;

import it.polimi.ingsw.controller.ServerInterface;

/**
 * The `ClientHeartBeater` class implements the `Runnable` interface and represents a thread
 * that periodically sends a "nop" message to the server to maintain the connection with the client.
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi

 */
public class ClientHeartBeater implements Runnable{

    /**
     * Represents the server
     */
    private final ServerInterface server;
    /**
     * it represents the maximum delay given to the player to make his choices inside the turn
     */
    private final long delay;
    /**
     * Listener needed here in case of loss connection
     */
    private final OnClientConnectionLostListener clientConnectionLostListener;

    /**
     * Constructs a `ClientHeartBeater` object with the specified parameters.
     *
     * @param clientConnectionLostListener the listener for client connection lost events
     * @param server                       the server interface
     * @param delay                        the delay between two consecutive "nop" messages
     */
    public ClientHeartBeater(OnClientConnectionLostListener clientConnectionLostListener, ServerInterface server, long delay) {
        this.server = server;
        this.delay = delay;
        this.clientConnectionLostListener = clientConnectionLostListener;
    }
    /**
     * Starts the execution of the thread.
     * The thread continuously sends "nop" messages to the server at a specified interval.
     * If the thread is interrupted, the client connection lost event is triggered.
     */
    @Override
    public void run() {
        while(true) {
            server.nop();
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
                //clientConnectionLostListener.onConnectionLost();
            }
        }
    }
}
