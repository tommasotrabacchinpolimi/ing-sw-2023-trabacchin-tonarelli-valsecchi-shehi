package net;

import java.rmi.Remote;

public interface NetworkMonitor<R extends RemoteInterface> {
    public void connectionDown(User<R> user);
}
