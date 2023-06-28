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


/**
 * Customized ChoiceBox component with MyShelfie styling and decorations.
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */

public class MyShelfieChoiceBox extends ChoiceBox<String> implements MyShelfieComponent {

    /**
     * Path for the choice box CSS file.
     */
    private static final String CHOICE_BOX_CSS_FILE_PATH = "/it.polimi.ingsw/gui/layout/MyShelfieChoiceBox.css";

    /**
     * Path for MyShelfie choice box CSS file.
     */
    private static final String MY_SHELFIE_CHOICE_BOX_STYLE_CLASS = "my-shelfie-choice-box";


    /**
     * Pseudo-class representing the "error" state of the MyShelfieChoiceBox.
     * This pseudo-class is used to indicate an erroneous value in the ChoiceBox.
     */
    private final PseudoClass errorClass = PseudoClass.getPseudoClass("error");

    /**
     * List that contains a series of default decorations
     * applied on a component
     *
     * @see MyShelfieDecoration
     */
    private final List<MyShelfieDecoration> baseDecorations = new ArrayList<>();

    /**
     * Create a new ChoiceBox which has an empty list of items.
     */
    public MyShelfieChoiceBox() {
        this(null);
    }

    /**
     * Creates a new ChoiceBox with the specified initial value.
     *
     * @param initValue The initial value for the ChoiceBox.
     */
    public MyShelfieChoiceBox(@NamedArg("initValue") String initValue) {
        super();

        if(initValue != null) {
            setValue(initValue);
            getItems().add(initValue);
        }

        applyDecorationAsDefault(new MyShelfieRoundEdge(MyShelfieRoundEdgeType.SMALL), new MyShelfieDarkShadow());

        getStylesheets().add(Objects.requireNonNull(getMyShelfieResource(CHOICE_BOX_CSS_FILE_PATH)).toExternalForm());

        getStyleClass().add(MY_SHELFIE_CHOICE_BOX_STYLE_CLASS);
    }

    /**
     * Sets the error style for the ChoiceBox, indicating an erroneous value.
     * The specified value will be set as the current value, and the error pseudo-class will be activated.
     *
     * @param valueText The erroneous value to be set.
     */
    public void setErrorStyle(String valueText) {
        setValue(valueText);
        pseudoClassStateChanged(errorClass, true);
    }

    /**
     * Removes the error style from the ChoiceBox, resetting it to the default style.
     * The error pseudo-class will be deactivated.
     */
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
