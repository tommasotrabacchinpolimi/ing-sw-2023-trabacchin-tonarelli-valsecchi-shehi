package it.polimi.ingsw.view.gui.customcomponents.tileview;

import javafx.animation.ParallelTransition;
import javafx.animation.Transition;
import javafx.scene.layout.Pane;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class TileViewInBoard implements TileSubjectViewState{

    @Override
    public void tileStateAction(@NotNull TileSubjectView tileSubjectView, Pane... panes) {

        Pane oldParent = tileSubjectView.getParentPane();

        Transition toNewParent = tileSubjectView.createToSmallerPathTransition(panes);

        // Start the animation
        toNewParent.playFromStart();

        toNewParent.setOnFinished( value -> {
            tileSubjectView.changeParent(Arrays.asList(panes).get((Arrays.asList(panes).size() - 1)), 0.0, 0.0);
            tileSubjectView.setCurrentState(new TileViewInBox(oldParent));
            tileSubjectView.updatedCSS(2.0);
        });
    }

    @Override
    public void reverseStateAction(TileSubjectView tileSubjectView) {
    }
}
