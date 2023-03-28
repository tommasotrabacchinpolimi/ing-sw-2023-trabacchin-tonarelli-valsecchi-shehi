package it.polimi.ingsw.net;

public interface NetworkMonitor<R extends RemoteInterface> {
    public void connectionDown(User<R> user);
}
