package it.polimi.ingsw.controller.listeners;

/**
 * A listener interface for notifying when the current player changes.
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public interface OnCurrentPlayerChangedListener {
    /**
     * Called when the current player changes.
     * @param nickname The nickname of the new current player.
     */
    void onCurrentPlayerChangedListener(String nickname);
}
