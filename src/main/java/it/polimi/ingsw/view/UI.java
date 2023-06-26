package it.polimi.ingsw.view;

/**
 * The UI abstract class represents the user interface for the game.
 */
public abstract class UI{
    /**
     * Attribute of the controller of the View MVC pattern.
     */
    private LogicInterface logicController;
    /**
     * Attribute of the model of the View MVC pattern.
     */
    private ViewData model;

    /**
     * Sets the logic controller for the UI.
     *
     * @param logicController The logic controller to set.
     */
    public void setLogicController(LogicInterface logicController) {
        this.logicController = logicController;
    }

    /**
     * Returns the logic controller associated with the UI.
     *
     * @return The logic controller.
     */
    public LogicInterface getLogicController() {
        return logicController;
    }

    /**
     * Launches the UI.
     */
    public abstract void launchUI();

    /**
     * Returns the model associated with the UI.
     *
     * @return The model.
     */
    public ViewData getModel() {
        return model;
    }

    /**
     * Sets the model for the UI.
     *
     * @param model The model to set.
     */
    public void setModel(ViewData model) {
        this.model = model;
    }

    /**
     * Event triggered when a new message is received.
     * @param sender The sender of the message.
     */
    public abstract void onNewMessage(String sender);

    /**
     * Event triggered when the current player changes.
     * @param newCurrentPlayer The new current player.
     */
    public abstract void onCurrentPlayerChanged(String newCurrentPlayer);

    /**
     * Displays the winner of the game.
     */
    public abstract void showWinner();

    /**
     * Event triggered when an exception occurs.
     */
    public abstract void onException();

    /**
     * Event triggered when the game state changes.
     */
    public abstract void onGameStateChanged();

    /**
     * Event triggered when new messages are available.
     */
    public abstract void onNewMessages();

    /**
     * Event triggered when the connection to the game is lost.
     */
    public abstract void onConnectionLost();

}
