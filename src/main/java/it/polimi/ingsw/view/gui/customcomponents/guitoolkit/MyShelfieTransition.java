package it.polimi.ingsw.view.gui.customcomponents.guitoolkit;

import javafx.util.Duration;

public enum MyShelfieTransition {
    DEF_DURATION(new Duration(600)),

    SHORT_DURATION(new Duration(100)),

    LONG_DURATION(new Duration(800));

    MyShelfieTransition(Duration animationDuration) {
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
