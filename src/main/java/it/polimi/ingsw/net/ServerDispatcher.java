package it.polimi.ingsw.net;

import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.net.servermessages.*;

/**
 * The `ServerDispatcher` class is responsible for routing messages received from the server to the `ControllerDispatcher`,
 * which handles the game logic. It implements the `ServerDispatcherInterface` to provide the implementation
 * of message routing methods for different message types.
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public class ServerDispatcher implements ServerDispatcherInterface {

    /**
     * Controller Dispatcher used to correctly match clientInterface and Controllers
     */
    private final ControllerDispatcher controllerDispatcher;

    /**
     * Constructs a `ServerDispatcher` object with the specified `ControllerDispatcher`.
     *
     * @param controllerDispatcher The controller dispatcher to route messages to.
     */
    public ServerDispatcher(ControllerDispatcher controllerDispatcher) {
        this.controllerDispatcher = controllerDispatcher;
    }

    /**
     * Routes a `CreateGameNetMessage` to the appropriate method in the `ControllerDispatcher`.
     *
     * @param createGameNetMessage The create game network message.
     * @param clientInterface      The client interface associated with the message.
     */
    @Override
    public void dispatch(CreateGameNetMessage createGameNetMessage, ClientInterface clientInterface) {
        try {
            controllerDispatcher.createGame(clientInterface, createGameNetMessage.getNickname(), createGameNetMessage.getNumberOfPlayer());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Routes a `JoinGameNetMessage` to the appropriate method in the `ControllerDispatcher`.
     *
     * @param joinGameNetMessage The join game network message.
     * @param view               The client interface associated with the message.
     */
    @Override
    public void dispatch(JoinGameNetMessage joinGameNetMessage, ClientInterface view) {
        try {
            controllerDispatcher.joinGame(view, joinGameNetMessage.getNickname());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Routes a `QuitGameNetMessage` to the appropriate method in the `ControllerDispatcher`.
     *
     * @param quitGameNetMessage The quit game network message.
     * @param view               The client interface associated with the message.
     */
    @Override
    public void dispatch(QuitGameNetMessage quitGameNetMessage, ClientInterface view) {
        controllerDispatcher.quitGame(view);
    }

    /**
     * Routes a `SentMessageNetMessage` to the appropriate method in the `ControllerDispatcher`.
     *
     * @param sentMessageNetMessage The sent message network message.
     * @param view                  The client interface associated with the message.
     */
    @Override
    public void dispatch(SentMessageNetMessage sentMessageNetMessage, ClientInterface view) {
        controllerDispatcher.sentMessage(view, sentMessageNetMessage.getText(), sentMessageNetMessage.getReceiversNickname());
    }

    /**
     * Routes a `DragTilesToBookShelfNetMessage` to the appropriate method in the `ControllerDispatcher`.
     *
     * @param dragTilesToBookShelfNetMessage The drag tiles to bookshelf network message.
     * @param view                          The client interface associated with the message.
     */
    @Override
    public void dispatch(DragTilesToBookShelfNetMessage dragTilesToBookShelfNetMessage, ClientInterface view) {
        System.out.println("received drag message");
        controllerDispatcher.dragTilesToBookShelf(view, dragTilesToBookShelfNetMessage.getChosenTiles(), dragTilesToBookShelfNetMessage.getChosenColumn());
        System.out.println("executed drag message");
    }

    /**
     * Routes a `NopNetMessage` to the appropriate method in the `ControllerDispatcher`.
     *
     * @param nopNetMessage The NOP network message.
     */
    @Override
    public void dispatch(NopNetMessage nopNetMessage) {
        controllerDispatcher.nop();
    }

}
