package it.polimi.ingsw.net;

import java.rmi.RemoteException;

public interface TestServerInterface extends RemoteInterface {
    void check(String string) throws RemoteException;
    void nop() throws RemoteException;
}
