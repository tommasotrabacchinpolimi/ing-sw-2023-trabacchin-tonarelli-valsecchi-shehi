package it.polimi.ingsw.net_alternative;

import it.polimi.ingsw.controller.ServerInterface;
import it.polimi.ingsw.utils.Coordinate;

import java.rmi.RemoteException;
import java.util.List;


/**
 * The `ClientRmiAdapter` class is an adapter that facilitates communication between the client and the server
 * using the RMI (Remote Method Invocation) protocol. It implements the `ServerInterface` interface.
 *
 * This adapter allows the client to invoke methods on the server by translating them into corresponding
 * method calls on the `rmiServer` instance, which is of type `RmiServerInterface`. If a `RemoteException`
 * occurs during the method invocation, the `onConnectionLost` method of the `onClientConnectionLostListener`
 * object is called to handle the connection loss event.
 *
 * Note that the `ClientRmiAdapter` class assumes that the `rmiServer` instance is already initialized and
 * connected to the RMI server.
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public class ClientRmiAdapter implements ServerInterface {
    private final RmiServerInterface rmiServer;
    private final OnClientConnectionLostListener onClientConnectionLostListener;

    /**
     * Constructs a new instance of the `ClientRmiAdapter` class with the specified RMI server interface and
     * connection lost listener.
     *
     * @param rmiServer                      The RMI server interface.
     * @param onClientConnectionLostListener The listener for client connection lost events.
     */
    public ClientRmiAdapter(RmiServerInterface rmiServer, OnClientConnectionLostListener onClientConnectionLostListener) {
        this.rmiServer = rmiServer;
        this.onClientConnectionLostListener = onClientConnectionLostListener;
    }

    /**
     * Sends a message to the server to drag tiles to the bookshelf.
     *
     * @param chosenTiles   The list of coordinates representing the chosen tiles.
     * @param chosenColumn  The chosen column index.
     */
    @Override
    public void dragTilesToBookShelf(List<Coordinate> chosenTiles, int chosenColumn) {
        try {
            rmiServer.dragTilesToBookShelf(chosenTiles, chosenColumn);
        } catch (RemoteException e) {
            onClientConnectionLostListener.onConnectionLost();
        }
    }

    /**
     * Sends a message to the server to join a game with the specified nickname.
     *
     * @param nickname The nickname of the player.
     */
    @Override
    public void joinGame(String nickname) {
        try {
            rmiServer.joinGame(nickname);
        } catch (RemoteException e) {
            onClientConnectionLostListener.onConnectionLost();
        }
    }

    /**
     * Sends a message to the server to create a game with the specified nickname and number of players.
     *
     * @param nickname       The nickname of the player creating the game.
     * @param numberOfPlayer The number of players in the game.
     */
    @Override
    public void createGame(String nickname, int numberOfPlayer) {
        try {
            rmiServer.createGame(nickname, numberOfPlayer);
        } catch (RemoteException e) {
            onClientConnectionLostListener.onConnectionLost();
        }
    }

    /**
     * Sends a message to the server to quit the game.
     */
    @Override
    public void quitGame() {
        try {
            rmiServer.quitGame();
        } catch (RemoteException e) {
            onClientConnectionLostListener.onConnectionLost();
        }
    }

    /**
     * Sends a message to the server to send a text message to specific receivers.
     *
     * @param text              The text message to be sent.
     * @param receiversNickname The nicknames of the message receivers.
     */
    @Override
    public void sentMessage(String text, String[] receiversNickname) {
        try {
            rmiServer.sentMessage(text, receiversNickname);
        } catch (RemoteException e) {
            onClientConnectionLostListener.onConnectionLost();
        }
    }

    /**
     * Sends a "nop" (no operation) message to the server.
     */
    @Override
    public void nop() {
        try {
            rmiServer.nop();
        } catch (RemoteException e) {
            onClientConnectionLostListener.onConnectionLost();
        }
    }
}
