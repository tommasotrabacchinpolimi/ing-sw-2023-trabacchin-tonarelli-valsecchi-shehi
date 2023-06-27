package it.polimi.ingsw.view.gui.customcomponents.tileview;

import javafx.animation.ParallelTransition;
import javafx.animation.Transition;
import javafx.scene.layout.Pane;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 * The `TileViewInBoard` class represents the state of a tile subject view when it is placed on the board in a graphical user interface.
 * It implements the `TileSubjectViewState` interface and defines the actions to be performed on the tile subject view when it is in this state.
 */
public class TileViewInBoard implements TileSubjectViewState {

    /**
     * Performs the action associated with the state on the specified tile subject view and its parent pane(s).
     *
     * @param tileSubjectView The tile subject view to perform the action on.
     * @param panes           The parent pane(s) of the tile subject view.
     */
    @Override
    public void tileStateAction(@NotNull TileSubjectView tileSubjectView, Pane... panes) {

        Pane oldParent = tileSubjectView.getParentPane();

        Transition toNewParent = tileSubjectView.createToSmallerPathTransition(panes);

        // Start the animation
        toNewParent.playFromStart();

        toNewParent.setOnFinished(value -> {
            tileSubjectView.changeParent(Arrays.asList(panes).get((Arrays.asList(panes).size() - 1)), 0.0, 0.0);
            tileSubjectView.setCurrentState(new TileViewInBox(oldParent));
            tileSubjectView.updatedCSS(2.0);
        });
    }

    /**
     * Reverses the action associated with the state on the specified tile subject view.
     *
     * @param tileSubjectView The tile subject view to reverse the action on.
     */
    @Override
    public void reverseStateAction(TileSubjectView tileSubjectView) {
        // No action is performed to reverse the state in this implementation
    }
}
