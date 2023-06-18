package it.polimi.ingsw.view.gui.customcomponents.animations;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;

import static it.polimi.ingsw.view.gui.customcomponents.guitoolkit.MyShelfieTransitionDurationType.DEF_DURATION;

public class MyShelfieRotateTransition implements MyShelfieTransition{

    private final double fromAngle;

    private final double toAngle;

    public MyShelfieRotateTransition(double fromAngle, double toAngle) {
        this.fromAngle = fromAngle;
        this.toAngle = toAngle;
    }

    @Override
    public RotateTransition getTransition() {

        RotateTransition rotateTransition = new RotateTransition();
        rotateTransition.setDuration(DEF_DURATION.getDuration());
        rotateTransition.setInterpolator(Interpolator.LINEAR);
        rotateTransition.setFromAngle(fromAngle);
        rotateTransition.setToAngle(toAngle);
        rotateTransition.setCycleCount(1);
        rotateTransition.setAutoReverse(false);

        return rotateTransition;
    }
}
