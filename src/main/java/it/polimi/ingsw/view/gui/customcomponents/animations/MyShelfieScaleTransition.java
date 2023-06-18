package it.polimi.ingsw.view.gui.customcomponents.animations;

import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;

import static it.polimi.ingsw.view.gui.customcomponents.guitoolkit.MyShelfieTransitionDurationType.DEF_DURATION;

public class MyShelfieScaleTransition implements MyShelfieTransition{

    private final double fromX;
    private final double fromY;
    private final double toX;
    private final double toY;

    public MyShelfieScaleTransition(double toX, double toY) {
        this(1.0, 1.0, toX, toY);
    }

    public MyShelfieScaleTransition(double fromX, double fromY, double toX, double toY) {
        this.fromX = fromX;
        this.fromY = fromY;
        this.toX = toX;
        this.toY = toY;
    }

    @Override
    public ScaleTransition getTransition() {
        ScaleTransition scaleTransition = new ScaleTransition();
        scaleTransition.setDuration(DEF_DURATION.getDuration());
        scaleTransition.setInterpolator(Interpolator.LINEAR);
        scaleTransition.setFromX(fromX);
        scaleTransition.setFromY(fromY);
        scaleTransition.setToX(toX);
        scaleTransition.setToY(toY);
        scaleTransition.setCycleCount(1);
        scaleTransition.setAutoReverse(false);

        return scaleTransition;
    }
}
