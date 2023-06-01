package it.polimi.ingsw.view.gui;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * The class is final, so no one can extend it
 */
public final class MyShelfieAlertCreator {
    private final static String ALERT_STYLE_SHEET = "/it.polimi.ingsw/layout/MyShelfieErrorAlert.css";

    private final static String ERROR_ICON = "/it.polimi.ingsw/graphical.resources/icons/error_cat_icon.png";

    /**
     * Default constructor made private, so no one can create an instance of this class
     */
    private MyShelfieAlertCreator(){
    }

    @NotNull
    private static Node getDefaultErrorAlertGraphic() {
        Pane graphicPane = new Pane();
        Rectangle clipper = new Rectangle();

        graphicPane.setStyle(
                "-fx-background-image: url('" + MyShelfieController.class.getResource(ERROR_ICON) + "');" +
                        "-fx-background-position: center;" +
                        "-fx-background-repeat: no-repeat;" +
                        "-fx-background-size: cover;" +
                        "-fx-padding: 2.5em;" +
                        "-fx-effect: dropshadow(gaussian, rgba(16, 27, 31, 0.93), 10.0, 0.0, 0.0, 0.0)");

        graphicPane.layoutBoundsProperty().addListener((observableValue, oldValue, newValue) -> {
            clipper.setHeight(newValue.getHeight());
            clipper.setWidth(newValue.getWidth());

            double radiusSize = Math.min(newValue.getHeight(), newValue.getWidth());

            clipper.setArcWidth(radiusSize * 0.10);
            clipper.setArcHeight(radiusSize * 0.10);
        });

        graphicPane.setShape(clipper);

        return graphicPane;
    }

    @NotNull
    public static Alert displayErrorAlert(String contentText, String headerText, Node graphic) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(contentText);
        alert.setHeaderText(headerText);

        if(graphic == null)
            graphic = getDefaultErrorAlertGraphic();

        alert.setGraphic(graphic);

        alert.getDialogPane().getStylesheets().add(
                Objects.requireNonNull(
                        MyShelfieController.class.getResource(ALERT_STYLE_SHEET)
                ).toExternalForm());
        alert.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

        alert.showAndWait();

        return alert;
    }

    @NotNull
    public static Alert displayErrorAlert(String contentText, String headerText) {
        return displayErrorAlert(contentText, headerText, null);
    }

    @NotNull
    public static Alert displayErrorAlert(String contentText) {
        return displayErrorAlert(contentText, null);
    }

    @NotNull
    public static Alert displayErrorAlert(@NotNull Exception e) {
        return displayErrorAlert(e.getMessage(), e.getCause().toString());
    }
}
