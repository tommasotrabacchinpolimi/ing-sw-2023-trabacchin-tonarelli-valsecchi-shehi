package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerState;

public class InitGameManager<R extends ClientInterface> {

    private Controller<R> controller;
    public void dragTilesToBookShelf(R view, int[] chosenTiles, int chosenColumn) {
        System.err.println("dragTilesToBookShelf called in the wrong state");
    }
    public void registerPlayer(R view, String nickname) {
        controller.getState().addPlayer(new Player<>(nickname, view));
        if(controller.getState().getPlayers().size() == controller.getState().getPlayersNumber()) {
            controller.getState().setGameState(GameState.MID);
            //cambio stato gamemanager
        }
    }

    public void quitGame(R view) {
        controller.getState().getPlayers().stream().filter(p -> p.getVirtualView() == view).findFirst().ifPresent((p)->p.setPlayerState(PlayerState.DISCONNECTED));
        controller.getLobbyController().onQuitGame(view);
    }
}
