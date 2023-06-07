package it.polimi.ingsw.view.gui.customcomponents.uitoolkit;

import javafx.util.Duration;

public enum MyShelfieTransition {
    DEF_DURATION(Duration.seconds(2)),

    SHORT_DURATION(Duration.seconds(1)),

    LONG_DURATION(Duration.seconds(4));

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
