package it.polimi.ingsw.net;

import java.rmi.Remote;

public abstract class ConnectionManager<L extends Remote,R extends Remote> {
    public abstract R getRemoteTarget();


}
