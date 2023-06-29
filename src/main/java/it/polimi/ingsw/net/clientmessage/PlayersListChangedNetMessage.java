package it.polimi.ingsw.net.clientmessage;

import it.polimi.ingsw.net.ClientMessage;
import it.polimi.ingsw.net.ClientDispatcherInterface;

import java.util.List;

/**
 * Represents a client message indicating that the Player list has changed.
 * <br>
 * Extends {@link ClientMessage} Interface.
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi

 */
public class PlayersListChangedNetMessage implements ClientMessage {
    /**
     * Used to represent players after modification
     */
    private final List<String> players;

    /**
     * Constructor of the class
     * @param players is the list of payers that still playing the game after modification.
     */
    public PlayersListChangedNetMessage(List<String> players) {
        this.players = players;
    }

    /**
     * getter method to have the payers playing the game
     * @return  list of payers that still playing the game after modification.
     */
    public List<String> getPlayers() {
        return players;
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
