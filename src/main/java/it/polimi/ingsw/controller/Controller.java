package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.net.OnConnectionLostListener;
import it.polimi.ingsw.utils.Coordinate;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
public class Controller implements OnConnectionLostListener<ClientInterface>, ControllerInterface {
    private State state;
    private GameManager gameManager;
    private final ChatManager chatManager;
    private LobbyController lobbyController;
    private final TimingStateMachine timingStateMachine;

    /**
     * Constructs a Controller object with the given initial state and lobby controller.
     * Initializes the game manager, chat manager, and timing state machine.
     * Sets the current player change listener to the timing state machine.
     * @param state the initial game state
     * @param lobbyController the lobby controller for managing the players
     * @throws FileNotFoundException if a file is not found during initialization
     */
    public Controller(State state, LobbyController lobbyController) throws FileNotFoundException {
        this.state = state;
        this.lobbyController = lobbyController;
        this.gameManager = new InitGameManager(this);
        this.chatManager = new ChatManager(this);
        this.timingStateMachine = new TimingStateMachine(this);
        state.setOnCurrentPlayerChangedListener(timingStateMachine);
    }

    /**
     * Retrieves the current game state.
     * @return the current game state
     */
    public State getState() {
        return state;
    }

    /**
     * Sets the current game state.
     * @param state the game state to set
     */
    public void setState(State state) {
        this.state = state;
    }

    /**
     * Retrieves the active common goal 1
     * @return the active common goal 1
     */
    public CommonGoal getActiveCommonGoal1(){
        return state.getCommonGoal1();
    }

    /**
     * Retrieves the active common goal 2
     * @return the active common goal 2
     */
    public CommonGoal getActiveCommonGoal2(){
        return state.getCommonGoal2();
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
     * method to set the lobby controller of the player
     *
     */
    public void setLobbyController(LobbyController lobbyController) {
        this.lobbyController = lobbyController;
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

    @Override
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

    public TimingStateMachine getTimingStateMachine() {
        return timingStateMachine;
    }
}
