package it.polimi.ingsw.view;

import it.polimi.ingsw.view.LogicInterface;

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
