package it.polimi.ingsw.view.gui;

import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

/**
 * <p>Represent each controller inside the My Shelfie application.</p>
 * <p>More precisely this class is used to store every controller created for any javafx class</p>
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 * @version 2.0
 * @see MyShelfieApplication
 * @see Initializable
 * @since 22/05/2023
 */
public abstract class MyShelfieController implements Initializable {

    private final static String ALERT_STYLE_SHEET = "/it.polimi.ingsw/layout/MyShelfieErrorAlert.css";

    private final static String ERROR_ICON = "/it.polimi.ingsw/graphical.resources/icons/error_cat_icon.png";

    private MyShelfieApplication myShelfieApplicationLauncher;

    public MyShelfieApplication getMyShelfieApplicationLauncher() {
        return myShelfieApplicationLauncher;
    }

    public void setMyShelfieApplicationLauncher(MyShelfieApplication myShelfieApplicationLauncher) {
        this.myShelfieApplicationLauncher = myShelfieApplicationLauncher;
    }

    public abstract void onGameStateChangedNotified();

    public abstract void onExceptionNotified();

    private static Node getDefaultGraphic() {
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

    public static void displayErrorAlert(String contentText, String headerText, Node graphic) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(contentText);
        alert.setHeaderText(headerText);

        if(graphic == null)
            graphic = getDefaultGraphic();

        alert.setGraphic(graphic);

        alert.getDialogPane().getStylesheets().add(
                Objects.requireNonNull(
                        MyShelfieController.class.getResource(ALERT_STYLE_SHEET)
                ).toExternalForm());

        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }

    public static void displayErrorAlert(String contentText, String headerText) {
        displayErrorAlert(contentText, headerText, null);
    }

    public static void displayErrorAlert(String contentText) {
        displayErrorAlert(contentText, null);
    }

    public static void displayErrorAlert(Exception e) {
        displayErrorAlert(e.getMessage(), e.getCause().toString());
    }
}
