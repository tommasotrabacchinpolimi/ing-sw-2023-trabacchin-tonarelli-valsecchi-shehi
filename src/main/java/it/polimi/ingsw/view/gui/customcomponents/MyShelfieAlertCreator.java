package it.polimi.ingsw.view.gui.customcomponents;

import it.polimi.ingsw.view.gui.MyShelfieController;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * The class is final, so no one can extend it
 *
 * @version 2.0
 * @since 02/06/2023
 */
public final class MyShelfieAlertCreator {
    private final static String ALERT_STYLE_SHEET = "/it.polimi.ingsw/layout/MyShelfieErrorAlert.css";

    private final static String ERROR_ICON = "/it.polimi.ingsw/graphical.resources/icons/error_cat_icon.png";

    private final static String WARNING_ICON = "/it.polimi.ingsw/graphical.resources/icons/warning_game_icon.png";

    private final static String INFO_ICON = "/it.polimi.ingsw/graphical.resources/icons/info_book_icon.png";

    /**
     * Default constructor made private, so no one can create an instance of this class
     */
    private MyShelfieAlertCreator(){
    }

    @NotNull
    private static Node getDefaultErrorAlertGraphic() {
        Pane graphicPane = new Pane();

        setDefaultCSSGraphic(graphicPane, ERROR_ICON);

        setDefaultShapeGraphic(graphicPane);

        return graphicPane;
    }

    @NotNull
    private static Node getDefaultWarningAlertGraphic() {
        Pane graphicPane = new Pane();

        setDefaultCSSGraphic(graphicPane, WARNING_ICON);

        setDefaultShapeGraphic(graphicPane);

        return graphicPane;
    }

    @NotNull
    private static Node getDefaultInformationAlertGraphic() {
        Pane graphicPane = new Pane();

        setDefaultCSSGraphic(graphicPane, INFO_ICON);

        setDefaultShapeGraphic(graphicPane);

        return graphicPane;
    }

    private static void setDefaultCSSGraphic(@NotNull Pane graphicPane, String iconFilePath) {
        graphicPane.setStyle(
                "-fx-background-image: url('" + MyShelfieController.class.getResource(iconFilePath) + "');" +
                        "-fx-background-position: center;" +
                        "-fx-background-repeat: no-repeat;" +
                        "-fx-background-size: cover;" +
                        "-fx-padding: 2.5em;" +
                        "-fx-effect: dropshadow(gaussian, rgba(16, 27, 31, 0.93), 10.0, 0.0, 0.0, 0.0)");
    }

    private static void setDefaultShapeGraphic(@NotNull Pane graphicPane) {
        Rectangle clipper = new Rectangle();

        graphicPane.layoutBoundsProperty().addListener((observableValue, oldValue, newValue) -> {
            clipper.setHeight(newValue.getHeight());
            clipper.setWidth(newValue.getWidth());

            double radiusSize = Math.min(newValue.getHeight(), newValue.getWidth());

            clipper.setArcWidth(radiusSize * 0.10);
            clipper.setArcHeight(radiusSize * 0.10);
        });

        graphicPane.setShape(clipper);
    }

    @NotNull
    private static Alert displayAlert(Alert.AlertType alertType, String contentText, String headerText, Node graphic) {
        Alert alert = new Alert(alertType);

        alert.setContentText(contentText);
        alert.setHeaderText(headerText);

        if(graphic == null){
            switch (alertType){
                case ERROR -> graphic = getDefaultErrorAlertGraphic();
                case WARNING -> graphic = getDefaultWarningAlertGraphic();
                case INFORMATION -> graphic = getDefaultInformationAlertGraphic();
            }
        }

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
    public static Alert displayErrorAlert(String contentText, String headerText, Node graphic) {
        return displayAlert(Alert.AlertType.ERROR, contentText, headerText, graphic);
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

    @NotNull
    public static Alert displayWarningAlert(String contentText, String headerText, Node graphic) {
        return displayAlert(Alert.AlertType.WARNING, contentText, headerText, graphic);
    }

    @NotNull
    public static Alert displayWarningAlert(String contentText, String headerText) {
        return displayWarningAlert(contentText, headerText, null);
    }

    @NotNull
    public static Alert displayWarningAlert(String contentText) {
        return displayWarningAlert(contentText, null);
    }

    @NotNull
    public static Alert displayWarningAlert(@NotNull Exception e) {
        return displayWarningAlert(e.getMessage(), e.getCause().toString());
    }

    @NotNull
    public static Alert displayInformationAlert(String contentText, String headerText, Node graphic) {
        return displayAlert(Alert.AlertType.INFORMATION, contentText, headerText, graphic);
    }

    @NotNull
    public static Alert displayInformationAlert(String contentText, String headerText) {
        return displayInformationAlert(contentText, headerText, null);
    }

    @NotNull
    public static Alert displayInformationAlert(String contentText) {
        return displayInformationAlert(contentText, null);
    }

    @NotNull
    public static Alert displayInformationAlert(@NotNull Exception e) {
        return displayInformationAlert(e.getMessage(), e.getCause().toString());
    }
}
