package it.polimi.ingsw.view.gui.customcomponents.decorations;

import it.polimi.ingsw.view.gui.customcomponents.guitoolkit.MyShelfieShadowType;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

/**
 * <p>{@code MyShelfieShadow} effect is one of the
 * {@linkplain MyShelfieDecoration decorations} created for
 * custom components of the graphical user interface.</p>
 *
 * <p>A high-level effect that renders a shadow of the
 * given content behind the content with a:
 * <ul>
 *     <li>Standard color, decided by subclasses
 *     ({@link MyShelfieDarkShadow}, {@link MyShelfieLightShadow} </li>
 *     <li>Standard radius value defined in {@link MyShelfieShadowType}</li>
 * </ul>
 * </p>
 *
 * @see MyShelfieDarkShadow
 * @see MyShelfieLightShadow
 * @see DropShadow
 */
public abstract class MyShelfieShadow implements MyShelfieDecoration {

    /**
     * Represents the type of blur algorithm that is used to soften the shadow
     */
    private static final BlurType BLUR_TYPE = BlurType.GAUSSIAN;

    /**
     * The spread of the shadow
     */
    private static final double SPREAD = 0.0;

    /**
     * The shadow offset in the x direction, in pixels
     */
    private static final double OFFSET_X = 0.0;

    /**
     * The shadow offset in the y direction, in pixels
     */
    private static final double OFFSET_Y = 0.0;

    /**
     * The drop shadow effect applied to the desired {@linkplain MyShelfieComponent component}
     */
    private final DropShadow dropShadow;

    /**
     * Creates a MyShelfieShadow with specified color
     *
     * @param color the shadow Color
     */
    protected MyShelfieShadow(Color color) {
        this(color, MyShelfieShadowType.NORMAL);
    }

    /**
     * Creates a MyShelfieShadow with specified radius and color
     *
     * @param color  the shadow Color
     * @param shadowType the radius of the shadow blur kernel
     */
    protected MyShelfieShadow(Color color, MyShelfieShadowType shadowType) {
        dropShadow = new DropShadow(BLUR_TYPE, color, shadowType.getRadius(), SPREAD, OFFSET_X, OFFSET_Y);
    }

    /**
     * Applies a drop shadow effect on the desired component
     *
     * @param decoratedComponent the component that needs to be customized
     */
    @Override
    public void customize(MyShelfieComponent decoratedComponent) {
        dropShadow.setInput(null);

        dropShadow.setInput(decoratedComponent.getCustomizedNode().getEffect());

        decoratedComponent.getCustomizedNode().setEffect(dropShadow);
    }
}
