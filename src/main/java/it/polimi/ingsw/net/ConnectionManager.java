package it.polimi.ingsw.net;

import java.rmi.Remote;
import java.util.LinkedList;
import java.util.List;

public abstract class ConnectionManager<L extends RemoteInterface,R extends RemoteInterface> {

    public enum ConnectionStatus {
        CONNECTED,
        NOT_CONNECTED;
    }

    private User<R> user;

    private R remoteTarget;
    private ConnectionStatus connectionStatus = ConnectionStatus.NOT_CONNECTED;

    private final List<OnConnectionLostListener<R>> onConnectionLostListeners;

    public ConnectionManager() {
        this.onConnectionLostListeners = new LinkedList<>();
    }
    public ConnectionStatus getConnectionStatus() {
        return this.connectionStatus;
    }
    public void setConnectionStatus(ConnectionStatus connectionStatus) {
        this.connectionStatus = connectionStatus;
    }
    public void setUser(User<R> user) {
        this.user = user;
    }

    public User<R> getUser() {
        return user;
    }

    public void setRemoteTarget(R remoteTarget) {
        this.remoteTarget = remoteTarget;
    }

    public R getRemoteTarget() {
        return remoteTarget;
    }
    synchronized public void connectionDown(){
        if (connectionStatus == ConnectionStatus.CONNECTED) {
            connectionStatus = ConnectionStatus.NOT_CONNECTED;
            notifyConnectionLost();
        }
    }

    private synchronized void notifyConnectionLost() {
        for(OnConnectionLostListener<R> onConnectionLostListener : onConnectionLostListeners) {
            onConnectionLostListener.onConnectionLost(this.user.getConnectionManager().getRemoteTarget());
        }
    }
    public synchronized void setOnConnectionLostListener(OnConnectionLostListener<R> onConnectionLostListener) {
        this.onConnectionLostListeners.add(onConnectionLostListener);
    }

    public synchronized void removeOnConnectionLostListener(OnConnectionLostListener<R> onConnectionLostListener) {
        this.onConnectionLostListeners.remove(onConnectionLostListener);
    }


}
