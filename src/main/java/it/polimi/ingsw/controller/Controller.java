package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.net.OnConnectionLostListener;
import it.polimi.ingsw.net.RemoteInterface;
import it.polimi.ingsw.net.User;

public class Controller<R extends ClientInterface> implements OnConnectionLostListener<R> {
    private State<R> state;
    private GameManager<R> gameManager;
    private ChatManager<R> chatManager;
    private LobbyController<R> lobbyController;

    public State<R> getState() {
        return state;
    }

    public void setState(State<R> state) {
        this.state = state;
    }

    public Player<R> getPlayerPlaying(){
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

    public LobbyController<R> getLobbyController() {
        return lobbyController;
    }

    public void setLobbyController(LobbyController<R> lobbyController) {
        this.lobbyController = lobbyController;
    }



    public void dragTilesToBookShelf(R view, int[] chosenTiles, int chosenColumn) {
        gameManager.dragTilesToBookShelf(view, chosenTiles, chosenColumn);
    }

    public void registerPlayer(R view, String nickname) {
        gameManager.registerPlayer(view, nickname);
    }

    public void quitGame(R view) {
    }






    @Override
    public void onConnectionLost(R user) {
        for(Player<R> p : getState().getPlayers()){
            if(p.getVirtualView() == user){
                p.setPlayerState(PlayerState.DISCONNECTED);
            }
        }
    }

}
