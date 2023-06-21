package it.polimi.ingsw.view.gui.customcomponents.tileview;

import it.polimi.ingsw.view.gui.customcomponents.MyShelfieAlertCreator;
import it.polimi.ingsw.view.gui.customcomponents.animations.MyShelfieAnimation;
import it.polimi.ingsw.view.gui.customcomponents.animations.MyShelfiePathTransition;
import it.polimi.ingsw.view.gui.customcomponents.animations.MyShelfieScaleTransition;
import javafx.scene.layout.Pane;

import java.util.Arrays;

public class TileViewInOpponent implements TileSubjectViewState{
    @Override
    public void tileStateAction(TileSubjectView tileSubjectView, Pane... panes) {
        MyShelfieAnimation.build()
                .addAnimation(new MyShelfiePathTransition(tileSubjectView, panes))
                .addAnimation(new MyShelfieScaleTransition(0.5, 0.5))
                .playMyShelfieAnimation(tileSubjectView, value -> {
                    tileSubjectView.changeParent(Arrays.asList(panes).get((Arrays.asList(panes).size() - 1)), 0.0, 0.0);
                    tileSubjectView.updatedCSS(1.4);
                });
    }

    @Override
    public void reverseStateAction(TileSubjectView tileSubjectView) {
        MyShelfieAlertCreator.displayErrorAlert(
                "The tile can not be transferred back to board once is in an opponent bookshelf",
                "Cannot move tile");
    }
}
