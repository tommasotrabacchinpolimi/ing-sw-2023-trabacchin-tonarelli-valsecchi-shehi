package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.timing.TimingStateMachine;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.net_alternative.OnServerConnectionLostListener;
import it.polimi.ingsw.utils.Coordinate;

import java.io.FileNotFoundException;
import java.util.List;

/**
 *The Controller class manages the game logic and interactions between the model and the view.
 * It implements the {@linkplain ControllerInterface} and {@linkplain OnServerConnectionLostListener} interfaces.
 * The controller is responsible for handling player actions, managing the game state, and coordinating
 * communication between the model and the view.
 *
 * @see ControllerInterface
 * @see OnServerConnectionLostListener
 * @see State
 * @see GameManager
 * @see ChatManager
 * @see LobbyController
 * @see it.polimi.ingsw.controller.timing.TimingStateMachine
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 * @version 3.0
 * @since 06/04/2023
 */
public class Controller implements OnServerConnectionLostListener, ControllerInterface {
    /**
     * State of the model
     * @see State
     */
    private State state;
    /**
     * The game manager responsible for managing the game flow and interactions
     * @see GameManager
     */
    private GameManager gameManager;
    /**
     * Chat Manager that handles the chat functionality in the game.
     * @see ChatManager
     */
    private final ChatManager chatManager;
    /**
     * The lobby controller
     * @see LobbyController
     */
    private final LobbyController lobbyController;
    /**
     * The timing state machine that handles timing-related functionality of the game.
     * @see TimingStateMachine
     */
    private TimingStateMachine timingStateMachine;

    /**
     * Constructs a Controller object with the given initial {@linkplain State state} and {@linkplain LobbyController lobby controller}.
     * Initializes the {@linkplain GameManager game manager}, {@linkplain ChatManager chat manager}, and {@linkplain TimingStateMachine timing state machine}.
     * Sets the {@linkplain it.polimi.ingsw.controller.listeners.OnCurrentPlayerChangedListener current player change listener} to the timing state machine.
     * @param state the initial game state
     * @param lobbyController the lobby controller for managing the players
     * @param delay turn duration
     * @param numberOfPlayer number of players in the game
     * @throws FileNotFoundException if a file is not found during initialization
     * @see State
     * @see LobbyController
     * @see GameManager
     * @see ChatManager
     * @see TimingStateMachine
     */
    public Controller(State state, LobbyController lobbyController, long delay, int numberOfPlayer) throws FileNotFoundException {
        this.state = state;
        this.lobbyController = lobbyController;
        this.gameManager = new InitGameManager(this);
        this.chatManager = new ChatManager(this);
        this.timingStateMachine = new TimingStateMachine(this, delay);
        state.setOnCurrentPlayerChangedListener(timingStateMachine);
        state.setStateChangedListener(timingStateMachine);
        this.setNumberPlayers(numberOfPlayer);
    }

    /**
     * Retrieves the current game state.
     * @return the current game state
     * @see State
     */
    public State getState() {
        return state;
    }



    /**
     * Set number of players for a game
     * @param numberOfPlayer the number of players to set
     */
    public void setNumberPlayers(int numberOfPlayer) {
        getState().setPlayersNumber(numberOfPlayer);
    }

    /**
     * method to get the lobby controller of the player
     * @return the current lobby controller
     * @see LobbyController
     */
    public LobbyController getLobbyController() {
        return lobbyController;
    }

    /**
     * method to get the game manager
     * @return the game manager of the game
     * @see GameManager
     */
    public GameManager getGameManager() {
        return gameManager;
    }

    /**
     * method to set the game manager of the game
     * @param gameManager the gameManager to set
     * @see GameManager
     */
    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    /**
     * method used to get the current player playing
     * @return the current player playing
     */
    public Player getPlayerPlaying(){
        return state.getCurrentPlayer();
    }

    /**
     * Handles the drag action of tiles from the board to the player's bookshelf.
     * @param view The {@linkplain ClientInterface client interface} of the player.
     * @param chosenTiles The list of {@linkplain Coordinate coordinates} of chosen tiles to be dragged.
     * @param chosenColumn The column of the bookshelf where the tiles are dragged to.
     * @see ControllerInterface#dragTilesToBookShelf(ClientInterface, List, int)
     * @see ClientInterface
     */
    @Override
    public synchronized void dragTilesToBookShelf(ClientInterface view, List<Coordinate> chosenTiles, int chosenColumn) {
        gameManager.dragTilesToBookShelf(view, chosenTiles, chosenColumn);
    }

    /**
     * Registers a player in the game.
     * @param view {@linkplain ClientInterface client interface} of the player.
     * @param nickname The nickname of the player.
     * @see ClientInterface
     */
    public synchronized void registerPlayer(ClientInterface view, String nickname) {
        gameManager.registerPlayer(view, nickname);

    }

    /**
     * Quits the game for a player.
     * @param view The {@linkplain ClientInterface client interface} of the player.
     * @see ControllerInterface#quitGame(ClientInterface)
     */
    @Override
    public synchronized void quitGame(ClientInterface view) {
        gameManager.quitGame(view);
    }

    /**
     * Sends a message in the chat.
     * @param view The {@linkplain ClientInterface client interface} of the sender.
     * @param text The message body.
     * @param receiversNickname The nicknames of the players who should receive the message.
     * @see ControllerInterface#sentMessage(ClientInterface, String, String[]) 
     */
    @Override
    public void sentMessage(ClientInterface view, String text, String[] receiversNickname) {
        chatManager.sentMessage(view, text, receiversNickname);
    }

    /**
     * Handles the event when a player's connection is lost.
     * @param user The {@linkplain ClientInterface client interface} of the disconnected player.
     * @see OnServerConnectionLostListener#onConnectionLost(ClientInterface) 
     */
    @Override
    public synchronized void onConnectionLost(ClientInterface user) {
        for(Player p : getState().getPlayers()){
            if(p.getVirtualView() == user){
                p.setPlayerState(PlayerState.DISCONNECTED);
            }
        }
    }


}
