package net;

import java.rmi.RemoteException;

public interface TestServerInterface<R extends ClientInterface> extends ServerInterface<R> {
    void check(String string) throws RemoteException;
    void nop() throws RemoteException;
}
