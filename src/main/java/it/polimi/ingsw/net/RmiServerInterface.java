package it.polimi.ingsw.net;

import it.polimi.ingsw.utils.Coordinate;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * The RmiServerInterface interface defines the remote methods that can be invoked on an RMI server.
 * These methods are used for gameplay interactions and communication with other players.
 */
public interface RmiServerInterface extends Remote {
    /**
     * Drags the chosen tiles from the board to the bookshelf.
     *
     * @param chosenTiles   The list of coordinates representing the chosen tiles.
     * @param chosenColumn  The chosen column on the bookshelf.
     * @throws RemoteException if a remote communication error occurs.
     */
    public void dragTilesToBookShelf(List<Coordinate> chosenTiles, int chosenColumn) throws RemoteException;

    /**
     * Joins a player to the game
     *
     * @param nickname The nickname of the player
     * @throws RemoteException if a remote communication error occurs
     */
    public void joinGame(String nickname) throws RemoteException;

    /**
     * Creates a new game
     *
     * @param nickname nickname of the first player
     * @param numberOfPlayer the number of players of the game
     * @throws RemoteException if a remote communication error occurs
     */
    public void createGame(String nickname, int numberOfPlayer) throws RemoteException;

    /**
     * allows to quit the game
     *
     * @throws RemoteException if a remote communication error occurs
     */
    public void quitGame() throws RemoteException;

    /**
     * Allows to send a message from sender to receiver/s
     *
     * @param text the message to be sent
     * @param receiversNickname nicknames of receivers
     * @throws RemoteException if a remote communication error occurs
     */
    public void sentMessage(String text, String[] receiversNickname) throws RemoteException;

    /**
     * nop operation to keep the connection alive
     *
     * @throws RemoteException if a remote communication error occurs
     */
    public void nop() throws RemoteException;
}
