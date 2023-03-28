package controller;

import model.State;

public class Controller {
    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    private State state;


}
