package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerState;
import it.polimi.ingsw.utils.Coordinate;

public class SuspendedGameManager extends GameManager {

    public SuspendedGameManager(Controller controller) {
        super(controller);
    }

    @Override
    public synchronized void dragTilesToBookShelf(ClientInterface view, Coordinate[] chosenTiles, int chosenColumn) {
        System.err.println("called dragTilesToBookShelf in SUSPENDED State");
    }

    @Override
    public synchronized void registerPlayer(ClientInterface view, String nickname) {
        Player player = getController().getState().getPlayerFromNick(nickname);
        if(player!=null && player.getPlayerState()==PlayerState.DISCONNECTED) {
            registerListeners(view, nickname);
            player.setVirtualView(view);
            player.setPlayerState(PlayerState.CONNECTED);
        }
    }

}
