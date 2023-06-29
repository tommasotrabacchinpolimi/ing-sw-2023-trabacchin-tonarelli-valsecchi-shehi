package it.polimi.ingsw.net.clientmessage;

import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.net.ClientMessage;
import it.polimi.ingsw.net.ClientDispatcherInterface;
/**
 * Represents a client message indicating a change in the game state.
 * <p>
 * The {@code StateChangedNetMessage} class is a client message that notifies the client about a change in the game state.
 * It contains the updated {@link GameState} value representing the new state of the game.
 * </p>
 * <br>
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi

 */

public class StateChangedNetMessage implements ClientMessage {

    /**
     * Represents the state of the game
     * @see GameState
     */
    private final GameState gameState;

    /**
     * Constructs a {@code StateChangedNetMessage} with the specified game state.
     *
     * @param gameState the new game state
     */
    public StateChangedNetMessage(GameState gameState) {
        this.gameState = gameState;
    }

    /**
     * Returns the game state.
     *
     * @return the game state
     */
    public GameState getGameState() {
        return gameState;
    }


    /**
     * Dispatches the message to the client dispatcher for handling.
     *
     * @param clientDispatcherInterface the client dispatcher interface
     */
    @Override
    public void dispatch(ClientDispatcherInterface clientDispatcherInterface) {
        clientDispatcherInterface.dispatch(this);
    }
}
