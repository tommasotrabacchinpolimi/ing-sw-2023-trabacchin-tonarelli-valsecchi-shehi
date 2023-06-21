package it.polimi.ingsw.net_alternative.servermessages;

import it.polimi.ingsw.controller.ClientInterface;
import it.polimi.ingsw.net_alternative.ServerMessage;
import it.polimi.ingsw.net_alternative.ServerDispatcherInterface;

public class QuitGameNetMessage implements ServerMessage {

    @Override
    public void dispatch(ServerDispatcherInterface serverDispatcher, ClientInterface view) {
        serverDispatcher.dispatch(this, view);
    }
}
