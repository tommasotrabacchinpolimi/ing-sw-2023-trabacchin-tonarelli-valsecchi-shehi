package it.polimi.ingsw.view.gui;

import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.util.Duration;

@Deprecated
public class MyFadeTransition extends AnimationTimer {

    private static final double DURATION = 100_000_000;

    private final Node node;
    private long startTime;
    private final double duration;
    private final double fromOpacity;
    private final double toOpacity;
    private boolean isPlaying = false;

    public MyFadeTransition(Node node) {
        this.node = node;
        this.duration = DURATION;
        this.fromOpacity = 0.0;
        this.toOpacity = 1.0;
    }

    public MyFadeTransition(Node node, double duration) {
        this.node = node;
        this.duration = duration;
        this.fromOpacity = node.getOpacity();
        this.toOpacity = (this.fromOpacity == 0.0) ? 1.0 : 0.0;
    }

    public MyFadeTransition(Node node, double duration, double fromOpacity, double toOpacity) {
        this.node = node;
        this.duration = duration * DURATION;
        this.fromOpacity = fromOpacity;
        this.toOpacity = toOpacity;
    }

    public MyFadeTransition(Node node, double fromOpacity, double toOpacity) {
        this.node = node;
        this.duration = DURATION;
        this.fromOpacity = fromOpacity;
        this.toOpacity = toOpacity;
    }

    @Override
    public void handle(long now) {
        if(!isPlaying)
            return;

        long elapsedTime = now - startTime;
        double opacity = fromOpacity;
        double progress = (double) elapsedTime / duration;

        if (progress >= 1.0) {
            stop();
        } else {
            opacity = progress;
        }

        node.setOpacity(fromOpacity - opacity);
    }

    @Override
    public void start() {
        startTime = System.nanoTime();
        isPlaying = true;
        super.start();
    }

    @Override
    public void stop() {
        super.stop();
        isPlaying = false;
        node.setOpacity(toOpacity);
    }

    public static void setDisableAfterTransition(Node node, boolean disable) {
        node.setDisable(disable);

        if (disable) {
            node.setOpacity(0.5); // set the opacity to a value that appears grayed out
        } else {
            node.setOpacity(1.0);
        }
    }
}