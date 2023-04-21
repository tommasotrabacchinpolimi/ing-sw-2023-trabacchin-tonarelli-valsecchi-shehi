package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.net.OnConnectionLostListener;

import java.io.FileNotFoundException;

public class Controller implements OnConnectionLostListener<ClientInterface>, ControllerInterface {
    private State state;
    private GameManager gameManager;
    private ChatManager<ClientInterface> chatManager;
    private LobbyController lobbyController;

    public Controller(State state, LobbyController lobbyController) throws FileNotFoundException {
        this.state = state;
        this.lobbyController = lobbyController;
        this.gameManager = new InitGameManager(this);
    };

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Player getPlayerPlaying(){
        return state.getCurrentPlayer();
    }

    public CommonGoal getActiveCommonGoal1(){
        return state.getCommonGoal1();
    }

    public CommonGoal getActiveCommonGoal2(){
        return state.getCommonGoal2();
    }


    public void setNumberPlayers(int numberOfPlayer) {
        getState().setPlayersNumber(numberOfPlayer);
    }

    public LobbyController getLobbyController() {
        return lobbyController;
    }

    public void setLobbyController(LobbyController lobbyController) {
        this.lobbyController = lobbyController;
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public synchronized void dragTilesToBookShelf(ClientInterface view, int[] chosenTiles, int chosenColumn) {
        gameManager.dragTilesToBookShelf(view, chosenTiles, chosenColumn);
    }

    public synchronized void registerPlayer(ClientInterface view, String nickname) {
        gameManager.registerPlayer(view, nickname);
    }

    public synchronized void quitGame(ClientInterface view) {
        gameManager.quitGame(view);
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
