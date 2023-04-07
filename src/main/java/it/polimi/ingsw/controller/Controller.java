package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.CommonGoal;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerState;
import it.polimi.ingsw.model.State;
import it.polimi.ingsw.net.OnConnectionLostListener;
import it.polimi.ingsw.net.RemoteInterface;
import it.polimi.ingsw.net.User;

public class Controller<R extends RemoteInterface> implements OnConnectionLostListener<R> {
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

    public void registerPlayer(R user, String nickname){
        gameManager.registerPlayer(user, nickname);
    }

    public void addMessageToState() {
        //chatManager.sentMessage();
    }

    public void setNumberPlayers(int numberOfPlayer) {
        getState().setPlayersNumber(numberOfPlayer);
    }


    public void quitGame(R user){
        lobbyController.onQuitGame(user);
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
