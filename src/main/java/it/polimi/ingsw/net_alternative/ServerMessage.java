package it.polimi.ingsw.net_alternative;

import it.polimi.ingsw.controller.ClientInterface;

import java.io.Serializable;

public interface ServerMessage extends Serializable {
    void dispatch(ServerDispatcherInterface serverDispatcher, ClientInterface view);
}
