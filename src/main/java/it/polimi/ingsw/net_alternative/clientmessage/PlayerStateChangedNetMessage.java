package it.polimi.ingsw.net_alternative.clientmessage;

import it.polimi.ingsw.model.PlayerState;
import it.polimi.ingsw.net_alternative.ClientMessage;
import it.polimi.ingsw.net_alternative.ClientDispatcherInterface;

/**
 *  Represents a client message indicating that the player state has changed.
 *  <br>
 *  Extends {@link ClientMessage} Interface.
 * <br>
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi

 */
public class PlayerStateChangedNetMessage implements ClientMessage {

    private final String nickname;

    private final PlayerState playerState;

    /**
     * Represents a client message indicating that the state of the player has changed.
     * @param nickname of the player whose State has changed
     * @param playerState the new state of the player
     */
    public PlayerStateChangedNetMessage(String nickname, PlayerState playerState) {
        this.nickname = nickname;
        this.playerState = playerState;
    }

    /**
     * Getter method that gives the nickname of the player whose state has changed
     * @return the nickanme of the player whose state has changed
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Getter method that gives the new state of the player whose state has changed
     * @return the new state of the player whose state has changed
     */
    public PlayerState getPlayerState() {
        return playerState;
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
