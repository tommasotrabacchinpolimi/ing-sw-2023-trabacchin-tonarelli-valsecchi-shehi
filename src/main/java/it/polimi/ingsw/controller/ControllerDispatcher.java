package it.polimi.ingsw.controller;

import it.polimi.ingsw.utils.Coordinate;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ControllerDispatcher implements LobbyControllerInterface, ControllerInterface{

    private final Map<ClientInterface, Controller> viewToControllerMap;

    private final LobbyController lobbyController;

    public ControllerDispatcher(LobbyController lobbyController) {
        this.viewToControllerMap = new ConcurrentHashMap<>();
        this.lobbyController = lobbyController;
    }

    @Override
    public void dragTilesToBookShelf(ClientInterface view, List<Coordinate> chosenTiles, int chosenColumn) {
        viewToControllerMap.get(view).dragTilesToBookShelf(view, chosenTiles, chosenColumn);
    }

    @Override
    public void quitGame(ClientInterface view) {
        lobbyController.onQuitGame(view);
    }

    @Override
    public void sentMessage(ClientInterface view, String text, String[] receiversNickname) {
        viewToControllerMap.get(view).sentMessage(view, text, receiversNickname);
    }

    @Override
    public void joinGame(ClientInterface view, String nickname) throws Exception {
        lobbyController.joinGame(view, nickname);
    }

    @Override
    public void createGame(ClientInterface view, String nickname, int numberOfPlayer) throws Exception {
        lobbyController.createGame(view, nickname, numberOfPlayer);
    }

    @Override
    public void nop() {
        lobbyController.nop();
    }

    public void setController(ClientInterface view, Controller controller) {
        viewToControllerMap.put(view, controller);
    }

    public void removeController(ClientInterface view, Controller controller) {
        viewToControllerMap.remove(view);
    }
}
