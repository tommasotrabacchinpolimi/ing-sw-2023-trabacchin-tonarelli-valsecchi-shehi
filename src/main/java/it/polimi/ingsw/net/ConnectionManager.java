package it.polimi.ingsw.net;

import java.rmi.Remote;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @apiNote Default value for fields are set to permit
 * <ul>
 *     <li>the addition of {@linkplain #onConnectionLostListeners action when the connection is lost}
 *         without caring about initialization.</li>
 *     <li>the initial connection to {@linkplain ConnectionStatus#NOT_CONNECTED disconnected}, so the state can be
 *         set to {@linkplain ConnectionStatus#CONNECTED connected} if and only if after the connection between server
 *         and client is effectively working</li>
 * </ul>
 * <p>{@linkplain User} is not set with default constructor (so it should be {@linkplain #setUser(User) set} in a
 *    second time)</p>
 *
 * @param <L> the type of the local target object
 * @param <R> the type of the remote object
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 * @version 2.0
 * @since 06/04/2023
 */


public abstract class ConnectionManager<L extends RemoteInterface,R extends RemoteInterface> {

    /**
     * <p>Represent the connection status.</p>
     * <p>It can be:
     *     <ul>
     *         <li>{@linkplain #CONNECTED Connected}: if the local target object is still connected with the remote
     *              object</li>
     *         <li>{@linkplain #NOT_CONNECTED Disconnected}: if the local target object can't reach the remote object</li>
     *     </ul>
     * </p>
     */
    public enum ConnectionStatus {
        /**
         * Local target object can reach the remote object
         */
        CONNECTED,

        /**
         * Remote object can't be reached
         */
        NOT_CONNECTED;
    }

    /**
     *
     * @see User
     */
    private User<R> user;

    /**
     * <p>This field is a remote target which will handle different user actions</p>
     *
     * @see RemoteInterface
     */
    private R remoteTarget;

    /**
     * <p>Status of the connection between server and user</p>
     */
    private ConnectionStatus connectionStatus = ConnectionStatus.NOT_CONNECTED;

    /**
     *
     */
    private final List<OnConnectionLostListener<R>> onConnectionLostListeners;

    /**
     * <p>Default constructor which will </p>
     */
    public ConnectionManager() {
        this.onConnectionLostListeners = new LinkedList<>();
    }

    public ConnectionManager(User user) {
        this.onConnectionLostListeners = new LinkedList<>();
        this.user = user;
    }

    /**
     *
     * @return
     */
    public ConnectionStatus getConnectionStatus() {
        return this.connectionStatus;
    }

    /**
     *
     * @param connectionStatus
     */
    public void setConnectionStatus(ConnectionStatus connectionStatus) {
        this.connectionStatus = connectionStatus;
    }

    /**
     *
     * @param user
     */
    public void setUser(User<R> user) {
        this.user = user;
    }

    /**
     *
     * @return
     */
    public User<R> getUser() {
        return user;
    }

    /**
     *
     * @param remoteTarget
     */
    public void setRemoteTarget(R remoteTarget) {
        this.remoteTarget = remoteTarget;
    }

    /**
     *
     * @return
     */
    public R getRemoteTarget() {
        return remoteTarget;
    }

    /**
     *
     */
    public synchronized void connectionDown(){
        if (connectionStatus == ConnectionStatus.CONNECTED) {
            connectionStatus = ConnectionStatus.NOT_CONNECTED;
            notifyConnectionLost();
        }
    }

    /**
     *
     */
    private synchronized void notifyConnectionLost() {
        for(OnConnectionLostListener<R> onConnectionLostListener : onConnectionLostListeners) {
            onConnectionLostListener.onConnectionLost(this.user.getConnectionManager().getRemoteTarget());
        }
    }

    /**
     *
     * @param onConnectionLostListener
     */
    public synchronized void setOnConnectionLostListener(OnConnectionLostListener<R> onConnectionLostListener) {
        this.onConnectionLostListeners.add(onConnectionLostListener);
    }

    /**
     *
     * @param onConnectionLostListener
     */
    public synchronized void removeOnConnectionLostListener(OnConnectionLostListener<R> onConnectionLostListener) {
        this.onConnectionLostListeners.remove(onConnectionLostListener);
    }


}
