package it.polimi.ingsw.net;


import java.rmi.Remote;

/**
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 * @version 2.0
 * @since 01/04/2023
 */
public class User<R extends RemoteInterface> {
    private ConnectionManager<? extends RemoteInterface, R> connectionManager;

    public void setConnectionManager(ConnectionManager<? extends RemoteInterface,R> connectionManager){
        this.connectionManager = connectionManager;
    }

    public ConnectionManager<? extends Remote,R> getConnectionManager(){
        return connectionManager;
    }

}
