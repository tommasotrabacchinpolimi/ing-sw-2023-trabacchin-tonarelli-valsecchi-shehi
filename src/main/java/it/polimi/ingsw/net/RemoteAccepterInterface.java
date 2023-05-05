package it.polimi.ingsw.net;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
/**
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 * @version 2.0
 * @since 23/04/2023
 */

public interface RemoteAccepterInterface extends Remote {
    /**
     * Registers the given remote object and returns a proxy object to the client that allows invoking its
     * methods remotely
     *
     * @param remoteObject the remote object to be registered, extending {@linkplain RemoteInterface}
     *
     * @return <p>a {@link RemoteInterface remote proxy object} that permits the client to invoke methods</p>
     *
     * @throws RemoteException if an error occurs while registering the remote object
     * @throws IOException if an I/O error occurs
     * @throws ClassNotFoundException if the class of the remote object could not be found
     */
    RemoteInterface register(RemoteInterface remoteObject) throws RemoteException, IOException, ClassNotFoundException;
}
