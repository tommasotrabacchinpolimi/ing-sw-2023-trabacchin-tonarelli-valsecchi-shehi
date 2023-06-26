package it.polimi.ingsw.net_alternative;

import it.polimi.ingsw.controller.ClientInterface;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.net_alternative.servermessages.*;

/**
 * The ServerDispatcherInterface defines the remote methods that can be invoked by the ServerDipatcher
 */
public interface ServerDispatcherInterface {
    /**
     * Dispatches a CreateGameNetMessage to a client's view.
     *
     * @param createGameNetMessage The CreateGameNetMessage to dispatch.
     * @param view                 The ClientInterface of the recipient client.
     */
    void dispatch(CreateGameNetMessage createGameNetMessage, ClientInterface view);

    /**
     * Dispatches a JoinGameNetMessage to a client's view.
     *
     * @param joinGameNetMessage The JoinGameNetMessage to dispatch.
     * @param view               The ClientInterface of the recipient client.
     */
    void dispatch(JoinGameNetMessage joinGameNetMessage, ClientInterface view);

    /**
     * Dispatches a QuitGameNetMessage to a client's view
     *
     * @param quitGameNetMessage The QuitNetMessage to dispatch.
     * @param view The ClientInterface of the recipient client.
     */
    void dispatch(QuitGameNetMessage quitGameNetMessage, ClientInterface view);

    /**
     * Dispatches a SentMessageNetMessage to a client's view
     *
     * @param sentMessageNetMessage The SentMessageNetMessage to dispatch
     * @param view The ClientInterface of the recipient client.
     */
    void dispatch(SentMessageNetMessage sentMessageNetMessage, ClientInterface view);

    /**
     * Dispatches a DragTilesToBookshelfNetMessage to a client's view
     *
     * @param dragTilesToBookShelfNetMessage The DragTilesToBookshelfNetMessage to dispatch.
     * @param view The ClientInterface of the recipient client.
     */
    void dispatch(DragTilesToBookShelfNetMessage dragTilesToBookShelfNetMessage, ClientInterface view);

    /**
     * Dispatches a NopNetMessage to a client's view
     *
     * @param nopNetMessage The Client of the recipient client.
     */
    void dispatch(NopNetMessage nopNetMessage);

}
