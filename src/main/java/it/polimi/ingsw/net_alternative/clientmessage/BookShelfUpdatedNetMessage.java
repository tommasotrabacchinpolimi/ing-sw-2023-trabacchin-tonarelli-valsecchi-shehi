package it.polimi.ingsw.net_alternative.clientmessage;

import it.polimi.ingsw.model.TileSubject;
import it.polimi.ingsw.net_alternative.ClientMessage;
import it.polimi.ingsw.net_alternative.ClientDispatcherInterface;

/**
 * Represents message for the client indicating that the Bookshelf has changed.
 * <br>
 * Extends {@link ClientMessage} Interface.
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi

 */
public class BookShelfUpdatedNetMessage implements ClientMessage {

    private final String nickname;

    private final TileSubject[][] bookShelf;

    /**
     * Constructor of the class
     * @param nickname the nickname of the user of the bookshelf
     * @param bookShelf the bookshelf being modified
     */
    public BookShelfUpdatedNetMessage(String nickname, TileSubject[][] bookShelf) {
        this.nickname = nickname;
        this.bookShelf = bookShelf;
    }

    /**
     * Getter method to have the nickname of the client
     * @return the nickname of the player
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * getter method to get the bookshelf
     * @return the bookshelf being updated
     */
    public TileSubject[][] getBookShelf() {
        return bookShelf;
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
