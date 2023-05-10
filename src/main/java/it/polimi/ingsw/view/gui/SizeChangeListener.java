package it.polimi.ingsw.view.gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

/**
 *
 * @version 3.0
 * @since 10/05/2023
 */
public class SizeChangeListener implements ChangeListener<Number> {
    private final double fontSize = Font.getDefault().getSize();

    private final Pane rootPane;

    private final double initialHeight;

    private final double initialWidth;

    private final double initialScale;

    public SizeChangeListener(final Pane rootPane) {

        this.rootPane = rootPane;

        this.initialWidth = rootPane.getWidth();
        this.initialHeight = rootPane.getHeight();

        this.initialScale = this.initialWidth / this.initialHeight;
    }

    @Override
    public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {

        final double newWidth = rootPane.getWidth();
        final double newHeight = rootPane.getHeight();

        final double scaleFactor = (newWidth / newHeight) > initialScale ? (newHeight / initialHeight) : (newWidth / initialWidth);

        rootPane.setStyle("-fx-font-size: " + (scaleFactor * fontSize) + "px");
    }
}