package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerState;
import it.polimi.ingsw.utils.Coordinate;

import java.util.List;

/**
 * The SuspendedGameManager class is responsible for managing the game when it is suspended.
 * <br>
 * It extends the GameManager class.
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 * @version 3.0
 * @since 28/04/2023
 */
public class SuspendedGameManager extends GameManager {
    GameState previousGameState;

    /**
     * Constructs a new SuspendedGameManager with the specified Controller and GameState.
     * <br>
     * @param controller the Controller instance
     * @param gameState the previous GameState before the game was suspended
     */
    public SuspendedGameManager(Controller controller, GameState gameState) {
        super(controller);
        previousGameState = gameState;
    }

    /**
     * {@inheritDoc}
     * This method is called when dragging tiles to the bookshelf in the suspended state.
     * <br>
     * It prints an error message to indicate that the method was called in the suspended state.
     */
    @Override
    public synchronized void dragTilesToBookShelf(ClientInterface view, List<Coordinate> chosenTiles, int chosenColumn) {
        System.err.println("called dragTilesToBookShelf in SUSPENDED State");
    }

    /**
     * {@inheritDoc}
     * This method is called when registering a player in the suspended state.
     * If the player exists and is in a disconnected state, it updates the player's view and state to connected.
     * If there are still enough connected players to resume the game, it restores the previous game state and transitions to the MidGameManager.
     *
     * @param view the ClientInterface of the player
     * @param nickname the nickname of the player
     */
    @Override
    public synchronized void registerPlayer(ClientInterface view, String nickname) {
        Player player = getController().getState().getPlayerFromNick(nickname);
        if(player!=null && player.getPlayerState()==PlayerState.DISCONNECTED) {
            ClientInterface oldView = getController().getState().getPlayerFromNick(nickname).getVirtualView();
            getController().getState().removeOnPlayersListChangedListener(oldView);
            getController().getState().setOnPlayersListChangedListener(view);
            registerListeners(view, nickname);
            player.setVirtualView(view);
            player.setPlayerState(PlayerState.CONNECTED);
            //if(checkIfNotSuspended()){
                getController().getState().setGameState(previousGameState);
                getController().setGameManager(new MidGameManager(getController()));
                //getController().getState().setCurrentPlayer(player);
            //}
        }
    }

    /**
     * {@inheritDoc}
     * This method is overridden and does nothing in the suspended state.
     */

    @Override
    public void setNextCurrentPlayer() {
        return;
    }

}
