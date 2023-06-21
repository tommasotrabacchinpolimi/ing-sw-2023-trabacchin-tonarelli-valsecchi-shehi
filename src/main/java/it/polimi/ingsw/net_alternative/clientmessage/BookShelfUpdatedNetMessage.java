package it.polimi.ingsw.net_alternative.clientmessage;

import it.polimi.ingsw.model.TileSubject;
import it.polimi.ingsw.net_alternative.ClientMessage;
import it.polimi.ingsw.net_alternative.ClientDispatcherInterface;

public class BookShelfUpdatedNetMessage implements ClientMessage {

    private String nickname;

    private TileSubject[][] bookShelf;

    public BookShelfUpdatedNetMessage(String nickname, TileSubject[][] bookShelf) {
        this.nickname = nickname;
        this.bookShelf = bookShelf;
    }

    public String getNickname() {
        return nickname;
    }

    public TileSubject[][] getBookShelf() {
        return bookShelf;
    }

    @Override
    public void dispatch(ClientDispatcherInterface clientDispatcherInterface) {
        clientDispatcherInterface.dispatch(this);
    }
}
