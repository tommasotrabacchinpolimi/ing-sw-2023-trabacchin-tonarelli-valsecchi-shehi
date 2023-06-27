package it.polimi.ingsw.view.gui.customcomponents.decorations;

import javafx.scene.effect.Glow;

/**
 * <p>{@code MyShelfieGlow} effect is one of the
 * {@linkplain MyShelfieDecoration decorations} created
 * for custom components of the graphical user interface.</p>
 *
 * <p>A high-level effect that makes the input image
 * appear to glow, based on a configurable level</p>
 *
 * <p>The glow can be applied:
 * <ul>
 *     <li>with a standard level ({@value LEVEL})</li>
 *     <li>with a personalized level ({@link #MyShelfieGlow(double level)})</li>
 * </ul>
 * </p>
 *
 * @see Glow
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emamuele Valsecchi
 * @author Adem Shehi
 */
public class MyShelfieGlow implements MyShelfieDecoration{

    /**
     * Standard level value, which defines the intensity of the glow effect.
     */
    private static final double LEVEL = 0.2;

    /**
     * The glow effect applied to the desired {@linkplain MyShelfieComponent component}
     */
    private final Glow glow;

    /**
     * Creates a new instance of Glow with specified level
     *
     * @param level the level value, which controls the intensity of the glow effect
     */
    public MyShelfieGlow(double level) {
        glow = new Glow(level);
    }

    /**
     * Creates a new instance of Glow with default parameters.
     */
    public MyShelfieGlow() {
        this(LEVEL);
    }

    /**
     * Applies a glow effect on the desired component
     *
     * @param decoratedComponent the component that needs to be customized
     */
    @Override
    public void customize(MyShelfieComponent decoratedComponent) {
        glow.setInput(null);

        glow.setInput(decoratedComponent.getCustomizedNode().getEffect());

        decoratedComponent.getCustomizedNode().setEffect(glow);
    }
}
