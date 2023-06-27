package it.polimi.ingsw.view.gui.customcomponents.decorations;

import it.polimi.ingsw.view.gui.customcomponents.guitoolkit.MyShelfieShadowType;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

import static it.polimi.ingsw.utils.color.MyShelfieColor.GAMBOGE;

/**
 * <p>{@code MyShelfieInnerShadow} effect is one of the
 * {@linkplain MyShelfieDecoration decorations} created
 * for custom components of the graphical user interface.</p>
 *
 * <p>A high-level effect that renders a shadow inside the
 * edges of the given content with a standard color and radius</p>
 *
 * @see InnerShadow
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emamuele Valsecchi
 * @author Adem Shehi
 */
public class MyShelfieInnerShadow implements MyShelfieDecoration{

    /**
     * The algorithm used to blur the shadow
     */
    private static final BlurType BLUR_TYPE = BlurType.GAUSSIAN;

    /**
     * The shadow Color
     */
    private static final Color COLOR = GAMBOGE.getLightenColor(0.93);

    /**
     * The choke of the shadow
     */
    private static final double CHOKE = 0.0;

    /**
     * The shadow offset in the x direction, in pixels
     */
    private static final double OFFSET_X = 0.0;

    /**
     * The shadow offset in the y direction, in pixels
     */
    private static final double OFFSET_Y = 0.0;

    /**
     * The inner effect applied to the desired {@linkplain MyShelfieComponent component}
     */
    private final InnerShadow innerShadow;

    /**
     * <p>Creates a new instance of a standard inner shadow</p>
     */
    public MyShelfieInnerShadow() {
        this.innerShadow = new InnerShadow(BLUR_TYPE, COLOR, MyShelfieShadowType.NORMAL.getRadius(), CHOKE, OFFSET_X, OFFSET_Y);
    }

    /**
     * Applies an inner shadow effect on the desired component
     *
     * @param decoratedComponent the component that needs to be customized
     */
    @Override
    public void customize(@NotNull MyShelfieComponent decoratedComponent) {
        innerShadow.setInput(null);

        innerShadow.setInput(decoratedComponent.getCustomizedNode().getEffect());

        decoratedComponent.getCustomizedNode().setEffect(innerShadow);
    }
}
