package it.polimi.ingsw.view.gui.customcomponents;

import it.polimi.ingsw.view.gui.MyShelfieController;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.Region;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * The class is final, so no one can extend it
 *
 * @version 2.0
 * @since 02/06/2023
 */
public final class MyShelfieAlertCreator {
    private final static String ALERT_STYLE_SHEET = "/it.polimi.ingsw/layout/MyShelfieAlert.css";

    private final static String ERROR_ICON = "/it.polimi.ingsw/graphical.resources/icons/error_cat_icon.png";

    private final static String WARNING_ICON = "/it.polimi.ingsw/graphical.resources/icons/warning_game_icon.png";

    private final static String INFORMATION_ICON = "/it.polimi.ingsw/graphical.resources/icons/info_book_icon.png";

    /**
     * Default constructor made private, so no one can create an instance of this class
     */
    private MyShelfieAlertCreator(){
    }

    @NotNull
    private static Alert displayAlert(Alert.AlertType alertType, String contentText, String headerText, Node graphic) {
        Alert alert = new Alert(alertType);

        alert.setContentText(contentText);
        alert.setHeaderText(headerText);

        if(graphic == null){
            switch (alertType){
                case ERROR -> graphic = new MyShelfieGraphicIcon(ERROR_ICON, 2.5);
                case WARNING -> graphic = new MyShelfieGraphicIcon(WARNING_ICON, 2.5);
                case INFORMATION -> graphic = new MyShelfieGraphicIcon(INFORMATION_ICON, 2.5);
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
