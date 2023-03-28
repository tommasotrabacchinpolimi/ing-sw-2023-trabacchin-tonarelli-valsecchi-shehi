package it.polimi.ingsw.net;

import java.rmi.RemoteException;

public interface TestClientInterface extends ClientInterface{
    void check(String string) throws RemoteException;
    void nop() throws RemoteException;
}
