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

    private static final BlurType BLUR_TYPE = BlurType.ONE_PASS_BOX;

    private static final double CHOKE = 1.0;

    private static final Color BORDER_COLOR = RUST.getColor();

    private static final double OFFSET = 0.0;

    private static final double RADIUS = 10.0;

    private final InnerShadow border;

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

    private double getRadius(Node decoratedComponent) {
        Bounds componentBounds = decoratedComponent.getBoundsInLocal();

        return (componentBounds.getWidth() > 0.0 && componentBounds.getHeight() > 0.0) ? Math.min(componentBounds.getWidth(), componentBounds.getHeight()) / 10.0 : RADIUS;
    }
}
