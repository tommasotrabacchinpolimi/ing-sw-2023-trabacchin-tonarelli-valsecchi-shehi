package it.polimi.ingsw.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class FontChangeListener implements ChangeListener<Number> {
    private final Scene scene;
    private final Pane contentPane;

    private final String elementID;
    private final double standardSize;
    private final double initialScale;
    private final double initialHeight;
    private final double initialWidth;

    public FontChangeListener(Scene scene, Pane contentPane, String id, double standardSize) {
        this.scene = scene;
        this.contentPane = contentPane;
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

        final double newWidth  = scene.getWidth();
        final double newHeight = scene.getHeight();

        scaleTextContent((newWidth / newHeight) > initialScale ? (newHeight / initialHeight) : (newWidth / initialWidth));
    }

    private void scaleTextContent(double scaleFactor) {
        changeFontSize(contentPane.lookup(elementID), scaleFactor);
    }

    private void changeFontSize(Node textNode, double scaleFactor) {
        textNode.setStyle("-fx-font-size: " + scaleFactor * standardSize + "px" );
    }
}