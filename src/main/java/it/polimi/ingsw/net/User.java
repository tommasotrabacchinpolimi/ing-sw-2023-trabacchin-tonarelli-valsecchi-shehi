package it.polimi.ingsw.net;

import it.polimi.ingsw.model.Player;

import java.rmi.Remote;

public class User<R extends RemoteInterface> {
    String nickname;
    ConnectionManager<? extends RemoteInterface, R> connectionManager;
    UserDispatcher userDispatcher;

    public UserDispatcher getUserDispatcher() {
        return userDispatcher;
    }

    public void setUserDispatcher(UserDispatcher userDispatcher) {
        this.userDispatcher = userDispatcher;
    }

    public void setConnectionManager(ConnectionManager<? extends RemoteInterface,R> connectionManager){
        this.connectionManager = connectionManager;
    }

    public ConnectionManager<? extends Remote,R> getConnectionManager(){
        return connectionManager;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
