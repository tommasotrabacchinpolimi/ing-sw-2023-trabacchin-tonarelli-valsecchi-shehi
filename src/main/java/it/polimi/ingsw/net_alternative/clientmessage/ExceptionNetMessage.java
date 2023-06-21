package it.polimi.ingsw.net_alternative.clientmessage;

import it.polimi.ingsw.net_alternative.ClientMessage;
import it.polimi.ingsw.net_alternative.ClientDispatcherInterface;

public class ExceptionNetMessage implements ClientMessage {
    private Exception exception;

    public ExceptionNetMessage(Exception exception) {
        this.exception = exception;
    }

    public Exception getException() {
        return exception;
    }

    @Override
    public void dispatch(ClientDispatcherInterface clientDispatcherInterface) {
        clientDispatcherInterface.dispatch(this);
    }
}
