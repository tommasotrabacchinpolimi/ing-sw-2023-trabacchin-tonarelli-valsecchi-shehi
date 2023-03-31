package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.CommonGoal;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.State;

public class Controller {

    private State state;

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
}
