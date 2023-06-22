package it.polimi.ingsw.net_alternative.clientmessage;

import it.polimi.ingsw.net_alternative.ClientMessage;
import it.polimi.ingsw.net_alternative.ClientDispatcherInterface;

/**
 * Represents a client message indicating that the game board has been refilled.
 * <p>
 * The {@code BoardRefilledNetMessage} class is a client message that notifies the client that the game board has been
 * refilled. This typically occurs when certain game actions or events trigger a refill of the game board tiles.
 * </p>
 *
 */
public class BoardRefilledNetMessage implements ClientMessage {



    /**
     * Dispatches the message to the client dispatcher for handling.
     *
     * @param clientDispatcherInterface the client dispatcher interface
     */
    @Override
    public void dispatch(ClientDispatcherInterface clientDispatcherInterface) {
        clientDispatcherInterface.dispatch(this);
    }
}
