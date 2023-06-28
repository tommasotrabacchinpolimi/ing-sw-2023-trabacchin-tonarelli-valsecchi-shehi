package it.polimi.ingsw.net_alternative;

import it.polimi.ingsw.controller.ClientInterface;
import it.polimi.ingsw.net_alternative.servermessages.*;
import it.polimi.ingsw.utils.Coordinate;

import java.rmi.RemoteException;

import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The `RmiServerImpl` class is an implementation of the `RmiServerInterface` interface.
 * It acts as the server-side RMI (Remote Method Invocation) implementation for handling client requests.
 * This class provides methods for performing various actions on the server, such as dragging tiles to the bookshelf, joining or creating a game, sending messages, and more.
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public class RmiServerImpl extends UnicastRemoteObject implements RmiServerInterface{

    /**
     * Used to manage correctly Server requests
     */
    private final ServerDispatcherInterface serverDispatcherInterface;
    /**
     * The client Interface
     */
    private final ClientInterface clientInterface;

    /**
     * Use to manage correctly threads
     */
    ExecutorService executorService;

    /**
     * Constructs a new `RmiServerImpl` instance with the specified server dispatcher and client interfaces.
     *
     * @param serverDispatcherInterface The server dispatcher interface responsible for dispatching server messages.
     * @param client           The client interface representing the connected client.
     * @throws RemoteException If an RMI-related error occurs during construction.
     */
    public RmiServerImpl(ServerDispatcherInterface serverDispatcherInterface, ClientInterface client) throws RemoteException{
        super();
        this.serverDispatcherInterface = serverDispatcherInterface;
        this.clientInterface = client;
        this.executorService = Executors.newSingleThreadExecutor();
    }


    /**
     * Sends a message to the server to perform the drag action on tiles in the bookshelf.
     *
     * @param chosenTiles  The coordinates of the chosen tiles to drag.
     * @param chosenColumn The column index of the bookshelf where the tiles will be dragged.
     * @throws RemoteException If an RMI-related error occurs during the method invocation.
     */
    public void dragTilesToBookShelf(List<Coordinate> chosenTiles, int chosenColumn) throws RemoteException{
        DragTilesToBookShelfNetMessage dragTilesToBookShelfNetMessage = new DragTilesToBookShelfNetMessage(chosenTiles, chosenColumn);
        executorService.submit(()->serverDispatcherInterface.dispatch(dragTilesToBookShelfNetMessage, clientInterface));
    }

    /**
     * Sends a message to the server to join a game with the specified nickname.
     *
     * @param nickname The nickname of the client joining the game.
     * @throws RemoteException If an RMI-related error occurs during the method invocation.
     */
    public void joinGame(String nickname) throws RemoteException{
        JoinGameNetMessage joinGameNetMessage = new JoinGameNetMessage(nickname);
        executorService.submit(()->serverDispatcherInterface.dispatch(joinGameNetMessage, clientInterface));

    }

    /**
     * Sends a message to the server to create a new game with the specified nickname and number of players.
     *
     * @param nickname        The nickname of the client creating the game.
     * @param numberOfPlayer  The number of players for the game.
     * @throws RemoteException If an RMI-related error occurs during the method invocation.
     */
    public void createGame(String nickname, int numberOfPlayer) throws RemoteException{
        CreateGameNetMessage createGameNetMessage = new CreateGameNetMessage(nickname, numberOfPlayer);
        executorService.submit(()->serverDispatcherInterface.dispatch(createGameNetMessage, clientInterface));

    }

    /**
     * Sends a message to the server to quit the current game.
     *
     * @throws RemoteException If an RMI-related error occurs during the method invocation.
     */
    public void quitGame() throws RemoteException{
        QuitGameNetMessage quitGameNetMessage = new QuitGameNetMessage();
        executorService.submit(()->serverDispatcherInterface.dispatch(quitGameNetMessage, clientInterface));

    }

    /**
     * Sends a message to the server to send a text message to the specified receivers.
     *
     * @param text              The text message to be sent.
     * @param receiversNickname The nicknames of the receivers of the message.
     * @throws RemoteException If an RMI-related error occurs during the method invocation.
     */
    public void sentMessage(String text, String[] receiversNickname) throws RemoteException{
        SentMessageNetMessage sentMessageNetMessage = new SentMessageNetMessage(text, receiversNickname);
        executorService.submit(()->serverDispatcherInterface.dispatch(sentMessageNetMessage, clientInterface));
    }

    /**
     * Sends a "nop" (no operation) message to the server.
     * This is used to keep the connection alive without performing any specific action.
     *
     * @throws RemoteException If an RMI-related error occurs during the method invocation.
     */
    public void nop() throws RemoteException {

    }
}
