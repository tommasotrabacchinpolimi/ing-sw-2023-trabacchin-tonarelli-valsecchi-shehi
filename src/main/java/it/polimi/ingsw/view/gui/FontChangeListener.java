package it.polimi.ingsw.view.gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.text.Font;

import java.awt.*;

public class FontChangeListener implements ChangeListener<Number> {

    private static final String ID_INITIAL_CHARACTER = "#";
    private final Scene scene;
    private final String elementID;
    private final double standardSize;
    private final double initialScale;
    private final double initialHeight;
    private final double initialWidth;

    public FontChangeListener(Scene scene, String id, double standardSize) {
        this.scene = scene;
        this.elementID = "#" + id;
        this.standardSize = standardSize;

        this.initialWidth = scene.getWidth();
        this.initialHeight = scene.getHeight();

        this.initialScale = this.initialWidth / this.initialHeight;

        this.scene.widthProperty().addListener(this);
        this.scene.heightProperty().addListener(this);

        scaleTextContent(this.initialScale);
    }

    @Override
    public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {

        final double newWidth = scene.getWidth();
        final double newHeight = scene.getHeight();

        scaleTextContent((newWidth / newHeight) > initialScale ? (newHeight / initialHeight) : (newWidth / initialWidth));
    }

    private void scaleTextContent(double scaleFactor) {
        changeFontSize(this.scene.lookup(elementID), scaleFactor);
    }

    private void changeFontSize(Node textNode, double scaleFactor) {
        textNode.setStyle("-fx-font-size: " + scaleFactor * standardSize + "px");
    }

    public static void setResizableFont(Scene scene, String id, double standardSize) {
        final double initialHeight = scene.getHeight();
        final double initialWidth = scene.getWidth();
        final double initialScale = initialWidth / initialHeight;

        scene.widthProperty().addListener((observableValue, oldValue, newValue) -> {
            final double newWidth = scene.getWidth();
            final double newHeight = scene.getHeight();

            scaleTextContent(scene, (newWidth / newHeight) > initialScale ? (newHeight / initialHeight) : (newWidth / initialWidth), ID_INITIAL_CHARACTER + id, standardSize);
        });

        scene.heightProperty().addListener((observableValue, oldValue, newValue) -> {
            final double newWidth = scene.getWidth();
            final double newHeight = scene.getHeight();

            scaleTextContent(scene, (newWidth / newHeight) > initialScale ? (newHeight / initialHeight) : (newWidth / initialWidth), ID_INITIAL_CHARACTER + id, standardSize);
        });
    }

    public static void automaticResizableFont(Scene scene, String id) {
        setResizableFont(scene, id, Font.getDefault().getSize());
    }

    private static void scaleTextContent(Scene scene, double scaleFactor, String elementID, double standardSize) {
        changeFontSize(scene.lookup(elementID), scaleFactor, standardSize);
    }

    private static void changeFontSize(Node textNode, double scaleFactor, double standardSize) {
        textNode.setStyle("-fx-font-size: " + scaleFactor * standardSize + "px");
    }
}