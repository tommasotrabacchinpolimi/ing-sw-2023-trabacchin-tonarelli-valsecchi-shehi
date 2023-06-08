package it.polimi.ingsw.view.gui.customcomponents.tileview;

import javafx.animation.PathTransition;
import javafx.scene.layout.Pane;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class TileViewInBoard implements TileSubjectViewState{

    @Override
    public void tileStateAction(@NotNull TileSubjectView tileSubjectView, Pane... panes) {

        Pane oldParent = tileSubjectView.getParentPane();

        PathTransition toNewParent = tileSubjectView.createPathTransition(panes);

        // Start the animation
        toNewParent.playFromStart();

        toNewParent.setOnFinished( value -> {
            tileSubjectView.changeParent(Arrays.asList(panes).get((Arrays.asList(panes).size() - 1)), 0.0, 0.0);
            tileSubjectView.setCurrentState(new TileViewInBox(oldParent));
        });
    }

    @Override
    public void reverseStateAction(TileSubjectView tileSubjectView) {
    }
}
