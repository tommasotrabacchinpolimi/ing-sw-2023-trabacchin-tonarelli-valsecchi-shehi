package it.polimi.ingsw.net;

/**
 *
 * @param <R>
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 * @version 1.0, 27/04/2023
 */
public interface OnConnectionLostListener<R extends RemoteInterface> {
    /**
     * Defines the action to be performed when a {@linkplain it.polimi.ingsw.controller.ClientInterface client}
     * lost the connection to the server.
     *
     * @param user the user which has lost the connection
     */
    public void onConnectionLost(R user);
}
