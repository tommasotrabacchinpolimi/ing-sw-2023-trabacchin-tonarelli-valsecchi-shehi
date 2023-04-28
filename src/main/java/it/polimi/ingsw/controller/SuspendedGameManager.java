package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerState;
import it.polimi.ingsw.utils.Coordinate;

import java.util.List;

public class SuspendedGameManager extends GameManager {
    GameState previousGameState;

    public SuspendedGameManager(Controller controller, GameState gameState) {
        super(controller);
        previousGameState = gameState;
    }

    @Override
    public synchronized void dragTilesToBookShelf(ClientInterface view, List<Coordinate> chosenTiles, int chosenColumn) {
        System.err.println("called dragTilesToBookShelf in SUSPENDED State");
    }

    @Override
    public synchronized void registerPlayer(ClientInterface view, String nickname) {
        Player player = getController().getState().getPlayerFromNick(nickname);
        if(player!=null && player.getPlayerState()==PlayerState.DISCONNECTED) {
            registerListeners(view, nickname);
            player.setVirtualView(view);
            player.setPlayerState(PlayerState.CONNECTED);
            if(checkIfNotSuspended()){
                getController().getState().setGameState(previousGameState);
                getController().setGameManager(new MidGameManager<>(getController()));
            }
        }
    }

    private boolean checkIfNotSuspended(){
        int numberPlayerConnected = (int)getController().getState().getPlayers().stream().filter(player -> player.getPlayerState()==PlayerState.CONNECTED).count();
        return numberPlayerConnected > 1;
    }

}
