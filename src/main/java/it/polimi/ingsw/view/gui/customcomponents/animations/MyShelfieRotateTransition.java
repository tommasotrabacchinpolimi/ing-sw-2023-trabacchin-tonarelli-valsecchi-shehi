package it.polimi.ingsw.view.gui.customcomponents.animations;

import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;

import static it.polimi.ingsw.view.gui.customcomponents.guitoolkit.MyShelfieTransitionDurationType.DEF_DURATION;

/**
 * This class is used to create a smooth rotation transition
 * from the center of the element on which the resulting
 * {@linkplain MyShelfieAnimation animation} will be applied
 *
 * @see RotateTransition
 * @see MyShelfieTransition
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public class MyShelfieRotateTransition implements MyShelfieTransition{

    /**
     * Specifies the start angle value for the {@linkplain RotateTransition rotation}.
     */
    private final double fromAngle;

    /**
     * Specifies the stop angle value for the {@linkplain RotateTransition rotation}.
     */
    private final double toAngle;

    /**
     * Construct a rotation transition with specified parameters
     *
     * @param fromAngle the start angle value
     * @param toAngle the stop angle value
     */
    public MyShelfieRotateTransition(double fromAngle, double toAngle) {
        this.fromAngle = fromAngle;
        this.toAngle = toAngle;
    }

    /**
     * {@inheritDoc}
     * <p>More precisely in this case the transition is a
     * rotation that is executed from the center of the
     * node on which it will be applied</p>
     *
     * @return the {@linkplain RotateTransition rotate
     * transition} of the element from the {@linkplain
     * #fromAngle starting angle} to the {@linkplain
     * #toAngle ending angle}
     */
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
