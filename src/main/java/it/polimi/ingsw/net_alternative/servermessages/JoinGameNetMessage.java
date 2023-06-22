package it.polimi.ingsw.net_alternative.servermessages;

import it.polimi.ingsw.controller.ClientInterface;
import it.polimi.ingsw.net_alternative.ServerMessage;
import it.polimi.ingsw.net_alternative.ServerDispatcherInterface;

/**

 * Represents a server message for joining a game.
 * <br>
 * Implements {@link ServerMessage} interface.
 * <br>
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi

 */
public class JoinGameNetMessage implements ServerMessage {
    private final String nickname;

    /**
     * Constructs an instance of JoinGameNetMessage.
     *
     * @param nickname the nickname of the player joining the game
     */
     public JoinGameNetMessage(String nickname) {
         this.nickname = nickname;
     }

    /**
     * Returns the nickname of the player joining the game.
     *
     * @return the nickname of the player
     */
     public String getNickname() {
         return this.nickname;
     }

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
