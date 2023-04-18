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
    public synchronized void registerPlayer(R view, String nickname) {
        Player<R> player = getController().getState().getPlayerFromNick(nickname);
        if(player!=null && player.getPlayerState()==PlayerState.DISCONNECTED) {
            registerListeners(view, nickname);
            player.setVirtualView(view);
            player.setPlayerState(PlayerState.CONNECTED);
        }
    }

}
