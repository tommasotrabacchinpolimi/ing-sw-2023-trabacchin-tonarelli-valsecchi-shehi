package it.polimi.ingsw.view.gui.customcomponents.waitingpage;

import it.polimi.ingsw.view.gui.customcomponents.MyShelfieGraphicIcon;
import it.polimi.ingsw.view.gui.customcomponents.decorations.MyShelfieComponent;
import it.polimi.ingsw.view.gui.customcomponents.decorations.MyShelfieDarkShadow;
import it.polimi.ingsw.view.gui.customcomponents.decorations.MyShelfieDecoration;
import it.polimi.ingsw.view.gui.customcomponents.decorations.MyShelfieRoundEdge;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.utils.color.MyShelfieColor.BONE;

class MyShelfieLabel extends Label implements MyShelfieComponent {

    private static final String BACKGROUND_PATH = "/it.polimi.ingsw/graphical.resources/misc/page_base_darken.jpg";

    private static final double DEFAULT_PADDING_GRAPHIC = 1.7;

    /**
     * List that contains a series of default decorations
     * applied on a component
     *
     * @see MyShelfieDecoration
     */
    private final List<MyShelfieDecoration> baseDecorations = new ArrayList<>();

    public MyShelfieLabel(String text) {
        this(text, null);
    }

    public MyShelfieLabel(String text, String filePath) {
        super(text);

        setCSS();

        if(filePath != null)
            addGraphic(filePath);

        applyDecorationAsDefault(new MyShelfieRoundEdge(), new MyShelfieDarkShadow());
    }

    private void addGraphic(String filePath) {
        setGraphic(new MyShelfieGraphicIcon(filePath, DEFAULT_PADDING_GRAPHIC));
        setContentDisplay(ContentDisplay.RIGHT);
    }

    private void setCSS() {
        setStyle("-fx-background-image: url('" + getMyShelfieResource(BACKGROUND_PATH) + "');" +
                "-fx-background-position: center;" +
                "-fx-background-repeat: no-repeat;" +
                "-fx-background-size: contain;" +
                "-fx-font-family: 'Special Elite Regular';" +
                "-fx-wrap-text: true;" +
                "-fx-label-padding: 0.7em;" +
                "-fx-font-size: 2em;" +
                "-fx-graphic-text-gap: 1.3em;" +
                "-fx-text-fill: " + BONE.getRGBAStyleSheet(0.97) + " ;");
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
