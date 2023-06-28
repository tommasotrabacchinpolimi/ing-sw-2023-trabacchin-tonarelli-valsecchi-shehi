package it.polimi.ingsw.view.gui.customcomponents.tileview;

import it.polimi.ingsw.view.gui.customcomponents.animations.MyShelfieAnimation;
import it.polimi.ingsw.view.gui.customcomponents.animations.MyShelfiePathTransition;
import it.polimi.ingsw.view.gui.customcomponents.animations.MyShelfieScaleTransition;
import it.polimi.ingsw.view.gui.customcomponents.guitoolkit.MyShelfieAlertCreator;
import it.polimi.ingsw.view.gui.customcomponents.guitoolkit.MyShelfieTransitionDurationType;
import javafx.scene.layout.Pane;

import java.util.Arrays;
/**
 * The `TileViewInOpponent` class represents the state of a tile subject view when it is in an opponent's bookshelf in a graphical user interface.
 * It implements the `TileSubjectViewState` interface and defines the actions to be performed on the tile subject view when it is in this state.
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public class TileViewInOpponent implements TileSubjectViewState{

    private static final double PADDING = 1.4;

    /**
     * Performs the action associated with the state on the specified tile subject view and its parent pane(s).
     *
     * @param tileSubjectView The tile subject view to perform the action on.
     * @param panes           The parent pane(s) of the tile subject view.
     */
    @Override
    public void tileStateAction(TileSubjectView tileSubjectView, Pane... panes) {
        MyShelfieAnimation.build()
                .addAnimation(new MyShelfiePathTransition(MyShelfieTransitionDurationType.LONG_DURATION, tileSubjectView, panes))
                .addAnimation(new MyShelfieScaleTransition(0.5, 0.5))
                .playMyShelfieAnimation(tileSubjectView, value -> {
                    tileSubjectView.changeParent(Arrays.asList(panes).get((Arrays.asList(panes).size() - 1)), 0.0, 0.0);
                    tileSubjectView.updatedCSS();
                });
    }

    /**
     * Reverses the action associated with the state on the specified tile subject view.
     *
     * @param tileSubjectView The tile subject view to reverse the action on.
     */
    @Override
    public void reverseStateAction(TileSubjectView tileSubjectView) {
        MyShelfieAlertCreator.displayErrorAlert(
                "The tile cannot be transferred back to the board once it is in an opponent's bookshelf",
                "Cannot move tile");
    }

    @Override
    public double getPadding() {
        return PADDING;
    }
}
