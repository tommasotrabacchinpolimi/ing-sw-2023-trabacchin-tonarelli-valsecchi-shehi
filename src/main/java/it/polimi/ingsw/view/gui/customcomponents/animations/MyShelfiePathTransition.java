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

public class MyShelfiePathTransition implements MyShelfieTransition{

    private final Pane animatedPane;

    private final Pane[] destinationPanes;

    public MyShelfiePathTransition(Pane animatedPane, Pane... destinationPanes) {
        this.animatedPane = animatedPane;
        this.destinationPanes = destinationPanes;
    }

    @Override
    public PathTransition getTransition() {
        Bounds boundsInScene = animatedPane.localToScene(animatedPane.getBoundsInLocal());

        List<Bounds> destinationBounds =
                Arrays.stream(destinationPanes)
                        .map(destinationPane -> destinationPane.localToScene(destinationPane.getBoundsInLocal()))
                        .toList();

        MoveTo start = new MoveTo(animatedPane.getWidth() / 2, animatedPane.getHeight() / 2);

        List<PathElement> lineTos = new ArrayList<>(List.of(start));

        destinationBounds.forEach( destinationBound -> lineTos.add(createCenterLineTo(boundsInScene, destinationBound)));

        Path transitionPath = new Path(lineTos);

        PathTransition pathTransition = new PathTransition();

        pathTransition.setDuration(DEF_DURATION.getDuration());
        pathTransition.setInterpolator(Interpolator.LINEAR);
        pathTransition.setCycleCount(1);
        pathTransition.setAutoReverse(false);

        pathTransition.setPath(transitionPath);

        return pathTransition;
    }

    @NotNull
    private LineTo createCenterLineTo(@NotNull Bounds startingBounds, @NotNull Bounds endingBounds) {
        double endX = endingBounds.getMinX() - startingBounds.getMinX() - (startingBounds.getWidth() - animatedPane.getWidth()) / 2.0 + (endingBounds.getWidth() / 2.0);
        double endY = endingBounds.getMinY() - startingBounds.getMinY() - (startingBounds.getHeight() - animatedPane.getHeight()) / 2.0 + (endingBounds.getHeight() / 2.0);

        return new LineTo(endX, endY);
    }
}
