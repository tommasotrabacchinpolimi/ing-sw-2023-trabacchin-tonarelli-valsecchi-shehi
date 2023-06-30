package it.polimi.ingsw.view.gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * This class implements a ChangeListener that maintains the aspect ratio and minimum size of a Stage window.
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 *
 * @version 3.0
 * @since 01/06/2023
 */
public class WindowSizeChangeListener implements ChangeListener<Number> {

    /**
     * Final Attribute indicating the proportion to screen
     */
    private final static double PROPORTION_TO_SCREEN = 0.35;

    /**
     * The Stage window to listen for size changes.
     */
    private final Stage stage;

    /**
     * The minimum size proportion of the screen.
     */
    private final double minSizeProportion;

    /**
     * Indicates whether the listener is active or not.
     */
    private boolean isActive;

    /**
     * The current height ratio of the stage.
     */
    private double ratioHeight;

    /**
     * The current width ratio of the stage.
     */
    private double ratioWidth;

    /**
     *  The desired aspect ratio of the stage.
     */
    private final double aspectRatio;

    /**
     * Constructs a WindowSizeChangeListener object with the specified Stage and minimum size proportion.
     *
     * @param stage             The Stage window to listen for size changes.
     * @param minSizeProportion The minimum size proportion of the screen.
     */
    public WindowSizeChangeListener(Stage stage, double minSizeProportion) {
        this.stage = stage;
        this.minSizeProportion = minSizeProportion;

        this.isActive = false;

        Rectangle2D screenBounds = Screen.getPrimary().getBounds();

        this.stage.setMinHeight(screenBounds.getHeight() * this.minSizeProportion);
        this.stage.setMinWidth(screenBounds.getWidth() * this.minSizeProportion);

        this.ratioWidth = stage.getWidth();
        this.ratioHeight = stage.getHeight();

        this.aspectRatio = ratioWidth / ratioHeight;
    }



    /**
     * Constructs a WindowSizeChangeListener object with the specified Stage and the default minimum size proportion.
     * The default minimum size proportion is set to 0.35.
     *
     * @param stage The Stage window to listen for size changes.
     */
    public WindowSizeChangeListener(Stage stage) {
        this(stage, PROPORTION_TO_SCREEN);
    }

    /**
     * Called when the observed value changes. Maintains the aspect ratio and minimum size of the Stage window.
     *
     * @param observable The observable value.
     * @param oldValue   The old value.
     * @param newValue   The new value.
     */
    @Override
    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        if (!stage.isMaximized() && !stage.isIconified() && !stage.isFullScreen() && !isActive) {
            isActive = true;

            if (observable == stage.heightProperty() && keepHeightAspectRatio(oldValue, newValue)) {
                ratioHeight = newValue.doubleValue();
                stage.setWidth(Math.round(ratioHeight * aspectRatio));
            } else if (observable == stage.widthProperty() && keepWidthAspectRatio(oldValue, newValue)) {
                ratioWidth = newValue.doubleValue();
                stage.setHeight(Math.round(ratioWidth / aspectRatio));
            }

            isActive = false;
        }
    }

    /**
     * Checks if the current stage has the correct aspect ratio.
     *
     * @return true if the stage has the correct aspect ratio, false otherwise.
     */
    private boolean hasRightProportion(){
        return Math.abs(stage.getWidth() / stage.getHeight() - aspectRatio) < 0.001;
    }

    /**
     * Checks if the height of the stage should be adjusted while maintaining the aspect ratio.
     *
     * @param oldValue The old value of the height.
     * @param newValue The new value of the height.
     * @return true if the height should be adjusted, false otherwise.
     */
    private boolean keepHeightAspectRatio(Number oldValue, Number newValue){
        return stage.getHeight() != oldValue.doubleValue() &&
                oldValue.doubleValue() != newValue.doubleValue() &&
                !hasRightProportion() && stage.getHeight() != ratioHeight;
    }

    /**
     * Checks if the width of the stage should be adjusted while maintaining the aspect ratio.
     *
     * @param oldValue The old value of the width.
     * @param newValue The new value of the width.
     * @return true if the width should be adjusted, false otherwise.
     */
    private boolean keepWidthAspectRatio(Number oldValue, Number newValue){
        return stage.getWidth() != oldValue.doubleValue() &&
                oldValue.doubleValue() != newValue.doubleValue() &&
                !hasRightProportion() && stage.getWidth() != ratioWidth;
    }
}
