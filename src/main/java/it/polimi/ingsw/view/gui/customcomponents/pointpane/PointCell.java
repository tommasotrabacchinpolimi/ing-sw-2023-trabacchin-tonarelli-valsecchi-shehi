package it.polimi.ingsw.view.gui.customcomponents.pointpane;

import it.polimi.ingsw.view.gui.customcomponents.decorations.*;
import it.polimi.ingsw.view.gui.customcomponents.guitoolkit.MyShelfieAlertCreator;
import it.polimi.ingsw.view.gui.customcomponents.guitoolkit.MyShelfieRoundEdgeType;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.utils.color.MyShelfieColor.BONE;
import static it.polimi.ingsw.utils.color.MyShelfieColor.DARK_LAVA;

class PointCell extends StackPane implements MyShelfieComponent {

    private static final double DEFAULT_PADDING = 2.7;

    /**
     * List that contains a series of default decorations
     * applied on a component
     *
     * @see MyShelfieDecoration
     */
    private final List<MyShelfieDecoration> baseDecorations = new ArrayList<>();

    /**
     * Creates a PointCell layout with default CENTER alignment.
     */
    public PointCell() {
        super();

        setCSS(DEFAULT_PADDING);

        applyDecorationAsDefault(new MyShelfieRoundEdge(MyShelfieRoundEdgeType.MINIMUM), new MyShelfieDarkShadow());

        applyDecoration(new MyShelfieInnerLighting());

        getChildren().addListener((ListChangeListener<? super Node>) change -> {
            while(change.next()) {
                if(change.wasAdded()) {
                    if(getChildren().size() == 1){
                        setCSS(0.5);
                        resetToDefaultDecorations();
                    }else {
                        MyShelfieAlertCreator.displayErrorAlert(
                                "Cannot insert more than one scoring token in a point cell",
                                "Scoring Token cannot be insert"
                        );
                    }
                }
            }
        });
    }

    private void setCSS(double padding) {
        setStyle("-fx-background-color: " + DARK_LAVA.getDarkenRGBAStyleSheet(0.76) + " ;" +
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
