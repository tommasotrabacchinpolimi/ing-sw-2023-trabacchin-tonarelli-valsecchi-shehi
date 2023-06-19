package it.polimi.ingsw.view.gui.customcomponents.animations;

import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;

import static it.polimi.ingsw.view.gui.customcomponents.guitoolkit.MyShelfieTransitionDurationType.DEF_DURATION;

/**
 * This class is used to create a smooth scale transition
 * of the element on which the resulting
 * {@linkplain MyShelfieAnimation animation} will be applied
 *
 * @see ScaleTransition
 * @see MyShelfieTransition
 */
public class MyShelfieScaleTransition implements MyShelfieTransition{

    /**
     * Specifies the start X scale value for the
     * {@linkplain ScaleTransition scale transition}
     */
    private final double fromX;

    /**
     * Specifies the start Y scale value for the
     * {@linkplain ScaleTransition scale transition}
     */
    private final double fromY;

    /**
     * Specifies the stop X scale value for the
     * {@linkplain ScaleTransition scale transition}
     */
    private final double toX;

    /**
     * Specifies the stop Y scale value for the
     * {@linkplain ScaleTransition scale transition}
     */
    private final double toY;

    /**
     * Construct a scale transition with specified parameters
     *
     * @param toX the stop X scale value
     * @param toY the stop Y scale value
     *
     * @apiNote The {@linkplain #fromX starting X} and
     * {@linkplain #fromY starting Y} values of the transition
     * are setted at "{@code 100%}" default value
     */
    public MyShelfieScaleTransition(double toX, double toY) {
        this(1.0, 1.0, toX, toY);
    }

    /**
     * Construct a scale transition with specified parameters
     *
     * @param fromX the start X scale value
     * @param fromY the start Y scale value
     * @param toX the stop X scale value
     * @param toY the stop Y scale value
     */
    public MyShelfieScaleTransition(double fromX, double fromY, double toX, double toY) {
        this.fromX = fromX;
        this.fromY = fromY;
        this.toX = toX;
        this.toY = toY;
    }

    /**
     * {@inheritDoc}
     * <p>More precisely in this case the transition will
     * scale the graphical element from a {@linkplain #fromX
     * starting X} and {@linkplain #fromY starting Y} values
     * and stops when {@linkplain #toX end X} and
     * {@linkplain #toY end Y} values will be reached</p>
     *
     * @return the {@linkplain ScaleTransition scale
     * transition} of the element from the starting X and Y
     * values to the ending X and Y values
     */
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
