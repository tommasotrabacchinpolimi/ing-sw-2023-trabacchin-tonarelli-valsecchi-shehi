package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerState;

public class SuspendedGameManager<R extends ClientInterface> extends GameManager<R>{

    public SuspendedGameManager(Controller<R> controller) {
        super(controller);
    }

    @Override
    public synchronized void dragTilesToBookShelf(R view, int[] chosenTiles, int chosenColumn) {
        System.err.println("called dragTilesToBookShelf in SUSPENDED State");
    }

    @Override
    public void registerPlayer(R view, String nickname) {
        getController().getState().addPlayer(new Player<>(nickname, view));
        getController().getState().setCurrentPlayer(getController().getState().getPlayerFromView(view));
        getController().setGameManager(new MidGameManager<>(getController()));
        getController().getState().setGameState(GameState.MID);
    }

    @Override
    public synchronized void quitGame(R view) {
        getController().getState().getPlayers().stream().filter(p -> p.getVirtualView() == view).findFirst().ifPresent((p)->p.setPlayerState(PlayerState.DISCONNECTED));
        getController().getLobbyController().onQuitGame(view);
    }
}
