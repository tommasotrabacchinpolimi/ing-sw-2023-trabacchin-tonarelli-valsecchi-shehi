package it.polimi.ingsw.net;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 * @version 1.0
 * @since 27/03/2023
 */

public interface RemoteInterface extends Remote {
    public void nop() throws RemoteException;
}
