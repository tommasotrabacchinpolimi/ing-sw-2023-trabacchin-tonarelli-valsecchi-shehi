package it.polimi.ingsw.net_alternative;

import it.polimi.ingsw.controller.ClientInterface;

public class NopNetMessage implements ClientMessage, ServerMessage{
    @Override
    public void dispatch(ClientDispatcherInterface clientDispatcher) {
        clientDispatcher.dispatch(this);
    }

    @Override
    public void dispatch(ServerDispatcherInterface serverDispatcher, ClientInterface view) {
        serverDispatcher.dispatch(this);
    }
}
