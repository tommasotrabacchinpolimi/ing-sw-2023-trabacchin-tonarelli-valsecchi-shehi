package it.polimi.ingsw.net_alternative;

import java.io.Serializable;

public interface ClientMessage extends Serializable {
    void dispatch(ClientDispatcherInterface clientDispatcherInterface);
}
