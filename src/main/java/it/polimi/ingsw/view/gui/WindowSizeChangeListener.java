package it.polimi.ingsw.view.gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class WindowSizeChangeListener implements ChangeListener<Number> {

    private final Stage stage;

    private boolean isActive;

    public WindowSizeChangeListener(Stage stage) {

        this.stage = stage;

        this.isActive = false;

        Rectangle2D screenBounds = Screen.getPrimary().getBounds();

        this.stage.setMinHeight(screenBounds.getHeight() * 0.25);
        this.stage.setMinWidth(screenBounds.getWidth() * 0.25);
    }

    @Override
    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        if (!stage.isMaximized() && !stage.isIconified() && !stage.isFullScreen() && !isActive) {
            isActive = true;

            if (observable == stage.heightProperty() && stage.getHeight() != oldValue.doubleValue() && oldValue.doubleValue() != newValue.doubleValue()) {
                stage.setWidth(newValue.doubleValue() * 2.0);
            } else if (observable == stage.widthProperty() && stage.getWidth() != oldValue.doubleValue() && oldValue.doubleValue() != newValue.doubleValue()) {
                stage.setHeight(newValue.doubleValue() / 2.0);
            }

            isActive = false;
        }
    }
}
