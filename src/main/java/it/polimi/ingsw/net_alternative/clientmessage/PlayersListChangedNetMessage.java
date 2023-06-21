package it.polimi.ingsw.net_alternative.clientmessage;

import it.polimi.ingsw.net_alternative.ClientMessage;
import it.polimi.ingsw.net_alternative.ClientDispatcherInterface;

import java.util.List;

public class PlayersListChangedNetMessage implements ClientMessage {
    private List<String> players;

    public PlayersListChangedNetMessage(List<String> players) {
        this.players = players;
    }

    public List<String> getPlayers() {
        return players;
    }


    @Override
    public void dispatch(ClientDispatcherInterface clientDispatcherInterface) {
        clientDispatcherInterface.dispatch(this);
    }
}
