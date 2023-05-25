package it.polimi.ingsw.view.tui;

import it.polimi.ingsw.view.UI;

import java.io.IOException;

public abstract class TUIState {

    private boolean triggered;
    private final TUIStateMachine tuiStateMachine;

    public TUIState(TUIStateMachine tuiStateMachine) {
        this.tuiStateMachine = tuiStateMachine;
        triggered = false;
    }
    protected abstract void newLine(String line) throws IOException;

    public abstract void launchUI();

    public abstract void onNewMessage(String sender);


    public abstract void onCurrentPlayerChanged(String newCurrentPlayer);

    public abstract void showWinner();

    public abstract void onException() throws IOException;
    public abstract void onGameStateChanged() throws IOException;

    public abstract void setup() throws IOException;

    public TUIStateMachine getTuiStateMachine() {
        return tuiStateMachine;
    }

    public boolean isTriggered() {
        return triggered;
    }

    public void setTriggered(boolean triggered) {
        this.triggered = triggered;
    }
}
