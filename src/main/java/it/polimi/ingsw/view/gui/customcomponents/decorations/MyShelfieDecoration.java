package it.polimi.ingsw.view.gui.customcomponents.decorations;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a "MyShelfie decoration" applied to a
 * custom graphical component according to the design of the game.
 *
 * @apiNote <p>This class is designed to be used
 * with {@link MyShelfieComponent component} class</p>
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
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emamuele Valsecchi
 * @author Adem Shehi
 */
public interface MyShelfieDecoration {

    /**
     * <p>The method that defines a customization to be applied on a {@linkplain MyShelfieComponent component}</p>
     *
     * @param decoratedComponent the component that needs to be customized
     */
    void customize(@NotNull MyShelfieComponent decoratedComponent);
}