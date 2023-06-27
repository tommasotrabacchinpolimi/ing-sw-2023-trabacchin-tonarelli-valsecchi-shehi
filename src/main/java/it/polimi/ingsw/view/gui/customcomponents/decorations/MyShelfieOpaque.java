package it.polimi.ingsw.view.gui.customcomponents.decorations;

import javafx.scene.Node;

/**
 * <p>{@code MyShelfieObscured} effect is one of the
 * {@linkplain MyShelfieDecoration decorations} created
 * for custom components of the graphical user interface.</p>
 *
 * <p>This effect modifies the opacity of the element</p>
 *
 * @see Node#setOpacity(double)
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emamuele Valsecchi
 * @author Adem Shehi
 */
public class MyShelfieOpaque implements MyShelfieDecoration {

    /**
     * Standard value of the property opacity
     */
    public static final double OPACITY = 0.37;

    /**
     * Specifies how opaque the
     * {@link MyShelfieComponent component} will
     * appear.
     *
     * @apiNote Opacity is specified as a value
     * between 0 and 1. Values less than 0 are
     * treated as 0, values greater than 1 are
     * treated as 1
     */
    private final double opacity;

    /**
     * <p>Creates a new instance of {@code MyShelfieOpaque}
     * with desired {@code opacity}</p>
     *
     * @param opacity specifies how opaque (that is, solid) the Node appears
     */
    public MyShelfieOpaque(double opacity) {
        this.opacity = opacity;
    }

    /**
     * <p>Creates a new instance of {@code MyShelfieOpaque}
     * with standard {@linkplain #OPACITY opacity}</p>
     */
    public MyShelfieOpaque() {
        this(OPACITY);
    }

    /**
     * Applies an opacity on the desired component
     *
     * @param decoratedComponent the component that needs to be customized
     */
    @Override
    public void customize(MyShelfieComponent decoratedComponent) {
        decoratedComponent.getCustomizedNode().setOpacity(opacity);
    }
}
