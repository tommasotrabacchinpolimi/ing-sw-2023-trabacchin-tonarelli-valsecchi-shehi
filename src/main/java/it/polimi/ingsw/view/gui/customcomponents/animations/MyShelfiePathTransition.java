package it.polimi.ingsw.view.gui.customcomponents.animations;

import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.geometry.Bounds;
import javafx.scene.layout.Pane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static it.polimi.ingsw.view.gui.customcomponents.guitoolkit.MyShelfieTransitionDurationType.DEF_DURATION;

/**
 * This class is used to create a smooth path transition
 * from the element specified at its construction
 * to the element(s) where it should be translated
 *
 * @see PathTransition
 * @see MyShelfieTransition
 */
public class MyShelfiePathTransition implements MyShelfieTransition {

    /**
     * The pane that will be animated with the
     * {@link MyShelfiePathTransition}
     */
    private final Pane animatedPane;

    /**
     * The ordered groups of pane that the
     * {@linkplain #animatedPane animated}
     * graphical component has to reach
     */
    private final Pane[] destinationPanes;

    /**
     * Construct a smooth path transition with specified parameters
     *
     * @param animatedPane     the pane that will be considered
     *                         as the starting point for
     *                         defining the path to be taken
     * @param destinationPanes the panes that {@code animatedPane}
     *                         has to reach
     */
    public MyShelfiePathTransition(Pane animatedPane, Pane... destinationPanes) {
        this.animatedPane = animatedPane;
        this.destinationPanes = destinationPanes;
    }

    /**
     * {@inheritDoc}
     * <p>More precisely in this case the transition is
     * a path that starts from the center of
     * {@linkplain #animatedPane animated pane} and
     * reaches the center of each {@link #destinationPanes}</p>
     *
     * @return the {@linkplain PathTransition path transition}
     * that guide the {@link #animatedPane animated pane} to the
     * center of each {@link #destinationPanes destination pane}
     */
    @Override
    public PathTransition getTransition() {
        Bounds boundsInScene = animatedPane.localToScene(animatedPane.getBoundsInLocal());

        List<Bounds> destinationBounds =
                Arrays.stream(destinationPanes)
                        .map(destinationPane -> destinationPane.localToScene(destinationPane.getBoundsInLocal()))
                        .toList();

        MoveTo start = new MoveTo(animatedPane.getWidth() / 2, animatedPane.getHeight() / 2);

        List<PathElement> lineTos = new ArrayList<>(List.of(start));

        destinationBounds.forEach(destinationBound -> lineTos.add(createCenterLineTo(boundsInScene, destinationBound)));

        Path transitionPath = new Path(lineTos);

        PathTransition pathTransition = new PathTransition();

        pathTransition.setDuration(DEF_DURATION.getDuration());
        pathTransition.setInterpolator(Interpolator.LINEAR);
        pathTransition.setCycleCount(1);
        pathTransition.setAutoReverse(false);

        pathTransition.setPath(transitionPath);

        return pathTransition;
    }

    /**
     * Computes and construct a strict line from the
     * {@link #animatedPane animated pane}'s center to one of
     * the {@link #destinationPanes destination pane}'s center
     *
     * @param startingBounds the "{@linkplain javafx.scene.Node#localToScene(Bounds)
     *                       local to scene}" bounds of the {@link #animatedPane
     *                       animated pane}
     * @param endingBounds   the "{@linkplain javafx.scene.Node#localToScene(Bounds)
     *                       local to scene}" bounds of one of the
     *                       {@link #destinationPanes destination panes} that has to
     *                       be reached
     * @return a {@linkplain LineTo strict line} from the center of {@link #animatedPane
     * animated pane} to one of the {@link #destinationPanes destination panes}
     */
    @NotNull
    private LineTo createCenterLineTo(@NotNull Bounds startingBounds, @NotNull Bounds endingBounds) {
        double endX = endingBounds.getMinX() - startingBounds.getMinX() - (startingBounds.getWidth() - animatedPane.getWidth()) / 2.0 + (endingBounds.getWidth() / 2.0);
        double endY = endingBounds.getMinY() - startingBounds.getMinY() - (startingBounds.getHeight() - animatedPane.getHeight()) / 2.0 + (endingBounds.getHeight() / 2.0);

        return new LineTo(endX, endY);
    }
}
