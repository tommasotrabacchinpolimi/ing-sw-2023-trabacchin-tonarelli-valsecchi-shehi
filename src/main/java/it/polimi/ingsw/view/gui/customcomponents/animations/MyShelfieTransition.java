package it.polimi.ingsw.view.gui.customcomponents.animations;

import javafx.animation.Transition;

/**
 * Common interface for all "My Shelfie" style
 * transition that defines basic common operation
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public interface MyShelfieTransition {

    /**
     * Retrieves the "My Shelfie" style transition
     * associated with the class
     *
     * @return the custom "My Shelfie" transition
     */
    Transition getTransition();
}
