package it.polimi.ingsw.view.gui.customcomponents.decorations;

import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

import static it.polimi.ingsw.utils.color.MyShelfieColor.BONE;

/**
 * Represents an inner lighting decoration for a component in the graphical user interface.
 * This class implements the `MyShelfieDecoration` interface.
 *<br>
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emamuele Valsecchi
 * @author Adem Shehi
 */
public class MyShelfieInnerLighting implements MyShelfieDecoration{

    private final static double AZIMUTH = -45.0;

    private final static double ELEVATION = 30.0;

    private final static Color LIGHT_COLOR_SOURCE = BONE.getLightenColor();

    private final Lighting lighting;

    /**
     * Constructs a new `MyShelfieInnerLighting` decoration.
     * It creates a lighting effect with a distant light source.
     */
    public MyShelfieInnerLighting() {
        lighting = new Lighting(new Light.Distant(AZIMUTH, ELEVATION, LIGHT_COLOR_SOURCE));
    }

    /**
     * <p>The method that defines a customization to be applied on a {@linkplain MyShelfieComponent component}</p>
     *
     * @param decoratedComponent the component that needs to be customized
     */
    @Override
    public void customize(@NotNull MyShelfieComponent decoratedComponent) {
        decoratedComponent.getCustomizedNode().setEffect(lighting);
    }
}
