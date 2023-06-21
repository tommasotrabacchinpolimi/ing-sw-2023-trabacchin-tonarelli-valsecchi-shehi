package it.polimi.ingsw.controller;

import it.polimi.ingsw.net.RemoteInterface;
import it.polimi.ingsw.utils.Coordinate;

import java.util.List;

/**
 * <p>Defines how a server is exposed on the network</p>
 * <p>More precisely inside this class are stored and displayed all actions allowed by a server that a client can
 * perform.</p>
 *
 * @apiNote Actions allowed by this class can be performed without caring about the type of connection used
 * (rmi or socket)
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 * @version 2.0
 * @since 27/04/2023
 */
public interface ServerInterface {

    /**
     * Defines the action of dragging the tiles from the board to the bookshelf
     *
     * @param chosenTiles the tiles drawn by the livingroom board
     * @param chosenColumn the column in which the tiles will be putted
     */
    void dragTilesToBookShelf(List<Coordinate> chosenTiles, int chosenColumn);

    /**
     * Defines the action to do when a {@linkplain it.polimi.ingsw.model.Player player} (identified by his nickname)
     * try to join a match
     *
     * @param nickname the nickname of the player that is trying to join the game
     *
     * @see it.polimi.ingsw.model.Player
     */
    void joinGame(String nickname);

    /**
     * Defines the action to do when a {@linkplain it.polimi.ingsw.model.Player player} (identified by his nickname)
     * wants to create a match
     *
     * @param nickname the nickname of the player that wats to create a match
     * @param numberOfPlayer the number of players that can join the match that the player wants to create
     *
     * @see it.polimi.ingsw.model.Player
     */
    void createGame(String nickname, int numberOfPlayer);

    /**
     * Defines the action to do when a {@linkplain it.polimi.ingsw.model.Player player} wants to disconnect from
     * the server
     *
     * @see it.polimi.ingsw.model.Player
     */
    void quitGame();

    /**
     * Defines the action to do when a {@linkplain it.polimi.ingsw.model.Player player} (identified by his nickname)
     * wants to send a {@linkplain it.polimi.ingsw.model.ChatMessage message} to the other players (one or more).
     *
     * @param text
     * @param receiversNickname
     *
     * @see it.polimi.ingsw.model.Player
     * @see it.polimi.ingsw.model.ChatMessage
     * @see it.polimi.ingsw.model.State
     */
    void sentMessage(String text, String[] receiversNickname);


    void nop();
}
