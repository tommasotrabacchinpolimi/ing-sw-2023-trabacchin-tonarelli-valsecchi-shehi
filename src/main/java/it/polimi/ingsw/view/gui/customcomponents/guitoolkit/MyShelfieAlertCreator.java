package it.polimi.ingsw.view.gui.customcomponents.guitoolkit;

import it.polimi.ingsw.view.gui.customcomponents.MyShelfieGraphicIcon;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.Region;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;

/**
 * A utility {@code class} that is used to display some alert
 * when an exception or an error occurs in the game
 *
 * @apiNote <p>The class is not extensible.</p>
 *<p>The class can not be instantiated</p>
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 * @version 2.0
 * @since 02/06/2023
 */
public final class MyShelfieAlertCreator {
    /**
     * A reference to the alert style sheet in the project
     * structure
     */
    private final static String ALERT_STYLE_SHEET = "/it.polimi.ingsw/gui/layout/MyShelfieAlert.css";

    /**
     * A reference to the error icon displayed with an
     * error alert message
     */
    private final static String ERROR_ICON = "/it.polimi.ingsw/graphical.resources/icons/error_cat_icon.png";

    /**
     * A reference to the warning icon displayed with
     * a warning alert message
     */
    private final static String WARNING_ICON = "/it.polimi.ingsw/graphical.resources/icons/warning_game_icon.png";

    /**
     * A reference to the information icon displayed
     * with an information alert message
     */
    private final static String INFORMATION_ICON = "/it.polimi.ingsw/graphical.resources/icons/info_book_icon.png";

    /**
     * Default constructor made private, so no one
     * can create an instance of this class
     */
    private MyShelfieAlertCreator(){
    }

    /**
     * Construct and display an alert window with
     * "My Shelfie" style
     *
     * @param alertType the type of alert to show
     * @param contentText the explanation text of
     *                    the alert
     * @param headerText the "title" of the alert
     * @param graphic a node that is displaying an
     *                image within the alert
     *                dialog
     * @return an alert window stylized with My
     * Shelfie style
     *
     * @apiNote the {@code alert} created is
     * returned as an object to allow callers
     * to perform optional action on the alert
     *
     * @see Alert
     */
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
                        MyShelfieAlertCreator.class.getResource(ALERT_STYLE_SHEET)
                ).toExternalForm());
        alert.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

        alert.showAndWait();

        return alert;
    }

    /**
     * Construct and display an error alert window
     * with "My Shelfie" style
     *
     * @param contentText the explanation text of
     *                    the alert
     * @param headerText the "title" of the alert
     * @param graphic a node that is displaying an
     *                image within the alert
     *                dialog
     * @return an error alert window
     */
    @NotNull
    public static Alert displayErrorAlert(String contentText, String headerText, Node graphic) {
        return displayAlert(Alert.AlertType.ERROR, contentText, headerText, graphic);
    }

    /**
     * Construct and display an error alert window
     * with "My Shelfie" style
     *
     * @param contentText the explanation text of
     *                    the alert
     * @param headerText the "title" of the alert
     * @return an error alert window
     */
    @NotNull
    public static Alert displayErrorAlert(String contentText, String headerText) {
        return displayErrorAlert(contentText, headerText, null);
    }

    /**
     * Construct and display an error alert window
     * with "My Shelfie" style
     *
     * @param contentText the explanation text of
     *                    the alert
     * @return an error alert window
     */
    @NotNull
    public static Alert displayErrorAlert(String contentText) {
        return displayErrorAlert(contentText, null);
    }

    /**
     * Construct and display an error alert window
     * with "My Shelfie" style
     *
     * @param e the exception that is displayed
     *          inside the alert
     *
     * @return an error alert window
     *
     * @apiNote <p>The title of the image displays
     * the cause of the exception if present</p>
     * <p>The body text of the image displays the
     * message of the exception</p>
     */
    @NotNull
    public static Alert displayErrorAlert(@NotNull Exception e) {
        return displayErrorAlert(e.getMessage(),
                (e.getCause() == null) ? null : e.getCause().toString());
    }

    /**
     * Construct and display a warning alert window
     * with "My Shelfie" style
     *
     * @param contentText the explanation text of
     *                    the alert
     * @param headerText the "title" of the alert
     * @param graphic a node that is displaying an
     *                image within the alert
     *                dialog
     * @return a warning alert window
     */
    @NotNull
    public static Alert displayWarningAlert(String contentText, String headerText, Node graphic) {
        return displayAlert(Alert.AlertType.WARNING, contentText, headerText, graphic);
    }

    /**
     * Construct and display a warning alert window
     * with "My Shelfie" style
     *
     * @param contentText the explanation text of
     *                    the alert
     * @param headerText the "title" of the alert
     * @return a warning alert window
     */
    @NotNull
    public static Alert displayWarningAlert(String contentText, String headerText) {
        return displayWarningAlert(contentText, headerText, null);
    }

    /**
     * Construct and display a warning alert window
     * with "My Shelfie" style
     *
     * @param contentText the explanation text of
     *                    the alert
     * @return a warning alert window
     */
    @NotNull
    public static Alert displayWarningAlert(String contentText) {
        return displayWarningAlert(contentText, null);
    }

    /**
     * Construct and display a warning alert window
     * with "My Shelfie" style
     *
     * @param e the exception that is displayed
     *          inside the alert
     *
     * @return a warning alert window
     *
     * @apiNote <p>The title of the image displays
     * the cause of the exception if present</p>
     * <p>The body text of the image displays the
     * message of the exception</p>
     */
    @NotNull
    public static Alert displayWarningAlert(@NotNull Exception e) {
        return displayWarningAlert(e.getMessage(), e.getCause().toString());
    }

    /**
     * Construct and display an information alert window
     * with "My Shelfie" style
     *
     * @param contentText the explanation text of
     *                    the alert
     * @param headerText the "title" of the alert
     * @param graphic a node that is displaying an
     *                image within the alert
     *                dialog
     * @return an information alert window
     */
    @NotNull
    public static Alert displayInformationAlert(String contentText, String headerText, Node graphic) {
        return displayAlert(Alert.AlertType.INFORMATION, contentText, headerText, graphic);
    }

    /**
     * Construct and display an information alert window
     * with "My Shelfie" style
     *
     * @param contentText the explanation text of
     *                    the alert
     * @param headerText the "title" of the alert
     * @return an information alert window
     */
    @NotNull
    public static Alert displayInformationAlert(String contentText, String headerText) {
        return displayInformationAlert(contentText, headerText, null);
    }

    /**
     * Construct and display an information alert window
     * with "My Shelfie" style
     *
     * @param contentText the explanation text of
     *                    the alert
     * @return an information alert window
     */
    @NotNull
    public static Alert displayInformationAlert(String contentText) {
        return displayInformationAlert(contentText, null);
    }

    /**
     * Construct and display an information alert window
     * with "My Shelfie" style
     *
     * @param e the exception that is displayed
     *          inside the alert
     *
     * @return an information alert window
     *
     * @apiNote <p>The title of the image displays
     * the cause of the exception if present</p>
     * <p>The body text of the image displays the
     * message of the exception</p>
     */
    @NotNull
    public static Alert displayInformationAlert(@NotNull Exception e) {
        return displayInformationAlert(e.getMessage(), e.getCause().toString());
    }
}
