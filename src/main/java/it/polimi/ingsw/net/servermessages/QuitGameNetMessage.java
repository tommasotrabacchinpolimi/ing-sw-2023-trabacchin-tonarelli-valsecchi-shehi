package it.polimi.ingsw.net.servermessages;

import it.polimi.ingsw.controller.ClientInterface;
import it.polimi.ingsw.net.ServerMessage;
import it.polimi.ingsw.net.ServerDispatcherInterface;

/**
 * Represents a server message for quitting a game.
 * <br>
 * Implements {@link ServerMessage} interface.
 *
 *<br>
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi

 */
public class QuitGameNetMessage implements ServerMessage {

    /**
     * Dispatches the message to the server dispatcher for handling.
     *
     * @param serverDispatcher the server dispatcher interface
     * @param view             the client interface
     */
    @Override
    public void dispatch(ServerDispatcherInterface serverDispatcher, ClientInterface view) {
        serverDispatcher.dispatch(this, view);
    }
}
