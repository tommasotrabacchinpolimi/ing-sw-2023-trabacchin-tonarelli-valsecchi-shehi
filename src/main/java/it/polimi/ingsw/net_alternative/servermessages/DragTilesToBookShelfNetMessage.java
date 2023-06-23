package it.polimi.ingsw.net_alternative.servermessages;

import it.polimi.ingsw.controller.ClientInterface;
import it.polimi.ingsw.net_alternative.ServerMessage;
import it.polimi.ingsw.net_alternative.ServerDispatcherInterface;
import it.polimi.ingsw.utils.Coordinate;

import java.util.List;

/**
 * Represents a server message for dragging tiles to the bookshelf.
 * <br>
 * Implements {@link ServerMessage} interface.
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi

 */
public class DragTilesToBookShelfNetMessage implements ServerMessage {

    private final List<Coordinate> chosenTiles;
    private final int chosenColumn;

    /**
     * Constructs an instance of DragTilesToBookShelfNetMessage.
     *
     * @param chosenTiles   the list of coordinates representing the chosen tiles
     * @param chosenColumn  the chosen column index on the bookshelf
     */
    public DragTilesToBookShelfNetMessage(List<Coordinate> chosenTiles, int chosenColumn) {
        this.chosenTiles = chosenTiles;
        this.chosenColumn = chosenColumn;
    }

    /**
     * Returns the list of coordinates representing the chosen tiles.
     *
     * @return the list of chosen tile coordinates
     */
    public List<Coordinate> getChosenTiles() {
        return chosenTiles;
    }

    /**
     * Returns the chosen column index on the bookshelf.
     *
     * @return the chosen column index
     */
    public int getChosenColumn() {
        return chosenColumn;
    }

    /**
     * Dispatches the message to the server dispatcher for handling.
     *
     * @param serverDispatcher the server dispatcher interface
     * @param view             the client interface
     */
    @Override
    public void dispatch(ServerDispatcherInterface serverDispatcher, ClientInterface view) {
        serverDispatcher.dispatch(this, view);
    }
}
