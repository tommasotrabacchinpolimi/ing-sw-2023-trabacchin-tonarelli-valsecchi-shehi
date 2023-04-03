package it.polimi.ingsw.net;

import java.lang.reflect.InvocationHandler;

public interface UserAdapterInterface<R extends RemoteInterface> extends InvocationHandler {

    void setUser(User<R> user);
    void setTarget(Object target);
}

