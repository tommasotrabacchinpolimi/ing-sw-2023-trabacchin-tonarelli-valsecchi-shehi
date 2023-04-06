package it.polimi.ingsw.net;

import java.rmi.Remote;
import java.util.List;

public abstract class ConnectionManager<L extends RemoteInterface,R extends RemoteInterface> {
    public abstract R getRemoteTarget();


}
