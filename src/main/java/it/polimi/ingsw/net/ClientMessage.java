package it.polimi.ingsw.net;

import java.io.Serializable;

/**
 *
 * The ClientMessage interface represents a message sent from the client to the server.
 * It extends the Serializable interface to enable serialization and transmission of the message.
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public interface ClientMessage extends Serializable {
    /**
     *
     * @param clientDispatcherInterface The client dispatcher interface responsible for handling the message.
     */
    void dispatch(ClientDispatcherInterface clientDispatcherInterface);
}
