package it.polimi.ingsw.view.tui;

import java.io.IOException;

public class TUIStateWaiting extends TUIState{


    public TUIStateWaiting(TUIStateMachine tuiStateMachine) {
        super(tuiStateMachine);
    }
    @Override
    protected void newLine(String line) throws IOException {
        if(line.equals("quit") && !isTriggered()) {
            setTriggered(true);
            getTuiStateMachine().getLogicController().quitGame();
            getTuiStateMachine().setTuiState(new TUIStateInit(getTuiStateMachine()));
            getTuiStateMachine().getTuiState().setup();
        }
    }

    @Override
    public void launchUI() {

    }

    @Override
    public void onNewMessage(String sender) {

    }

    @Override
    public void onCurrentPlayerChanged(String newCurrentPlayer) {

    }

    @Override
    public void showWinner() {

    }

    @Override
    public void onException() throws IOException {
        if(!isTriggered()) {
            System.out.println(getTuiStateMachine().getModel().getException());
            System.out.println("Redirecting to setup page...");
            setTriggered(true);
            getTuiStateMachine().setTuiState(new TUIStateInit(getTuiStateMachine()));
            getTuiStateMachine().getTuiState().setup();
        }
    }

    @Override
    public void onGameStateChanged() throws IOException {
        if(!isTriggered()) {
            if(getTuiStateMachine().getModel().getGameState().equals("INIT")) {
                setTriggered(true);
                getTuiStateMachine().setTuiState(new TUIStateGame(getTuiStateMachine()));
                new Thread(() -> {
                    try {
                        getTuiStateMachine().getTuiState().setup();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }).start();

            }
        }

    }

    @Override
    public void setup() throws IOException {
        System.out.println("Please wait until a game is available");
    }
}
