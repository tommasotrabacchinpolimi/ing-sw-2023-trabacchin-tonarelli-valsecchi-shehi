package it.polimi.ingsw.view.gui.customcomponents;

import it.polimi.ingsw.view.gui.customcomponents.decorations.MyShelfieComponent;
import it.polimi.ingsw.view.gui.customcomponents.decorations.MyShelfieDarkShadow;
import it.polimi.ingsw.view.gui.customcomponents.decorations.MyShelfieDecoration;
import it.polimi.ingsw.view.gui.customcomponents.decorations.MyShelfieRoundEdge;
import it.polimi.ingsw.view.gui.customcomponents.guitoolkit.MyShelfieRoundEdgeType;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @since 08/06/2023
 */
public class MyShelfieGraphicIcon extends Pane implements MyShelfieComponent {

    /**
     * List that contains a series of default decorations
     * applied on a component
     *
     * @see MyShelfieDecoration
     */
    private final List<MyShelfieDecoration> baseDecorations = new ArrayList<>();

    MyShelfieGraphicIcon(String iconFilePath, double padding, boolean round, String graphicSize) {
        setCSS(iconFilePath, padding, graphicSize);

        if(round)
            applyDecorationAsDefault(new MyShelfieRoundEdge(MyShelfieRoundEdgeType.MINIMUM));

        applyDecorationAsDefault(new MyShelfieDarkShadow());
    }

    public MyShelfieGraphicIcon(String iconFilePath, double padding, String graphicSize) {
        this(iconFilePath, padding, true, graphicSize);
    }

    public MyShelfieGraphicIcon(String iconFilePath, double padding) {
        this(iconFilePath, padding, true, "cover");
    }

    private void setCSS(String iconFilePath, double padding, String graphicSize) {

        if(!allowedGraphicSize(graphicSize))
            graphicSize = "cover";

        setStyle("-fx-background-image: url('" + getClass().getResource(iconFilePath) + "');" +
                "-fx-background-repeat: no-repeat;" +
                "-fx-background-position: center;" +
                "-fx-background-size: " + graphicSize + ";" +
                "-fx-padding: " + padding + "em;");
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
     * @return {@code true} if the cover size of
     * the image is allowed, {@code false} otherwise
     */
    private boolean allowedGraphicSize(String graphicSize){
        return graphicSize.equals("cover") ||
                graphicSize.equals("contain") ||
                graphicSize.equals("auto") ||
                graphicSize.equals("stretch");
    }
}
