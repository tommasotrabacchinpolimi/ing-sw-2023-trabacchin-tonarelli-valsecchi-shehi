package it.polimi.ingsw.net;

import java.lang.reflect.InvocationHandler;

/**
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 * @version 2.0
 * @since 03/04/2023
 */

public interface UserAdapterInterface<R extends RemoteInterface> extends InvocationHandler {

    void setUser(User<R> user);
    void setTarget(Object target);
}

