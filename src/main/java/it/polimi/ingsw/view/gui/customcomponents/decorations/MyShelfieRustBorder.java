package it.polimi.ingsw.view.gui.customcomponents.decorations;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;

import static it.polimi.ingsw.utils.color.MyShelfieColor.*;


/**
 * Represents a rust border decoration for a component in the graphical user interface.
 * This class implements the `MyShelfieDecoration` interface.
 * <br>
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emamuele Valsecchi
 * @author Adem Shehi
 */

public class MyShelfieRustBorder implements MyShelfieDecoration{

    /**
     * The blur type used for the border effect.
     */
    private static final BlurType BLUR_TYPE = BlurType.ONE_PASS_BOX;

    /**
     * The choke of the border effect.
     */
    private static final double CHOKE = 1.0;

    /**
     * The color of the border.
     */
    private static final Color BORDER_COLOR = RUST.getColor();

    /**
     * The offset of the border effect in the x and y direction.
     */
    private static final double OFFSET = 0.0;

    /**
     * The radius of the border effect.
     */
    private static final double RADIUS = 10.0;

    /**
     * The inner shadow border effect applied to the decorated component.
     */
    private final InnerShadow border;


    /**
     * Constructs a new `MyShelfieRustBorder` decoration.
     * It creates an inner shadow effect for the border.
     */
    public MyShelfieRustBorder() {
        border = new InnerShadow();
        border.setBlurType(BLUR_TYPE);
        border.setColor(BORDER_COLOR);
        border.setChoke(CHOKE);
        border.setOffsetX(OFFSET);
        border.setOffsetY(OFFSET);
    }

    /**
     * <p>The method that defines a customization to be applied on a {@linkplain MyShelfieComponent component}</p>
     *
     * @param decoratedComponent the component that needs to be customized
     */
    @Override
    public void customize(MyShelfieComponent decoratedComponent) {
        border.setInput(null);
        border.setRadius( getRadius(decoratedComponent.getCustomizedNode()));

        border.setInput(decoratedComponent.getCustomizedNode().getEffect());

        decoratedComponent.getCustomizedNode().setEffect(border);
    }

    /**
     * Returns the radius for the border effect based on the size of the decorated component.
     * If the component's width or height is zero, the default radius is used.
     *
     * @param decoratedComponent the component for which to calculate the radius
     * @return the radius for the border effect
     */
    private double getRadius(Node decoratedComponent) {
        Bounds componentBounds = decoratedComponent.getBoundsInLocal();

        return (componentBounds.getWidth() > 0.0 && componentBounds.getHeight() > 0.0) ? Math.min(componentBounds.getWidth(), componentBounds.getHeight()) / 10.0 : RADIUS;
    }
}
