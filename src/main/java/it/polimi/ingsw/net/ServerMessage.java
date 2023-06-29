package it.polimi.ingsw.net;

import it.polimi.ingsw.controller.ClientInterface;

import java.io.Serializable;

/**
 * The ServerMessage interface represents a message that can be sent from the server to the client.
 * It extends the Serializable interface to support object serialization for network communication.
 */
public interface ServerMessage extends Serializable {

    /**
     * Dispatches the server message to the appropriate server dispatcher and client view.
     * This method is responsible for handling the server message on the client-side.
     *
     * @param serverDispatcher the server dispatcher to handle the message.
     * @param view             the client view to handle the message.
     */
    void dispatch(ServerDispatcherInterface serverDispatcher, ClientInterface view);
}
