package it.polimi.ingsw.net_alternative.clientmessage;

import it.polimi.ingsw.net_alternative.ClientDispatcherInterface;
import it.polimi.ingsw.net_alternative.ClientMessage;
import it.polimi.ingsw.utils.Coordinate;

import java.util.List;

/**
 * Represents a client message indicating that a Common goal has been achieved.
 * It implements the {@link ClientMessage} interface.
 */
public class AchievedCommonGoalNetMessage implements ClientMessage {
    private String nicknamePlayer;
    private List<Coordinate> tiles;
    private int numberCommonGoal;

    /**
     * Constructor of the class
     * @param nicknamePlayer nickname of the player
     * @param tiles The list of Tile {@link Coordinate} that made it possible to achieve CommonGoal
     * @param numberCommonGoal Number of the specific CommonGoal being Achieved
     */
    public AchievedCommonGoalNetMessage(String nicknamePlayer, List<Coordinate> tiles, int numberCommonGoal) {
        this.nicknamePlayer = nicknamePlayer;
        this.tiles = tiles;
        this.numberCommonGoal = numberCommonGoal;
    }


    /**
     * Method that allows to get the Nickname of the player
     * @return the nickname of the Player that sends the message
     */
    public String getNicknamePlayer() {
        return nicknamePlayer;
    }

    /**
     * Retrieves the nickname associated with the message.
     *
     * @return The List of Tile {@link Coordinate} that made it possible to achieve CommonGoal.
     */
    public List<Coordinate> getTiles() {
        return tiles;
    }

    /**
     * Retrieves the CommonGoalNumber associated with the message.
     *
     * @return the CommonGoalNumber being achieved.
     */
    public int getNumberCommonGoal() {
        return numberCommonGoal;
    }

    /**
     * Method that allows to dispatch the message from the client
     * @param clientDispatcherInterface is the handler of the message
     */
    @Override
    public void dispatch(ClientDispatcherInterface clientDispatcherInterface) {
        clientDispatcherInterface.dispatch(this);
    }
}