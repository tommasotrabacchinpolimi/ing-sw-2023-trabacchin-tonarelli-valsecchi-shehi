package it.polimi.ingsw.net;


import java.rmi.Remote;

/**
 * <p>Represent a potential player over a connection</p>
 *
 * @see RemoteInterface
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 * @version 2.0
 * @since 01/04/2023
 */
public class User<R extends RemoteInterface> {

    /**
     * <p>The "connection director" for this potential player over the connection established to the server</p>
     *
     * @see ConnectionManager
     */
    private ConnectionManager<? extends RemoteInterface, R> connectionManager;

    /**
     * <p>Set the {@linkplain #connectionManager connection director} for the connection</p>
     *
     * @param connectionManager connection director that has to manage connectivity
     */
    public void setConnectionManager(ConnectionManager<? extends RemoteInterface,R> connectionManager){
        this.connectionManager = connectionManager;
    }

    /**
     * <p>Retrieve the {@linkplain #connectionManager connection director} assigned to this potential player</p>
     *
     * @return the {@linkplain #connectionManager connection manager}
     */
    public ConnectionManager<? extends Remote,R> getConnectionManager(){
        return connectionManager;
    }

}
