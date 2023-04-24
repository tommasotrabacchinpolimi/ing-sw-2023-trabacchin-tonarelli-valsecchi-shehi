package it.polimi.ingsw.net;

public interface UserAccepter<R extends RemoteInterface> {
    boolean acceptUser(User<R> user);
    void registerConnectionDownListener(User<R> user);
}
