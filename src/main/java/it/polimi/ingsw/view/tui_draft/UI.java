package it.polimi.ingsw.view.tui_draft;

public abstract class UI {
    private LogicInterface logicController;
    public void setLogicController(LogicInterface logicController) {
        this.logicController = logicController;
    }

    protected LogicInterface getLogicController() {
        return logicController;
    }

    public abstract void launch();
}
