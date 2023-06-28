package it.polimi.ingsw.net_alternative;

import it.polimi.ingsw.controller.ClientInterface;

/**
 * The `ServerHeartBeater` class implements the `Runnable` interface and represents a thread
 * that periodically sends a "nop" message to the client to maintain the connection with the server.
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public class ServerHeartBeater implements Runnable{

    /**
     * ClientInterface of the client to be checked if is "Alive" or not.
     */
    private final ClientInterface client;
    /**
     * Maximum delay under witch a Server is considered "Alive"
     */
    private final long delay;
    /**
     * Listener to properly manage a loss connection scenario
     */
    private final OnServerConnectionLostListener serverConnectionLostListener;

    /**
     * Constructs a `ServerHeartBeater` object with the specified parameters.
     *
     * @param client                      the client interface
     * @param delay                       the delay between two consecutive "nop" messages
     * @param serverConnectionLostListener the listener for server connection lost events
     */
    public ServerHeartBeater(ClientInterface client, long delay, OnServerConnectionLostListener serverConnectionLostListener) {
        this.client = client;
        this.delay = delay;
        this.serverConnectionLostListener = serverConnectionLostListener;
    }

    /**
     * Starts the execution of the thread.
     * The thread continuously sends "nop" messages to the client at a specified interval.
     * If the thread is interrupted, the server connection lost event is triggered.
     */
    @Override
    public void run() {
        while(true) {
            client.nop();
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ignored) {
                ignored.printStackTrace();
            }
        }
    }
}
