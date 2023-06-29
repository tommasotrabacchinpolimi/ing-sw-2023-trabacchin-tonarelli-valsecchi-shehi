package it.polimi.ingsw.net;

import it.polimi.ingsw.controller.ClientInterface;

/**
 * Represents a no-operation (NOP) message that can be sent between the client and the server.
 * It implements both the {@link ClientMessage} and {@link ServerMessage} interfaces.
 *
 * <p>The `NopNetMessage` class serves as a simple communication mechanism to keep the connection active
 * or to acknowledge a message without any specific action.
 *
 * <p>When dispatched as a client message, it is sent to the client-side dispatcher for processing.
 * When dispatched as a server message, it is sent to the server-side dispatcher for processing.
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public class NopNetMessage implements ClientMessage, ServerMessage{

    /**
     *
     * Dispatches the NOP message to the client-side dispatcher for processing.
     * @param clientDispatcher The client-side dispatcher that handles the message.
     */
    @Override
    public void dispatch(ClientDispatcherInterface clientDispatcher) {
        clientDispatcher.dispatch(this);
    }


    @Override
    public void dispatch(ServerDispatcherInterface serverDispatcher, ClientInterface view) {
        serverDispatcher.dispatch(this);
    }
}
