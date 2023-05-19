package it.polimi.ingsw.view;

import it.polimi.ingsw.view.LogicInterface;

public abstract class UI {
    private LogicInterface logicController;

    private ViewData model;
    public void setLogicController(LogicInterface logicController) {
        this.logicController = logicController;
    }

    protected LogicInterface getLogicController() {
        return logicController;
    }

    public abstract void launch();

    public ViewData getModel() {
        return model;
    }

    public void setModel(ViewData model) {
        this.model = model;
    }

    protected abstract void onNewMessage(String sender);

    protected abstract void onCurrentPlayerChanged(String newCurrentPlayer);

    protected abstract void showWinner();

    public abstract void onException();
}
