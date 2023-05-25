package it.polimi.ingsw.view.tui;

import it.polimi.ingsw.view.UI;
import it.polimi.ingsw.view.ViewData;

import java.io.IOException;

public class TUIStateMachine extends UI {

    private TUIState tuiState;

    public TUIStateMachine() {
        tuiState = new TUIStateInit(this);
    }

    public void newLine(String newLine) throws IOException {
        tuiState.newLine(newLine);
    }
    public void setup() throws IOException {
        tuiState.setup();
    }
    @Override
    public void launchUI() {

    }

    @Override
    public void onNewMessage(String sender) {
        tuiState.onNewMessage(sender);
    }

    @Override
    public void onCurrentPlayerChanged(String newCurrentPlayer) {
        tuiState.onCurrentPlayerChanged(newCurrentPlayer);
    }

    @Override
    public void showWinner() {
        tuiState.showWinner();
    }

    @Override
    public void onException() throws IOException {
        tuiState.onException();
    }

    @Override
    public void onGameStateChanged() throws IOException {

        tuiState.onGameStateChanged();
    }

    public TUIState getTuiState() {
        return tuiState;
    }

    public void setTuiState(TUIState tuiState) {
        this.tuiState = tuiState;
    }
}
