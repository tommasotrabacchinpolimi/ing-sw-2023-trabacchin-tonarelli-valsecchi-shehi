package it.polimi.ingsw.view.gui.customcomponents;

import it.polimi.ingsw.view.gui.customcomponents.decorations.MyShelfieComponent;
import it.polimi.ingsw.view.gui.customcomponents.decorations.MyShelfieDarkShadow;
import it.polimi.ingsw.view.gui.customcomponents.decorations.MyShelfieDecoration;
import it.polimi.ingsw.view.gui.customcomponents.decorations.MyShelfieRoundEdge;
import it.polimi.ingsw.view.gui.customcomponents.guitoolkit.MyShelfieRoundEdgeType;
import javafx.beans.NamedArg;
import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MyShelfieChoiceBox extends ChoiceBox<String> implements MyShelfieComponent {

    private static final String CHOICE_BOX_CSS_FILE_PATH = "/it.polimi.ingsw/gui/layout/MyShelfieChoiceBox.css";

    private static final String MY_SHELFIE_CHOICE_BOX_STYLE_CLASS = "my-shelfie-choice-box";

    private final PseudoClass errorClass = PseudoClass.getPseudoClass("error");

    /**
     * List that contains a series of default decorations
     * applied on a component
     *
     * @see MyShelfieDecoration
     */
    private final List<MyShelfieDecoration> baseDecorations = new ArrayList<>();

    public MyShelfieChoiceBox(@NamedArg("initValue") String value) {
        super();

        setValue(value);

        applyDecorationAsDefault(new MyShelfieRoundEdge(MyShelfieRoundEdgeType.SMALL), new MyShelfieDarkShadow());

        getStylesheets().add(Objects.requireNonNull(getMyShelfieResource(CHOICE_BOX_CSS_FILE_PATH)).toExternalForm());

        getStyleClass().add(MY_SHELFIE_CHOICE_BOX_STYLE_CLASS);
    }

    /**
     * Create a new ChoiceBox which has an empty list of items.
     */
    public MyShelfieChoiceBox() {
        super();

        applyDecorationAsDefault(new MyShelfieRoundEdge(MyShelfieRoundEdgeType.SMALL), new MyShelfieDarkShadow());

        getStylesheets().add(Objects.requireNonNull(getMyShelfieResource(CHOICE_BOX_CSS_FILE_PATH)).toExternalForm());

        getStyleClass().add(MY_SHELFIE_CHOICE_BOX_STYLE_CLASS);
    }

    public void setErrorStyle(String valueText) {
        setValue(valueText);
        pseudoClassStateChanged(errorClass, true);
    }

    public void removeErrorStyle() {
        pseudoClassStateChanged(errorClass, false);
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
