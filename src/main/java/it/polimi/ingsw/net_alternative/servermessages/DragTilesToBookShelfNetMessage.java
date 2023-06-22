package it.polimi.ingsw.net_alternative.servermessages;

import it.polimi.ingsw.controller.ClientInterface;
import it.polimi.ingsw.net_alternative.ServerMessage;
import it.polimi.ingsw.net_alternative.ServerDispatcherInterface;
import it.polimi.ingsw.utils.Coordinate;

import java.util.List;

public class DragTilesToBookShelfNetMessage implements ServerMessage {

    private final List<Coordinate> chosenTiles;
    private final int chosenColumn;
    public DragTilesToBookShelfNetMessage(List<Coordinate> chosenTiles, int chosenColumn) {
        this.chosenTiles = chosenTiles;
        this.chosenColumn = chosenColumn;
    }

    public List<Coordinate> getChosenTiles() {
        return chosenTiles;
    }

    public int getChosenColumn() {
        return chosenColumn;
    }

    @Override
    public void dispatch(ServerDispatcherInterface serverDispatcher, ClientInterface view) {
        serverDispatcher.dispatch(this, view);
    }
}
