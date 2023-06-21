package it.polimi.ingsw.net_alternative.clientmessage;

import it.polimi.ingsw.model.TileSubject;
import it.polimi.ingsw.net_alternative.ClientMessage;
import it.polimi.ingsw.net_alternative.ClientDispatcherInterface;

public class BoardUpdatedNetMessage implements ClientMessage {

    private TileSubject[][] tileSubjects;

    public BoardUpdatedNetMessage(TileSubject[][] tileSubjects) {
        this.tileSubjects = tileSubjects;
    }

    public TileSubject[][] getTileSubjects() {
        return tileSubjects;
    }

    @Override
    public void dispatch(ClientDispatcherInterface clientDispatcherInterface) {
        clientDispatcherInterface.dispatch(this);
    }
}
