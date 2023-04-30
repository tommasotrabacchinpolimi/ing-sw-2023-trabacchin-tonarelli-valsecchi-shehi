package it.polimi.ingsw.view.gui;

import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

public class ImageRoundCornersClipper {
    public static void roundClipper(Region region, double arc){
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

    public static void roundClipperDropShadow(Region region, double arc, BlurType blurType, Color colorHSB, double radius, double spread, double offsetX, double offsetY){
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

    public static void roundClipper(Region region, double arc, double red) {
        final Rectangle outputClip = new Rectangle();

        region.layoutBoundsProperty().addListener((ov, oldValue, newValue) -> {
            final double lesserExtent = Math.min(newValue.getWidth(), newValue.getHeight());

            outputClip.setArcWidth(lesserExtent * (arc / 100));
            outputClip.setArcHeight(lesserExtent * (arc / 100));

            region.setClip(outputClip);

            outputClip.setWidth(newValue.getWidth() - (newValue.getWidth() * red / 100));
            outputClip.setHeight(newValue.getHeight() - (newValue.getHeight() * red / 100));

            centerBackground(outputClip, region);
        });
    }

    private static void centerBackground(Rectangle clip, Region region) {
        double w = 0;
        double h = 0;

        double ratioX = clip.getWidth() / region.getWidth();
        double ratioY = clip.getHeight() / region.getHeight();

        double reduceCoeff = Math.min(ratioX, ratioY);

        w = clip.getWidth() * reduceCoeff;
        h = clip.getHeight() * reduceCoeff;

        clip.setTranslateX((region.getWidth() - w) / 2);
        clip.setTranslateY((region.getHeight() - h) / 2);
    }
}
