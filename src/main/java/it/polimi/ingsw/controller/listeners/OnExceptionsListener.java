package it.polimi.ingsw.controller.listeners;

import java.io.IOException;

/**
 * A listener interface for notifying when an exception occurs.
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public interface OnExceptionsListener {
    /**
     * Called when an exception occurs.
     * @param e The exception that occurred.
     */
    public void onException(String player, Exception e);
}
