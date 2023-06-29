package it.polimi.ingsw.view.gui.customcomponents.tileview;

import javafx.scene.layout.Pane;

/**
 * The `TileSubjectViewState` interface represents the state of a tile subject view in a graphical user interface.
 * It defines methods for performing actions on the tile subject view based on its state.
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public interface TileSubjectViewState {
    /**
     * Performs the action associated with the state on the specified tile subject view and its parent pane(s).
     *
     * @param tileSubjectView The tile subject view to perform the action on.
     * @param parent          The parent pane(s) of the tile subject view.
     */
    void tileStateAction(TileSubjectView tileSubjectView, Pane... parent);

    /**
     * Reverses the action associated with the state on the specified tile subject view.
     *
     * @param tileSubjectView The tile subject view to reverse the action on.
     */
    void reverseStateAction(TileSubjectView tileSubjectView);

    /**
     *
     * @return Padding of TileSubjectViewState
     */
    double getPadding();
}
