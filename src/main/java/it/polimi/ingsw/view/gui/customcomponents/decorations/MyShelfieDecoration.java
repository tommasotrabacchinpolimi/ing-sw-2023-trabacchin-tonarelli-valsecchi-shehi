package it.polimi.ingsw.view.gui.customcomponents.decorations;

/**
 * Represents a "MyShelfie decoration" applied to a
 * custom graphical component according to the design of the game.
 *
 * @apiNote This class is designed to be used
 * with {@link MyShelfieComponent} class
 *
 * @see MyShelfieBloom
 * @see MyShelfieDarkShadow
 * @see MyShelfieGlow
 * @see MyShelfieInnerShadow
 * @see MyShelfieLightShadow
 * @see MyShelfieObscured
 * @see MyShelfieOpaque
 * @see MyShelfieRoundEdge
 * @see MyShelfieComponent
 */
public interface MyShelfieDecoration {

    /**
     * <p>The method that defines a customization to be applied on a {@linkplain MyShelfieComponent component}</p>
     *
     * @param decoratedComponent the component that needs to be customized
     */
    void customize(MyShelfieComponent decoratedComponent);
}