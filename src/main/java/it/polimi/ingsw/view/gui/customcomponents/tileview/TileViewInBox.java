package it.polimi.ingsw.view.gui.customcomponents.tileview;

import javafx.animation.PathTransition;
import javafx.scene.layout.Pane;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class TileViewInBox implements TileSubjectViewState{
    private Pane oldParent;

    private TileViewInBox() {}

    public TileViewInBox(@NotNull Pane oldParent) {
        this.oldParent = oldParent;
    }

    @Override
    public void tileStateAction(@NotNull TileSubjectView tileSubjectView, Pane... panes) {

        if(panes == null) {
            //display error
            System.err.println("new Parent is null");
            return;
        }

        PathTransition toNewParent = tileSubjectView.createPathTransition(panes);

        toNewParent.playFromStart();

        toNewParent.setOnFinished( value ->
                tileSubjectView.changeParent(Arrays.asList(panes).get((Arrays.asList(panes).size() - 1)), 0.0, 0.0));
    }

    @Override
    public void reverseStateAction(TileSubjectView tileSubjectView) {

        PathTransition toOldParent = tileSubjectView.createPathTransition(oldParent);

        toOldParent.playFromStart();

        toOldParent.setOnFinished( value -> {
            tileSubjectView.changeParent(oldParent, 0.0, 0.0);
            tileSubjectView.setCurrentState(new TileViewInBoard());
        });
    }
}