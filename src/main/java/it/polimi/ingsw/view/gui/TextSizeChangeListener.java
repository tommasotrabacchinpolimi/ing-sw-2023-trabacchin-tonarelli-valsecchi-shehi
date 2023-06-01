package it.polimi.ingsw.view.gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

/**
 *
 * @version 3.0
 * @since 27/05/2023
 */
public class TextSizeChangeListener implements ChangeListener<Number> {
    private final double fontSize = Font.getDefault().getSize();

    private final Pane rootPane;

    private final double initialHeight;

    private final double initialWidth;

    private final double initialScale;

    public TextSizeChangeListener(final Pane rootPane, final double screenWidth, final double screenHeight) {

        this.rootPane = rootPane;

        if(rootPane.getWidth() != 0.0 && rootPane.getHeight() != 0.0) {
            this.initialWidth = rootPane.getWidth();
            this.initialHeight = rootPane.getHeight();
        } else {
            this.initialWidth = screenWidth * 0.5;
            this.initialHeight = screenHeight * 0.5;
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