package net;

import java.rmi.Remote;

public interface UserAccepter<R extends RemoteInterface> {
    boolean acceptUser(User<R> user);
}
