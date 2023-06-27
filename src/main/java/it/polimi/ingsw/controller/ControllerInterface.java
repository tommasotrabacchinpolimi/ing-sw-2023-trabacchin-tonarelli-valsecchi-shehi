package it.polimi.ingsw.controller;

import it.polimi.ingsw.utils.Coordinate;

import java.util.List;

/**
 * The ControllerInterface defines the methods that a game controller should implement.
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 * @see Controller
 * @see GameManager
 */
public interface ControllerInterface {
    /**
     * Handles the event of dragging tiles to the bookshelf.
     * @param view The {@linkplain ClientInterface client interface} associated with the event.
     * @param chosenTiles The list of chosen tiles to be dragged.
     * @param chosenColumn The chosen column on the bookshelf.
    */
    void dragTilesToBookShelf(ClientInterface view, List<Coordinate> chosenTiles, int chosenColumn);

    /**
     * Handles the event of quitting the game.
     * @param view The {@linkplain ClientInterface client interface} associated with the event.
     */
    void quitGame(ClientInterface view);

    /**
     * Handles the event of sending a message.
     * @param view The @linkplain ClientInterface client interface} associated with the event.
     * @param text The message text.
     * @param receiversNickname The nicknames of the message receivers.
     */
    void sentMessage(ClientInterface view, String text,  String[] receiversNickname);
}
