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
 *
 * The MyShelfieGraphicIcon class represents a graphical icon with "My Shelfie" style.
 * It extends the JavaFX Pane class and implements the MyShelfieComponent interface.
 * <br>
 * @version 1.0
 * @since 08/06/2023
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
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

    /**
     * Creates a MyShelfieGraphicIcon with the specified icon file path and padding.
     *
     * @param iconFilePath the file path of the icon image
     * @param padding      the padding to be applied to the icon
     */
    public MyShelfieGraphicIcon(String iconFilePath, double padding) {
        this(iconFilePath, padding, DEF_ROUND_EDGE, DEF_GRAPHIC_SIZE);
    }


    /**
     * Creates a MyShelfieGraphicIcon with the specified icon file path, padding, round edge type, and graphic size.
     *
     * @param iconFilePath   the file path of the icon image
     * @param padding        the padding to be applied to the icon
     * @param roundEdgeType  the round edge type to be applied to the icon
     * @param graphicSize    the graphic size to be applied to the icon
     */
    public MyShelfieGraphicIcon(String iconFilePath, double padding, MyShelfieRoundEdgeType roundEdgeType, String graphicSize) {

        this(iconFilePath, null, roundEdgeType, graphicSize, padding);
    }

    /**
     * Creates a MyShelfieGraphicIcon with the specified icon file path, support file path, and padding.
     *
     * @param iconFilePath       the file path of the icon image
     * @param supportFilePath    the file path of the support image
     * @param padding            the padding to be applied to the icon
     */
    public MyShelfieGraphicIcon(String iconFilePath, String supportFilePath, double padding) {
        this(iconFilePath, supportFilePath, DEF_ROUND_EDGE, DEF_GRAPHIC_SIZE, padding);
    }

    /**
     * Creates a MyShelfieGraphicIcon with the specified parameters.
     *
     * @param iconFilePath       the file path of the icon image
     * @param supportFilePath    the file path of the support image
     * @param roundEdgeType      the round edge type to be applied to the icon
     * @param graphicSize        the graphic size to be applied to the icon
     * @param padding            the padding to be applied to the icon
     */
    protected MyShelfieGraphicIcon(String iconFilePath,
                                 String supportFilePath,
                                 MyShelfieRoundEdgeType roundEdgeType,
                                 String graphicSize,
                                 double padding) {
        this(iconFilePath, supportFilePath, roundEdgeType, graphicSize, padding, padding, padding, padding);
    }

    /**
     * Creates a MyShelfieGraphicIcon with the specified parameters.
     *
     * @param iconFilePath       the file path of the icon image
     * @param supportFilePath    the file path of the support image
     * @param roundEdgeType      the round edge type to be applied to the icon
     * @param paddingTop         the top padding to be applied to the icon
     * @param paddingRight       the right padding to be applied to the icon
     * @param paddingBottom      the bottom padding to be applied to the icon
     * @param paddingLeft        the left padding to be applied to the icon
     */
    public MyShelfieGraphicIcon(String iconFilePath,
                                   String supportFilePath,
                                   MyShelfieRoundEdgeType roundEdgeType,
                                   double paddingTop,
                                   double paddingRight,
                                   double paddingBottom,
                                   double paddingLeft) {
        this(iconFilePath, supportFilePath, roundEdgeType, DEF_GRAPHIC_SIZE, paddingTop, paddingRight, paddingBottom, paddingLeft);
    }

    /**
     * Creates a MyShelfieGraphicIcon with the specified parameters.
     *
     * @param iconFilePath       the file path of the icon image
     * @param supportFilePath    the file path of the support image
     * @param roundEdgeType      the round edge type to be applied to the icon
     * @param graphicSize        the graphic size to be applied to the icon
     * @param paddingTop         the top padding to be applied to the icon
     * @param paddingRight       the right padding to be applied to the icon
     * @param paddingBottom      the bottom padding to be applied to the icon
     * @param paddingLeft        the left padding to be applied to the icon
     */
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

    /**
     * Sets the CSS properties for the MyShelfieGraphicIcon.
     *
     * @param iconFileURL    the URL of the icon image
     * @param graphicSize    the graphic size to be applied to the icon
     * @param paddingTop     the top padding to be applied to the icon
     * @param paddingRight   the right padding to be applied to the icon
     * @param paddingBottom  the bottom padding to be applied to the icon
     * @param paddingLeft    the left padding to be applied to the icon
     */
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
