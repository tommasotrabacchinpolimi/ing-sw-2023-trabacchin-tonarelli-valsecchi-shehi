package it.polimi.ingsw.view.gui.customcomponents.tileview;

import it.polimi.ingsw.view.gui.customcomponents.guitoolkit.MyShelfieAlertCreator;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.Transition;
import javafx.scene.layout.Pane;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
/**
 * The `TileViewInBox` class represents the state of a tile subject view when it is placed in a box in a graphical user interface.
 * It implements the `TileSubjectViewState` interface and defines the actions to be performed on the tile subject view when it is in this state.
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public class TileViewInBox implements TileSubjectViewState {

    /**
     * Old Parent
     */
    private final Pane oldParent;

    /**
     * Constructs a `TileViewInBox` object with the specified old parent pane.
     *
     * @param oldParent The old parent pane of the tile subject view.
     */
    public TileViewInBox(@NotNull Pane oldParent) {
        this.oldParent = oldParent;
    }

    /**
     * Performs the action associated with the state on the specified tile subject view and its parent pane(s).
     *
     * @param tileSubjectView The tile subject view to perform the action on.
     * @param panes           The parent pane(s) of the tile subject view.
     */
    @Override
    public void tileStateAction(@NotNull TileSubjectView tileSubjectView, Pane... panes) {

        if (panes == null) {
            MyShelfieAlertCreator.displayErrorAlert("New Parent is null", "Tile moving failed");
            return;
        }

        Transition toNewParent = tileSubjectView.createPathTransition(panes);

        toNewParent.playFromStart();

        toNewParent.setOnFinished(value ->
                tileSubjectView.changeParent(Arrays.asList(panes).get((Arrays.asList(panes).size() - 1)), 0.0, 0.0));
    }

    /**
     * Reverses the action associated with the state on the specified tile subject view.
     *
     * @param tileSubjectView The tile subject view to reverse the action on.
     */
    @Override
    public void reverseStateAction(TileSubjectView tileSubjectView) {

        Transition toOldParent = tileSubjectView.createToBiggerPathTransition(oldParent);

        toOldParent.playFromStart();

        toOldParent.setOnFinished(value -> {
            tileSubjectView.changeParent(oldParent, 0.0, 0.0);
            tileSubjectView.setCurrentState(new TileViewInBoard());
            tileSubjectView.resetCSS();
        });
    }
}
