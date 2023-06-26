package it.polimi.ingsw.view;

import java.io.IOException;

public abstract class UI{
    private LogicInterface logicController;

    private ViewData model;

    public void setLogicController(LogicInterface logicController) {
        this.logicController = logicController;
    }


    public LogicInterface getLogicController() {
        return logicController;
    }

    public abstract void launchUI();

    public ViewData getModel() {
        return model;
    }

    public void setModel(ViewData model) {
        this.model = model;
    }

    public abstract void onNewMessage(String sender);

    public abstract void onCurrentPlayerChanged(String newCurrentPlayer);

    public abstract void showWinner();

    public abstract void onException();

    public abstract void onGameStateChanged();

    public abstract void onNewMessages();

    public abstract void onConnectionLost();

}
