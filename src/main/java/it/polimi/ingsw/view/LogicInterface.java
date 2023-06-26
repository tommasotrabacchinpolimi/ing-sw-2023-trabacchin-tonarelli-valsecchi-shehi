package it.polimi.ingsw.view;

import it.polimi.ingsw.model.TileSubject;
import it.polimi.ingsw.model.TileType;
import it.polimi.ingsw.utils.Coordinate;
import it.polimi.ingsw.utils.Triple;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.List;

/**
 * The LogicInterface interface defines the methods for the game logic and interaction.
 */
public interface LogicInterface {
    /**
     * Joins an existing game with the specified nickname.
     * @param nickname The nickname of the player joining the game.
     */
    void joinGame(String nickname);

    /**
     * Creates a new game with the specified nickname and number of players.
     *
     * @param nickname       The nickname of the player creating the game.
     * @param numberOfPlayer The number of players for the game.
     */
    void createGame(String nickname, int numberOfPlayer);

    /**
     * Quits the current game.
     */
    void quitGame();

    /**
     * Sends a message to the specified receivers.
     *
     * @param text The text of the message.
     * @param receiversNickname An array of nicknames of the message receivers.
     */
    void sentMessage(String text,  String[] receiversNickname);

    /**
     * Drags the specified tiles from the board to the chosen bookshelf column.
     *
     * @param chosenTiles   The list of coordinates of the chosen tiles.
     * @param chosenColumn  The chosen bookshelf column.
     */
    void dragTilesToBookShelf(List<Coordinate> chosenTiles, int chosenColumn);

    /**
     * Connects to the game using the specified socket.
     *
     * @param port The port for the socket connection.
     * @param host The host for the socket connection.
     * @throws IOException If an I/O error occurs.
     */
    void chosenSocket(int port, String host) throws IOException;

    /**
     * Connects to the game using the specified RMI connection.
     *
     * @param port The port for the RMI connection.
     * @param host The host for the RMI connection.
     * @throws NotBoundException        If the specified name is not currently bound.
     * @throws IOException              If an I/O error occurs.
     * @throws ClassNotFoundException If the class of a serialized object cannot be found.
     */
    void chosenRMI(int port, String host) throws NotBoundException, IOException, ClassNotFoundException;

    /**
     * Reconnects to the game.
     */
    void reConnect();
}
