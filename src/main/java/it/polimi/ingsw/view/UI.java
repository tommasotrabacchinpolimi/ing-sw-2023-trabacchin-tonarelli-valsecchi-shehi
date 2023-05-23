package it.polimi.ingsw.view;

public abstract class UI {
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

    protected abstract void onNewMessage(String sender);

    protected abstract void onCurrentPlayerChanged(String newCurrentPlayer);

    protected abstract void showWinner();

    public abstract void onException();

    public abstract void onGameStateChanged();
}
