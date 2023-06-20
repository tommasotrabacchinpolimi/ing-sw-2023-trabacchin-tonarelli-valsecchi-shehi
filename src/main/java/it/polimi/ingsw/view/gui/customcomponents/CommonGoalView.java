package it.polimi.ingsw.view.gui.customcomponents;

import it.polimi.ingsw.utils.CommonGoalDeserializer;
import it.polimi.ingsw.view.gui.customcomponents.decorations.MyShelfieComponent;
import it.polimi.ingsw.view.gui.customcomponents.decorations.MyShelfieDarkShadow;
import it.polimi.ingsw.view.gui.customcomponents.decorations.MyShelfieDecoration;
import it.polimi.ingsw.view.gui.customcomponents.decorations.MyShelfieRoundEdge;
import it.polimi.ingsw.view.gui.customcomponents.guitoolkit.MyShelfieRoundEdgeType;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CommonGoalView extends StackPane implements MyShelfieComponent {

    private static final String COMMON_GOAL_IMAGE_PATH = "/it.polimi.ingsw/graphical.resources/common.goal.cards/";

    private static final String ERROR_COMMON_GOAL_IMAGE = "error_common_goal";

    private final String description;

    /**
     * List that contains a series of default decorations
     * applied on a component
     *
     * @see MyShelfieDecoration
     */
    private final List<MyShelfieDecoration> baseDecorations = new ArrayList<>();

    /**
     * Creates a CommonGoalView layout with default CENTER alignment.
     */
    public CommonGoalView(String description) {
        super();

        this.description = description;

        try {
            setCSS(CommonGoalDeserializer.getCommonGoalImage(description));
        } catch (FileNotFoundException e) {
            MyShelfieAlertCreator.displayErrorAlert(
                    "The image corresponding to the common goal chosen can not be found, you have probably compromised the application structure",
                    "Error in loading common goal card image"
            );

            setCSS(ERROR_COMMON_GOAL_IMAGE);
        }

        applyDecorationAsDefault(new MyShelfieRoundEdge(MyShelfieRoundEdgeType.SMALL), new MyShelfieDarkShadow());

        setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
    }

    private void setCSS(String fileName) {
        setStyle("-fx-background-image: url('" + getImageFile(fileName)  + "');" +
                "-fx-background-position: center;" +
                "-fx-background-repeat: no-repeat;" +
                "-fx-background-size: cover;" +
                "-fx-padding: 6em 9em 6em 9em;");
    }

    private URL getImageFile(String fileName) {

        URL fileURL = getClass().getResource( COMMON_GOAL_IMAGE_PATH + fileName + ".jpg");

        if(fileURL == null) {
            System.err.println("Common goal image source not found");
        }

        return fileURL;
    }

    /**
     * Retrieve the element that has to be customized
     *
     * @return the element that needs to be customized
     */
    @Override
    public Node getCustomizedNode() {
        return this;
    }

    /**
     * Retrieves the list of default decorations
     * that are applied on a customized
     * {@linkplain MyShelfieComponent component}
     *
     * @return the list of default decorations
     */
    @Override
    public List<MyShelfieDecoration> getBaseDecorations() {
        return baseDecorations;
    }
}
