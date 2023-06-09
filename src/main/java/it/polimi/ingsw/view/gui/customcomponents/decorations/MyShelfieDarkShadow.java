package it.polimi.ingsw.view.gui.customcomponents.decorations;

import it.polimi.ingsw.utils.color.MyShelfieColor;
import it.polimi.ingsw.view.gui.customcomponents.guitoolkit.MyShelfieShadowType;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

import static it.polimi.ingsw.utils.color.MyShelfieColor.KOBICHA;

/**
 * <p>Direct implementation of the {@link MyShelfieShadow}
 * that creates a {@linkplain #DARK_SHADOW dark} drop shadow
 * for custom {@link MyShelfieComponent components}</p>
 *
 * @apiNote Refer to {@link MyShelfieShadow} for more information
 *
 * @see MyShelfieShadow
 */
public class MyShelfieDarkShadow extends MyShelfieShadow {

    /**
     * Default dark color for the drop shadow effect
     *
     * @apiNote The color is chosen from the
     * {@linkplain MyShelfieColor colors} chosen for
     * the My Shelfie application
     */
    private static final Color DARK_SHADOW = KOBICHA.getColor(0.97);

    /**
     * Creates a new standard instance of {@code MyShelfieDarkShadow}
     */
    public MyShelfieDarkShadow() {
        super(DARK_SHADOW);
    }

    /**
     * Creates a new instance of MyShelfieDarkShadow the specified radius
     *
     * @param myShelfieShadowType the radius of the shadow blur kernel
     */
    public MyShelfieDarkShadow(@NotNull MyShelfieShadowType myShelfieShadowType) {
        super(DARK_SHADOW, myShelfieShadowType);
    }
}

