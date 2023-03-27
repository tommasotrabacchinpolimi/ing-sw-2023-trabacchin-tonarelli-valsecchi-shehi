package net;

import java.lang.reflect.InvocationHandler;

public interface UserAdapterInterface<R extends ClientInterface> extends InvocationHandler {

    void setUser(User<R> user);
    void setTarget(Object target);
}

