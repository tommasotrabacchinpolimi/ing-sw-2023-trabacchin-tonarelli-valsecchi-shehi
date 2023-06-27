package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.timing.TimingStateMachine;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.net_alternative.OnServerConnectionLostListener;
import it.polimi.ingsw.utils.Coordinate;

import java.io.FileNotFoundException;
import java.util.List;

/**
 *The Controller class manages the game logic and interactions between the model and the view.
 * It implements the ControllerInterface and OnConnectionLostListener interfaces.
 * The controller is responsible for handling player actions, managing the game state, and coordinating
 * communication between the model and the view.
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 * @version 3.0
 * @since 06/04/2023
 */
public class Controller implements OnServerConnectionLostListener, ControllerInterface {
    private State state;
    private GameManager gameManager;
    private final ChatManager chatManager;
    private final LobbyController lobbyController;
    private TimingStateMachine timingStateMachine;

    /**
     * Constructs a Controller object with the given initial state and lobby controller.
     * Initializes the game manager, chat manager, and timing state machine.
     * Sets the current player change listener to the timing state machine.
     * @param state the initial game state
     * @param lobbyController the lobby controller for managing the players
     * @throws FileNotFoundException if a file is not found during initialization
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
     */
    public State getState() {
        return state;
    }



    /**
     * method to set number of players for a game
     * @param numberOfPlayer the number of players to set
     */
    public void setNumberPlayers(int numberOfPlayer) {
        getState().setPlayersNumber(numberOfPlayer);
    }

    /**
     * method to get the lobby controller of the player
     * @return the current lobby controller
     */
    public LobbyController getLobbyController() {
        return lobbyController;
    }

    /**
     * method to get the game manager
     * @return the game manager of the game
     */
    public GameManager getGameManager() {
        return gameManager;
    }

    /**
     * method to set the game manager of the game
     * @param gameManager the gameManager to set
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

    @Override
    public synchronized void dragTilesToBookShelf(ClientInterface view, List<Coordinate> chosenTiles, int chosenColumn) {
        gameManager.dragTilesToBookShelf(view, chosenTiles, chosenColumn);
    }

    public synchronized void registerPlayer(ClientInterface view, String nickname) {
        gameManager.registerPlayer(view, nickname);

    }

    @Override
    public synchronized void quitGame(ClientInterface view) {
        gameManager.quitGame(view);
    }

    @Override
    public void sentMessage(ClientInterface view, String text, String[] receiversNickname) {
        chatManager.sentMessage(view, text, receiversNickname);
    }

    @Override
    public synchronized void onConnectionLost(ClientInterface user) {
        for(Player p : getState().getPlayers()){
            if(p.getVirtualView() == user){
                p.setPlayerState(PlayerState.DISCONNECTED);
            }
        }
    }


}
