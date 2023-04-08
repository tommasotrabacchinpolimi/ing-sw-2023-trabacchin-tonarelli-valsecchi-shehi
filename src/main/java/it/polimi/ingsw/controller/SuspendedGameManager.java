package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerState;

public class SuspendedGameManager<R extends ClientInterface> extends GameManager<R>{
    Controller<R> controller;
    @Override
    public synchronized void dragTilesToBookShelf(R view, int[] chosenTiles, int chosenColumn) {
        System.err.println("called dragTilesToBookShelf in SUSPENDED State");
    }

    @Override
    public void registerPlayer(R view, String nickname) {
        controller.getState().addPlayer(new Player<>(nickname, view));
        controller.getState().setCurrentPlayer(controller.getState().getPlayerFromView(view));
        controller.setGameManager(new MidGameManager<>());
        controller.getState().setGameState(GameState.MID);
    }

    @Override
    public synchronized void quitGame(R view) {
        controller.getState().getPlayers().stream().filter(p -> p.getVirtualView() == view).findFirst().ifPresent((p)->p.setPlayerState(PlayerState.DISCONNECTED));
        controller.getLobbyController().onQuitGame(view);
    }
}
