package it.polimi.ingsw.view;

/**
 * The UI abstract class represents the user interface for the game.
 *
 * @see LogicInterface
 * @see UI
 * @see ViewData
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public abstract class UI{
    /**
     * Attribute of the {@link LogicInterface controller} of the View MVC pattern.
     * @see LogicInterface
     */
    private LogicInterface logicController;
    /**
     * Attribute of the {@link ViewData model} of the View MVC pattern.
     * @see ViewData
     */
    private ViewData model;

    /**
     * Sets the {@link LogicInterface logic controller} for the {@link UI}.
     *
     * @param logicController The logic controller to set.
     * @see UI
     * @see LogicInterface
     */
    public void setLogicController(LogicInterface logicController) {
        this.logicController = logicController;
    }

    /**
     * Returns the {@link LogicInterface logic controller} associated with the {@link UI}.
     *
     * @return The logic controller.
     * @see LogicInterface
     * @see UI
     */
    public LogicInterface getLogicController() {
        return logicController;
    }

    /**
     * Launches the {@link UI}.
     * @see UI
     */
    public abstract void launchUI();

    /**
     * Returns the {@link ViewData model} associated with the {@link UI}.
     *
     * @return The model.
     * @see ViewData
     */
    public ViewData getModel() {
        return model;
    }

    /**
     * Sets the {@link ViewData model} for the {@link UI}.
     *
     * @param model The model to set.
     * @see ViewData
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
