package it.polimi.ingsw.view.gui.customcomponents.decorations;

import javafx.scene.paint.Color;

import static it.polimi.ingsw.utils.color.MyShelfieColor.GAMBOGE;

/**
 * <p>Direct implementation of the {@link MyShelfieShadow}
 * that creates a {@linkplain #LIGHT_SHADOW light} drop shadow
 * for custom {@link MyShelfieComponent components}</p>
 *
 * @apiNote Refer to {@link MyShelfieShadow} for more information
 *
 * @see MyShelfieShadow
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emamuele Valsecchi
 * @author Adem Shehi
 */
public class MyShelfieLightShadow extends MyShelfieShadow {

    /**
     * Default light color for the drop shadow effect
     */
    private static final Color LIGHT_SHADOW = GAMBOGE.getColor(0.93);

    /**
     * Creates a new standard instance of {@code MyShelfieLightShadow}
     */
    public MyShelfieLightShadow() {
        super(LIGHT_SHADOW);
    }
}