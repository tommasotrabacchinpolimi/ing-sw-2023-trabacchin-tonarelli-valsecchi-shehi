package it.polimi.ingsw.net;

public interface OnConnectionLostListener<R extends RemoteInterface> {
    public void onConnectionLost(User<R> user);
}
