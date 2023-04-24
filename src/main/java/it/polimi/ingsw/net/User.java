package it.polimi.ingsw.net;


import java.rmi.Remote;

public class User<R extends RemoteInterface> {
    private ConnectionManager<? extends RemoteInterface, R> connectionManager;

    public void setConnectionManager(ConnectionManager<? extends RemoteInterface,R> connectionManager){
        this.connectionManager = connectionManager;
    }

    public ConnectionManager<? extends Remote,R> getConnectionManager(){
        return connectionManager;
    }

}
