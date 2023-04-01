package it.polimi.ingsw.net;

import it.polimi.ingsw.model.Player;

import java.rmi.Remote;

public class User<R extends RemoteInterface> {
    Player player;
    ConnectionManager<? extends RemoteInterface,R> connectionManager;
    public void setConnectionManager(ConnectionManager<? extends RemoteInterface,R> connectionManager){
        this.connectionManager = connectionManager;
    }

    public ConnectionManager<? extends Remote,R> getConnectionManager(){
        return connectionManager;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
