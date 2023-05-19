package it.polimi.ingsw.net;

import it.polimi.ingsw.controller.ClientInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * <p>This interface defines methods that can be invoked from a non-local virtual machine, regardless that this is a
 * client or a server</p>
 *
 * @see Remote
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 * @version 1.0
 * @since 27/03/2023
 */

public interface RemoteInterface extends Remote {

    /**
     * <p>This method is used to verify that the connection between server and client is still working.</p>
     *
     * @throws RemoteException in case of connection failure
     */
    public void nop() throws RemoteException;
}
