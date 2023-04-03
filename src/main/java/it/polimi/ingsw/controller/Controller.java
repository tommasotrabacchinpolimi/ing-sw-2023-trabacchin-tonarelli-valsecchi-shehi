package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.CommonGoal;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.State;
import it.polimi.ingsw.net.RemoteInterface;
import it.polimi.ingsw.net.User;

public class Controller<R extends RemoteInterface> {
    private State state;
    private GameManager gameManager;
    private ChatManager chatManager;

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
        gameManager.registerPlayer(user, nickname);
    }

    public void addMessageToState() {
        //chatManager.sentMessage();
    }
}
