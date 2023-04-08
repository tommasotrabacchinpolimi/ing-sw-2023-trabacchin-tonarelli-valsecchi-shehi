package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerState;

public class InitGameManager<R extends ClientInterface> extends GameManager<R>{

    public InitGameManager(Controller<R> controller) {
        super(controller);
    }

    @Override
    public void dragTilesToBookShelf(R view, int[] chosenTiles, int chosenColumn) {
        System.err.println("dragTilesToBookShelf called in the wrong state");
    }

    @Override
    public void registerPlayer(R view, String nickname) {
        getController().getState().addPlayer(new Player<>(nickname, view));
        if(getController().getState().getPlayers().size() == getController().getState().getPlayersNumber()) {
            getController().setGameManager(new MidGameManager<>(getController()));
            getController().getState().setGameState(GameState.MID);
        }
    }

    @Override
    public void quitGame(R view) {
        getController().getState().getPlayers().stream().filter(p -> p.getVirtualView() == view).findFirst().ifPresent((p)->p.setPlayerState(PlayerState.DISCONNECTED));
        getController().getLobbyController().onQuitGame(view);
    }
}
