package it.polimi.ingsw.view.gui.customcomponents.uitoolkit;

import it.polimi.ingsw.view.gui.customcomponents.decorations.MyShelfieInnerShadow;
import it.polimi.ingsw.view.gui.customcomponents.decorations.MyShelfieShadow;

/**
 * Define standard radius value of the shadow blur kernel
 *
 * @see MyShelfieShadow
 * @see MyShelfieInnerShadow
 */
public enum MyShelfieShadowType {
    /**
     * Default value for a short drop shadow
     */
    SHORT (3.7),

    /**
     * Default value for a normal drop shadow
     */
    NORMAL(10.0),

    /**
     * Default value for an extended drop shadow
     */
    EXTENDED(127.0);

    /**
     * Constructor for different radius drop shadow
     *
     * @param radius the value of the shadow blur kernel
     */
    MyShelfieShadowType(double radius){
        this.radius = radius;
    }

    /**
     * The radius value of the shadow blur kernel
     */
    private final double radius;

    /**
     * Retrieve the standard radius value of the
     * shadow blur kernel for a different shadow type
     *
     * @return the radius value of the shadow blur kernel
     */
    public double getRadius(){
        return radius;
    }
}
