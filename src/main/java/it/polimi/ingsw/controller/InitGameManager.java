package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerState;

public class InitGameManager<R extends ClientInterface> extends GameManager<R>{
    private Controller<R> controller;

    @Override
    public void dragTilesToBookShelf(R view, int[] chosenTiles, int chosenColumn) {
        System.err.println("dragTilesToBookShelf called in the wrong state");
    }

    @Override
    public void registerPlayer(R view, String nickname) {
        controller.getState().addPlayer(new Player<>(nickname, view));
        if(controller.getState().getPlayers().size() == controller.getState().getPlayersNumber()) {
            controller.setGameManager(new MidGameManager<>());
            controller.getState().setGameState(GameState.MID);
        }
    }

    @Override
    public void quitGame(R view) {
        controller.getState().getPlayers().stream().filter(p -> p.getVirtualView() == view).findFirst().ifPresent((p)->p.setPlayerState(PlayerState.DISCONNECTED));
        controller.getLobbyController().onQuitGame(view);
    }
}
