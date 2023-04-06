package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.CommonGoal;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.State;
import it.polimi.ingsw.net.OnConnectionLostListener;
import it.polimi.ingsw.net.RemoteInterface;
import it.polimi.ingsw.net.User;

public class Controller<R extends RemoteInterface> implements OnConnectionLostListener<R> {
    private State state;
    private GameManager<R> gameManager;
    private ChatManager<R> chatManager;

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

    public void registerPlayer(User<R> user, String nickname){
        if(this.state == null){
            State state = new State();
            this.state = state;
        }
        gameManager.registerPlayer(user, nickname);
    }

    public void addMessageToState() {
        //chatManager.sentMessage();
    }

    public void setNumberPlayers(int numberOfPlayer) {
        getState().setPlayersNumber(numberOfPlayer);
    }

    @Override
    public void onConnectionLost(User<R> user) {
        for(Player p : getState().getPlayers()){
            if(p.getNickName().equals(user.getNickname())){
                p.setVirtualView(null);
            }
        }
    }

    public void quitGame(User<R> user){
        //notifica la lobby controller di toglierlo dalla mappa
        // togliamo la virtual view dal player
    }

}
