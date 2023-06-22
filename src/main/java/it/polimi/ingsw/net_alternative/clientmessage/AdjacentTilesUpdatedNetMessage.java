package it.polimi.ingsw.net_alternative.clientmessage;

import it.polimi.ingsw.net_alternative.ClientMessage;
import it.polimi.ingsw.net_alternative.ClientDispatcherInterface;
import it.polimi.ingsw.utils.Coordinate;

import java.util.List;

/**
 * Represents a client message indicating that the adjacent tiles have been updated.
 * It implements the {@link ClientMessage} interface.
 */
public class AdjacentTilesUpdatedNetMessage implements ClientMessage {
    private String nickname;

    private List<Coordinate> tiles;

    /**
     * Constructor of the class
     * @param nickname is the nickname of the player
     * @param tiles The coordinates of the tiles being updated
     */
    public AdjacentTilesUpdatedNetMessage(String nickname, List<Coordinate> tiles) {
        this.nickname = nickname;
        this.tiles = tiles;
    }

    /**
     * Getter method for the nickname
     * @return the nickname of the player whose client notifies AdjacentTilesUpdates
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Getter method for the Tiles
     * @return the Tiles being updated
     */
    public List<Coordinate> getTiles() {
        return tiles;
    }


    /**
     * Method that allows to dispatch the message from the client
     * @param clientDispatcherInterface is {@link ClientDispatcherInterface} used to handle the message
     */
    @Override
    public void dispatch(ClientDispatcherInterface clientDispatcherInterface) {
        clientDispatcherInterface.dispatch(this);
    }
}
