package it.polimi.ingsw.view.gui.customcomponents.guitoolkit;

import javafx.util.Duration;

/**
 * The `MyShelfieTransitionDurationType` enum represents different types of transition durations for animations in the GUI toolkit.
 * Each duration type corresponds to a specific duration value, represented by the `Duration` class from JavaFX.
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem shehi
 */
public enum MyShelfieTransitionDurationType {

    /**
     * Short duration type with a duration of 100 milliseconds.
     */
    SHORT_DURATION(new Duration(100)),

    /**
     * Default duration type with a duration of 600 milliseconds.
     */
    DEF_DURATION(new Duration(600)),

    /**
     * Long duration type with a duration of 800 milliseconds.
     */
    LONG_DURATION(new Duration(800)),

    /**
     * Extreme long duration type with a duration of 5000 milliseconds.
     */
    EXTREME_LONG_DURATION(new Duration(5000));

    /**
     * The animation duration associated with the duration type.
     */
    private final Duration animationDuration;

    /**
     * Constructs a `MyShelfieTransitionDurationType` with the specified animation duration.
     *
     * @param animationDuration The animation duration.
     */
    MyShelfieTransitionDurationType(Duration animationDuration) {
        this.animationDuration = animationDuration;
    }

    /**
     * Returns the animation duration associated with the duration type.
     *
     * @return The animation duration.
     */
    public Duration getDuration() {
        return animationDuration;
    }

    /**
     * Returns the animation duration in hours.
     *
     * @return The animation duration in hours.
     */
    public double asHours() {
        return animationDuration.toHours();
    }

    /**
     * Returns the animation duration in minutes.
     *
     * @return The animation duration in minutes.
     */
    public double asMinutes() {
        return animationDuration.toMinutes();
    }

    /**
     * Returns the animation duration in seconds.
     *
     * @return The animation duration in seconds.
     */
    public double asSeconds() {
        return animationDuration.toSeconds();
    }

    /**
     * Returns the animation duration in milliseconds.
     *
     * @return The animation duration in milliseconds.
     */
    public double asMillis() {
        return animationDuration.toMillis();
    }
}
