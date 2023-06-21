package it.polimi.ingsw.net_alternative;

import it.polimi.ingsw.controller.ClientInterface;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.net_alternative.servermessages.*;

public interface ServerDispatcherInterface {
    void dispatch(CreateGameNetMessage createGameNetMessage, ClientInterface view);

    void dispatch(JoinGameNetMessage joinGameNetMessage, ClientInterface view);

    void dispatch(QuitGameNetMessage quitGameNetMessage, ClientInterface view);

    void dispatch(SentMessageNetMessage sentMessageNetMessage, ClientInterface view);

    void dispatch(DragTilesToBookShelfNetMessage dragTilesToBookShelfNetMessage, ClientInterface view);

    void dispatch(NopNetMessage nopNetMessage);

}
