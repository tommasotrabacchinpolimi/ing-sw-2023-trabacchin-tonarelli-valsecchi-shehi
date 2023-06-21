package it.polimi.ingsw.net_alternative.clientmessage;

import it.polimi.ingsw.net_alternative.ClientMessage;
import it.polimi.ingsw.net_alternative.ClientDispatcherInterface;

public class BoardRefilledNetMessage implements ClientMessage {

    @Override
    public void dispatch(ClientDispatcherInterface clientDispatcherInterface) {
        clientDispatcherInterface.dispatch(this);
    }
}
