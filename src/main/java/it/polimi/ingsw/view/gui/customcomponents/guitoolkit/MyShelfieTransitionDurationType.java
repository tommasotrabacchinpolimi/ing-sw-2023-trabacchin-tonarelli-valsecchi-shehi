package it.polimi.ingsw.view.gui.customcomponents.guitoolkit;

import javafx.util.Duration;

public enum MyShelfieTransitionDurationType {

    SHORT_DURATION(new Duration(100)),

    DEF_DURATION(new Duration(600)),

    LONG_DURATION(new Duration(800)),

    EXTREME_LONG_DURATION(new Duration(5000));

    MyShelfieTransitionDurationType(Duration animationDuration) {
        this.animationDuration = animationDuration;
    }

    private final Duration animationDuration;

    public Duration getDuration(){
        return animationDuration;
    }

    public double asHours() {
        return animationDuration.toHours();
    }

    public double asMinutes() {
        return animationDuration.toMinutes();
    }

    public double asSeconds() {
        return animationDuration.toSeconds();
    }

    public double asMillis() {
        return animationDuration.toMillis();
    }
}
