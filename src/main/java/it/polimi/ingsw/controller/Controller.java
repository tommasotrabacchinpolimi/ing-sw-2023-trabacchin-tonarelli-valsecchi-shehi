package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.net.OnConnectionLostListener;
import it.polimi.ingsw.utils.Coordinate;

import java.io.FileNotFoundException;
import java.util.List;

public class Controller implements OnConnectionLostListener<ClientInterface>, ControllerInterface {
    private State state;
    private GameManager gameManager;
    private ChatManager chatManager;
    private LobbyController lobbyController;

    public Controller(State state, LobbyController lobbyController) throws FileNotFoundException {
        this.state = state;
        this.lobbyController = lobbyController;
        this.gameManager = new InitGameManager(this);
        this.chatManager = new ChatManager(this);
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
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

}
