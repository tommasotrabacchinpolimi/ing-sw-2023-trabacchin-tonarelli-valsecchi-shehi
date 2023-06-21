package it.polimi.ingsw.net_alternative.clientmessage;

import it.polimi.ingsw.net_alternative.ClientMessage;
import it.polimi.ingsw.net_alternative.ClientDispatcherInterface;
import it.polimi.ingsw.utils.Coordinate;

import java.util.List;

public class AdjacentTilesUpdatedNetMessage implements ClientMessage {
    private String nickname;

    private List<Coordinate> tiles;

    public AdjacentTilesUpdatedNetMessage(String nickname, List<Coordinate> tiles) {
        this.nickname = nickname;
        this.tiles = tiles;
    }

    public String getNickname() {
        return nickname;
    }

    public List<Coordinate> getTiles() {
        return tiles;
    }


    @Override
    public void dispatch(ClientDispatcherInterface clientDispatcherInterface) {
        clientDispatcherInterface.dispatch(this);
    }
}
