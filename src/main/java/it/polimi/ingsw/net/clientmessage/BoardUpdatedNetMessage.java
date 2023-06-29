package it.polimi.ingsw.net.clientmessage;

import it.polimi.ingsw.model.TileSubject;
import it.polimi.ingsw.net.ClientMessage;
import it.polimi.ingsw.net.ClientDispatcherInterface;

/**
 * Class used to allow the client to be notified about the change of board.
 * <br>
 * It extends {@link ClientMessage} Interface.
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi

 */
public class BoardUpdatedNetMessage implements ClientMessage {


    /**
     * Matrix of tileSubjects that represents the board
     */
    private final TileSubject[][] tileSubjects;

    /**
     * Constructor of the class
     * @param tileSubjects Represents the bord updated
     */
    public BoardUpdatedNetMessage(TileSubject[][] tileSubjects) {
        this.tileSubjects = tileSubjects;
    }


    /**
     * getter method to get the updated board
     * @return the Board updated
     */
    public TileSubject[][] getTileSubjects() {
        return tileSubjects;
    }

    /**
     * It dispatches the message to the client
     * @param clientDispatcherInterface is the handler of the message.
     */
    @Override
    public void dispatch(ClientDispatcherInterface clientDispatcherInterface) {
        clientDispatcherInterface.dispatch(this);
    }
}
