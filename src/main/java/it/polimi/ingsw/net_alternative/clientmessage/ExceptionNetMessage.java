package it.polimi.ingsw.net_alternative.clientmessage;

import it.polimi.ingsw.net_alternative.ClientMessage;
import it.polimi.ingsw.net_alternative.ClientDispatcherInterface;

/**
 * Represents a client message indicating an exception occurred during the game.
 * <p>
 * The {@code ExceptionNetMessage} class is a client message that notifies the client about an exception that occurred
 * during the game. It contains the exception object that encapsulates the details of the exception.
 * </p>
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi

 */
public class ExceptionNetMessage implements ClientMessage {
    /**
     * Variable used to represent the exception
     */
    private final Exception exception;

    /**
     * The player that caused the exception
     */
    private final String playerCause;

    /**
     * Constructs a new {@code ExceptionNetMessage} with the specified exception.
     *
     * @param exception the exception object representing the occurred exception
     */
    public ExceptionNetMessage(String causePlayer, Exception exception) {
        this.exception = exception;
        this.playerCause = causePlayer;
    }

    /**
     * Returns the exception object encapsulating the occurred exception.
     * @return the exception object sent by the client
     */
    public Exception getException() {
        return exception;
    }

    /**
     * Returns the player that caused the exception
     * @return the exception object sent by the client
     */
    public String getPlayerCause() {
        return playerCause;
    }

    /**
     * It dispatches the message to the client
     * @param clientDispatcherInterface is the handler of the message.
     */
    @Override
    public void dispatch(ClientDispatcherInterface clientDispatcherInterface) {
        clientDispatcherInterface.dispatch(this);
    }
}
