package it.polimi.ingsw.net_alternative.servermessages;

import it.polimi.ingsw.controller.ClientInterface;
import it.polimi.ingsw.net_alternative.ServerMessage;
import it.polimi.ingsw.net_alternative.ServerDispatcherInterface;

import java.io.Serializable;

/**
 * Represents a server message for creating a game.
 * <br>
 * Implements {@link ServerMessage} and {@link Serializable} interfaces.
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi

 */
public class CreateGameNetMessage implements ServerMessage, Serializable {
    private final String nickname;
    private final int numberOfPlayer;

    /**
     * Constructs an instance of CreateGameNetMessage.
     *
     * @param nickname        the nickname of the player creating the game
     * @param numberOfPlayer  the number of players for the game
     */
    public CreateGameNetMessage(String nickname, int numberOfPlayer) {
        this.nickname = nickname;
        this.numberOfPlayer = numberOfPlayer;
    }

    /**
     * Returns the nickname of the player creating the game.
     *
     * @return the nickname of the player
     */
    @Override
    public void dispatch(ServerDispatcherInterface serverDispatcher, ClientInterface view) {
        serverDispatcher.dispatch(this, view);
    }

    /**
     * Returns the number of players for the game.
     *
     * @return the number of players
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Dispatches the message to the server dispatcher for handling.
     *
     * @param serverDispatcher the server dispatcher interface
     * @param view             the client interface
     */
    public int getNumberOfPlayer() {
        return numberOfPlayer;
    }
}
