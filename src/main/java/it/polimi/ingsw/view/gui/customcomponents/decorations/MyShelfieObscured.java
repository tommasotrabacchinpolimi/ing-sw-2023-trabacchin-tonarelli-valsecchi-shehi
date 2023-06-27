package it.polimi.ingsw.view.gui.customcomponents.decorations;

import javafx.scene.effect.ColorAdjust;

/**
 * <p>{@code MyShelfieObscured} effect is one of the
 * {@linkplain MyShelfieDecoration decorations} created
 * for custom components of the graphical user interface.</p>
 *
 * <p>This effect modifies per-pixel adjustments of hue,
 * saturation, brightness, and contrast to show the
 * designed {@link MyShelfieComponent component} to appear
 * darken</p>
 *
 * @see ColorAdjust
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emamuele Valsecchi
 * @author Adem Shehi
 */
public class MyShelfieObscured implements MyShelfieDecoration {

    /**
     * The hue adjustment value
     */
    private static final double HUE = 0.0;

    /**
     * The saturation adjustment value
     */
    private static final double SATURATION = 0.0;

    /**
     * The brightness adjustment value
     */
    private static final double BRIGHTNESS = -0.37;

    /**
     * The contrast adjustment value
     */
    private static final double CONTRAST = 0.0;

    /**
     * The obscuration effect applied to the
     * desired {@linkplain MyShelfieComponent component}
     */
    private final ColorAdjust obscuration;

    /**
     * Creates a new instance of {@code MyShelfieObscured} with default parameters
     */
    public MyShelfieObscured() {
        this(BRIGHTNESS);
    }

    /**
     * Creates a new instance of {@code MyShelfieObscured} with
     * the obscuration passed as parameter.
     *
     * @apiNote <p>Please note that obscuration should be a
     * negative value between {@code 0.0} and {@code -1.0}.</p>
     * <p>If a positive value is passed, it is automatically
     * considered negative</p>
     *
     * @param obscuration
     */
    public MyShelfieObscured(double obscuration) {
        if(obscuration > 0)
            obscuration = -obscuration;

        if(obscuration < -1.0)
            obscuration = -1.0;

        this.obscuration = new ColorAdjust(HUE, SATURATION, obscuration, CONTRAST);
    }

    /**
     * Applies an obscuration on the desired component
     *
     * @param decoratedComponent the component that needs to be customized
     */
    @Override
    public void customize(MyShelfieComponent decoratedComponent) {
        obscuration.setInput(null);

        obscuration.setInput(decoratedComponent.getCustomizedNode().getEffect());

        decoratedComponent.getCustomizedNode().setEffect(obscuration);
    }
}
