package it.polimi.ingsw.net;

import java.rmi.RemoteException;

/**
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 * @version 2.0
 * @since 03/04/2023
 */
public interface TestClientInterface extends RemoteInterface{
    void check(String string) throws RemoteException;
    void nop() throws RemoteException;
}
