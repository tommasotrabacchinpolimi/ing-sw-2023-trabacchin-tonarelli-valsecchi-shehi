package it.polimi.ingsw.view.gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

/**
 * This class implements a ChangeListener that adjusts the font size of text based on the size of a root pane.
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
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

    /**
     * Constructs a TextSizeChangeListener object with the specified parameters.
     *
     * @param rootPane      The root pane containing the text.
     * @param screenWidth  The width of the screen.
     * @param screenHeight The height of the screen.
     */
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

    /**
     * Called when the observed value changes. Adjusts the font size of the text based on the size of the root pane.
     *
     * @param observableValue The observable value.
     * @param oldValue        The old value.
     * @param newValue        The new value.
     */
    @Override
    public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {

        final double newWidth = rootPane.getWidth();
        final double newHeight = rootPane.getHeight();

        final double scaleFactor = (newWidth / newHeight) > initialScale ? (newHeight / initialHeight) : (newWidth / initialWidth);

        rootPane.setStyle("-fx-font-size: " + (scaleFactor * fontSize) + "px");
    }
}