package it.polimi.ingsw.view.gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

import java.awt.*;

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

        if(rootPane.getWidth() != 0.0 && rootPane.getHeight() != 0.0) {
            this.initialWidth = rootPane.getWidth();
            this.initialHeight = rootPane.getHeight();
        } else {
            GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

            this.initialWidth = gd.getDisplayMode().getWidth() * 0.5;
            this.initialHeight = gd.getDisplayMode().getHeight() * 0.5;
        }

        this.initialScale = this.initialWidth / this.initialHeight;
    }

    public SizeChangeListener(final Pane rootPane, final int screen_width, final int screen_height) {

        this.rootPane = rootPane;

        if(rootPane.getWidth() != 0.0 && rootPane.getHeight() != 0.0) {
            this.initialWidth = rootPane.getWidth();
            this.initialHeight = rootPane.getHeight();
        } else {
            this.initialWidth = screen_width * 0.5;
            this.initialHeight = screen_height * 0.5;
        }

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