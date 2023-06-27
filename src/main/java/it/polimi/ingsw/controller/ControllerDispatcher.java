package it.polimi.ingsw.controller;

import it.polimi.ingsw.utils.Coordinate;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The ControllerDispatcher class handles the mapping between {@link ClientInterface client interfaces} and {@linkplain Controller Controllers}.
 * It acts as a bridge between the {@linkplain LobbyController} and {@linkplain Controller Controllers}.
 * @see LobbyControllerInterface
 * @see ControllerInterface
 * @see ClientInterface
 * @see Controller
 * @see LobbyController
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public class ControllerDispatcher implements LobbyControllerInterface, ControllerInterface{
    /**
     * Map that associates each {@link ClientInterface client interfaces} to his {@linkplain Controller}.
     * @see ClientInterface
     * @see Controller
     */
    private final Map<ClientInterface, Controller> viewToControllerMap;
    /**
     * Lobby controller
     * @see LobbyController
     */
    private final LobbyController lobbyController;

    /**
     * Creates a new ControllerDispatcher with the specified LobbyController.
     * @param lobbyController The LobbyController instance.
     * @see LobbyController
     */
    public ControllerDispatcher(LobbyController lobbyController) {
        this.viewToControllerMap = new ConcurrentHashMap<>();
        this.lobbyController = lobbyController;
    }

    /**
     * {@inheritDoc}
     * @see ControllerInterface#dragTilesToBookShelf(ClientInterface, List, int)
     */
    @Override
    public void dragTilesToBookShelf(ClientInterface view, List<Coordinate> chosenTiles, int chosenColumn) {
        viewToControllerMap.get(view).dragTilesToBookShelf(view, chosenTiles, chosenColumn);
    }

    /**
     * Retrieves the map of client interfaces to controllers.
     * @return The viewToControllerMap.
     * @see #viewToControllerMap
     */
    public Map<ClientInterface, Controller> getViewToControllerMap() {
        return viewToControllerMap;
    }

    /**
     * {@inheritDoc}
     * @see ControllerInterface#quitGame(ClientInterface)
     */
    @Override
    public void quitGame(ClientInterface view) {
        lobbyController.onQuitGame(view);
    }

    /**
     * {@inheritDoc}
     * @see ControllerInterface#sentMessage(ClientInterface, String, String[])
     */
    @Override
    public void sentMessage(ClientInterface view, String text, String[] receiversNickname) {
        viewToControllerMap.get(view).sentMessage(view, text, receiversNickname);
    }

    /**
     * Handles the event of joining the game.
     * @param view instance of a {@linkplain ClientInterface client} that wants to join a game
     * @param nickname the {@linkplain it.polimi.ingsw.model.Player nickname} of the client that is trying to connect
     *                 at a match
     * @throws Exception if an error occurs while joining the game.
     * @see ClientInterface
     * @see LobbyController
     * @see LobbyControllerInterface#joinGame(ClientInterface, String)
     */
    @Override
    public void joinGame(ClientInterface view, String nickname) throws Exception {
        lobbyController.joinGame(view, nickname);
    }

    /**
     * Handles the event of creating a game.
     * @param view instance of a {@linkplain ClientInterface client} that wants to create a game
     * @param nickname the {@linkplain it.polimi.ingsw.model.Player nickname} of the client that is trying to create
     *                 a game
     * @param numberOfPlayer the max {@linkplain it.polimi.ingsw.model.State number of player} that can join the game
     *
     * @throws Exception if an error occurs while creating the game.
     * @see LobbyController
     * @see ClientInterface
     * @see LobbyControllerInterface#createGame(ClientInterface, String, int) 
     */
    @Override
    public void createGame(ClientInterface view, String nickname, int numberOfPlayer) throws Exception {
        lobbyController.createGame(view, nickname, numberOfPlayer);
    }

    /**
     * Handles a nop event.
     * @see LobbyControllerInterface#nop()
     * @see LobbyController
     */
    @Override
    public void nop() {
        lobbyController.nop();
    }

    /**
     * Sets the {@linkplain Controller} for the specified {@linkplain ClientInterface client interface}.
     * @param view The client interface.
     * @param controller The controller associated with the client interface.
     * @see Controller
     * @see ClientInterface
     */
    public void setController(ClientInterface view, Controller controller) {
        viewToControllerMap.put(view, controller);
    }

    /**
     * Removes the {@linkplain Controller} associated with the specified {@linkplain ClientInterface client interface}.
     * @param view The client interface.
     * @param controller The controller to be removed.
     * @see ClientInterface
     * @see Controller
     */
    public void removeController(ClientInterface view, Controller controller) {
        viewToControllerMap.remove(view);
    }
}
