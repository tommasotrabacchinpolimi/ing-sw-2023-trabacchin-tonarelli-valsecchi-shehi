package it.polimi.ingsw.view.gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * @version 3.0
 * @since 01/06/2023
 */
public class WindowSizeChangeListener implements ChangeListener<Number> {

    private final static double PROPORTION_TO_SCREEN = 0.35;

    private final Stage stage;

    private final double minSizeProportion;

    private boolean isActive;

    private double ratioHeight;

    private double ratioWidth;

    private final double aspectRatio;

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


    public WindowSizeChangeListener(Stage stage) {
        this(stage, PROPORTION_TO_SCREEN);
    }

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

    private boolean hasRightProportion(){
        return Math.abs(stage.getWidth() / stage.getHeight() - aspectRatio) < 0.001;
    }

    private boolean keepHeightAspectRatio(Number oldValue, Number newValue){
        return stage.getHeight() != oldValue.doubleValue() &&
                oldValue.doubleValue() != newValue.doubleValue() &&
                !hasRightProportion() && stage.getHeight() != ratioHeight;
    }

    private boolean keepWidthAspectRatio(Number oldValue, Number newValue){
        return stage.getWidth() != oldValue.doubleValue() &&
                oldValue.doubleValue() != newValue.doubleValue() &&
                !hasRightProportion() && stage.getWidth() != ratioWidth;
    }
}
