package it.polimi.ingsw.net_alternative.clientmessage;

import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.net_alternative.ClientMessage;
import it.polimi.ingsw.net_alternative.ClientDispatcherInterface;

public class StateChangedNetMessage implements ClientMessage {

    private GameState gameState;

    public StateChangedNetMessage(GameState gameState) {
        this.gameState = gameState;
    }

    public GameState getGameState() {
        return gameState;
    }


    @Override
    public void dispatch(ClientDispatcherInterface clientDispatcherInterface) {
        clientDispatcherInterface.dispatch(this);
    }
}
