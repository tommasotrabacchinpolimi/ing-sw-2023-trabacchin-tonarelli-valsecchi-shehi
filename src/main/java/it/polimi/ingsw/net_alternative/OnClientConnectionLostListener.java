package it.polimi.ingsw.net_alternative;

import it.polimi.ingsw.controller.ClientInterface;

/**
 * The {@code OnClientConnectionLostListener} interface defines a callback method for notifying when a client connection is lost.
 *
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public interface OnClientConnectionLostListener {
    /**
     * Called when a client connection is lost.
     * Implementations of this method should handle any necessary actions when the client connection is lost.
     */
    void onConnectionLost();
}
