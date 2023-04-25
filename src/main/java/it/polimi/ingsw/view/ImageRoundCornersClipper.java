package it.polimi.ingsw.view;

import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

public class ImageRoundCornersClipper {
    public static void roundClipper(Region region, double arc) throws IOException {
        final Rectangle outputClip = new Rectangle();

        region.layoutBoundsProperty().addListener((ov, oldValue, newValue) -> {
            final double lesserExtent = Math.min(newValue.getWidth(), newValue.getHeight());

            outputClip.setArcWidth(lesserExtent * (arc / 100));
            outputClip.setArcHeight(lesserExtent * (arc / 100));

            region.setClip(outputClip);

            outputClip.setWidth(newValue.getWidth());
            outputClip.setHeight(newValue.getHeight());
        });
    }

    public static void roundClipper(Region region, double arc, double red) throws IOException {
        final Rectangle outputClip = new Rectangle();

        region.layoutBoundsProperty().addListener((ov, oldValue, newValue) -> {
            final double lesserExtent = Math.min(newValue.getWidth(), newValue.getHeight());

            outputClip.setArcWidth(lesserExtent * (arc / 100));
            outputClip.setArcHeight(lesserExtent * (arc / 100));

            region.setClip(outputClip);

            outputClip.setWidth(newValue.getWidth() - (newValue.getWidth() * red / 100));
            outputClip.setHeight(newValue.getHeight() - (newValue.getHeight() * red / 100));
        });
    }
}
