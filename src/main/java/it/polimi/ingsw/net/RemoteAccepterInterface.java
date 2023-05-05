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
    RemoteInterface register(RemoteInterface remoteObject) throws RemoteException, IOException, ClassNotFoundException;
}
