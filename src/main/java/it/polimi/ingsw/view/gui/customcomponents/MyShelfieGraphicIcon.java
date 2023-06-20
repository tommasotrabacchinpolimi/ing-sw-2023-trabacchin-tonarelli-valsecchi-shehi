package it.polimi.ingsw.view.gui.customcomponents;

import it.polimi.ingsw.view.gui.customcomponents.decorations.MyShelfieComponent;
import it.polimi.ingsw.view.gui.customcomponents.decorations.MyShelfieDarkShadow;
import it.polimi.ingsw.view.gui.customcomponents.decorations.MyShelfieDecoration;
import it.polimi.ingsw.view.gui.customcomponents.decorations.MyShelfieRoundEdge;
import it.polimi.ingsw.view.gui.customcomponents.guitoolkit.MyShelfieRoundEdgeType;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @since 08/06/2023
 */
public class MyShelfieGraphicIcon extends Pane implements MyShelfieComponent {

    /**
     * Defines the default value for round edge of a
     * graphical icon with "My Shelfie" style
     */
    protected static final MyShelfieRoundEdgeType DEF_ROUND_EDGE = MyShelfieRoundEdgeType.MINIMUM;

    /**
     * <p>Defines the default "graphic size" for the
     * icon created that will "{@code cover}" the
     * entire region of the graphical component</p>
     * <p><a href="https://www.w3.org/TR/css-backgrounds-3/#propdef-background-size">
     *     See CSS documentation for more information</a></p>
     */
    protected static final String DEF_GRAPHIC_SIZE = "cover";

    /**
     * Used to display a default error message when
     * the resource specified as icon is not found
     */
    private static final String DEF_ERROR_MESSAGE = "Error in loading shelfie icon";

    /**
     * List that contains a series of default decorations
     * applied on a component
     *
     * @see MyShelfieDecoration
     */
    private final List<MyShelfieDecoration> baseDecorations = new ArrayList<>();

    public MyShelfieGraphicIcon(String iconFilePath, double padding) {
        this(iconFilePath, padding, DEF_ROUND_EDGE, DEF_GRAPHIC_SIZE);
    }

    public MyShelfieGraphicIcon(String iconFilePath, double padding, MyShelfieRoundEdgeType roundEdgeType, String graphicSize) {

        this(iconFilePath, null, roundEdgeType, graphicSize, padding);
    }

    public MyShelfieGraphicIcon(String iconFilePath, String supportFilePath, double padding) {
        this(iconFilePath, supportFilePath, DEF_ROUND_EDGE, DEF_GRAPHIC_SIZE, padding);
    }

    protected MyShelfieGraphicIcon(String iconFilePath,
                                 String supportFilePath,
                                 MyShelfieRoundEdgeType roundEdgeType,
                                 String graphicSize,
                                 double padding) {
        this(iconFilePath, supportFilePath, roundEdgeType, graphicSize, padding, padding, padding, padding);
    }

    public MyShelfieGraphicIcon(String iconFilePath,
                                   String supportFilePath,
                                   MyShelfieRoundEdgeType roundEdgeType,
                                   double paddingTop,
                                   double paddingRight,
                                   double paddingBottom,
                                   double paddingLeft) {
        this(iconFilePath, supportFilePath, roundEdgeType, DEF_GRAPHIC_SIZE, paddingTop, paddingRight, paddingBottom, paddingLeft);
    }

    protected MyShelfieGraphicIcon(String iconFilePath,
                                String supportFilePath,
                                MyShelfieRoundEdgeType roundEdgeType,
                                String graphicSize,
                                double paddingTop,
                                double paddingRight,
                                double paddingBottom,
                                double paddingLeft) {
        if(supportFilePath == null) {
            setCSS(getMyShelfieResource(iconFilePath, DEF_ERROR_MESSAGE), graphicSize, paddingTop, paddingRight, paddingBottom, paddingLeft);
        }else {
            setCSS(getMyShelfieResource(iconFilePath, supportFilePath, DEF_ERROR_MESSAGE), graphicSize, paddingTop, paddingRight, paddingBottom, paddingLeft);
        }

        if(roundEdgeType != null)
            applyDecorationAsDefault(new MyShelfieRoundEdge(roundEdgeType));

        applyDecorationAsDefault(new MyShelfieDarkShadow());
    }

    private void setCSS(URL iconFileURL, String graphicSize, double paddingTop, double paddingRight, double paddingBottom, double paddingLeft) {

        if(!allowedGraphicSize(graphicSize))
            graphicSize = DEF_GRAPHIC_SIZE;

        setStyle("-fx-background-image: url('" + iconFileURL + "');" +
                "-fx-background-repeat: no-repeat;" +
                "-fx-background-position: center;" +
                "-fx-background-size: " + graphicSize + ";" +
                "-fx-padding: " + paddingTop + "em " + paddingRight + "em " + paddingBottom +"em " + paddingLeft +"em;");
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
     * Retrieves the set of default decorations
     * that are applied on a customized
     * {@linkplain MyShelfieComponent component}
     *
     * @return the set
     */
    @Override
    public List<MyShelfieDecoration> getBaseDecorations() {
        return baseDecorations;
    }

    /**
     * Verify if the background size chosen can be
     * applied to the background image
     *
     * @param graphicSize the graphic size to be
     *                    applied on the background
     *                    image
     * @return {@code true} if the graphic size of
     * the image is allowed, {@code false} otherwise
     */
    private boolean allowedGraphicSize(String graphicSize){
        return graphicSize.equals(DEF_GRAPHIC_SIZE) ||
                graphicSize.equals("contain") ||
                graphicSize.equals("auto") ||
                graphicSize.equals("stretch");
    }
}
