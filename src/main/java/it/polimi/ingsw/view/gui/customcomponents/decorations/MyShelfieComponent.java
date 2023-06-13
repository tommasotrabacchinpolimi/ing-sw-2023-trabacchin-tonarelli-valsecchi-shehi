package it.polimi.ingsw.view.gui.customcomponents.decorations;

import javafx.scene.Node;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

/**
 * <p>Represents a customizable node</p>
 * <p>It is directly related to the {@link MyShelfieDecoration}</p>
 * <p>The purpose of this interface is to define some basic operations
 * that every customized component needs to implement</p>
 *
 * @apiNote For a correct implementation of this interface,
 * every custom component should have a
 * {@link MyShelfieDecoration decoration}'s collection that contains
 * the default decoration for a graphical component
 * @see MyShelfieDecoration
 */
public interface MyShelfieComponent {

    /**
     * Apply to the calling customized component defaults
     * decoration
     */
    default void resetToDefaultDecorations() {
        getCustomizedNode().setEffect(null);
        getBaseDecorations().forEach(decoration -> decoration.customize(this));
    }

    /**
     * Applies to the calling customized component a
     * chosen {@linkplain MyShelfieDecoration decoration}
     *
     * @param decoration the {@link MyShelfieDecoration decoration}
     *                   to apply at the component and that is set as
     *                   one of the default decorations
     * @apiNote if the customized component allows it,
     * the decoration is set as a default decoration
     */
    default void applyDecorationAsDefault(MyShelfieDecoration decoration) {
        getBaseDecorations().add(decoration);
        applyDecoration(decoration);
    }

    /**
     * Applies to the calling customized component some
     * chosen {@linkplain MyShelfieDecoration decorations}
     *
     * @param decorations the {@link MyShelfieDecoration decorations}
     *                    to apply at the component and that are set as
     *                    default decorations
     * @apiNote if the customized component allows it,
     * the decorations are set as default decorations
     */
    default void applyDecorationAsDefault(MyShelfieDecoration... decorations) {
        Arrays.asList(decorations).forEach(this::applyDecorationAsDefault);
    }

    /**
     * Applies to the calling customized component a
     * chosen {@linkplain MyShelfieDecoration decoration}
     * on top of other decorations already present
     *
     * @param decoration the {@link MyShelfieDecoration decoration}
     *                   to apply at the component
     */
    default void applyDecoration(@NotNull MyShelfieDecoration decoration) {
        decoration.customize(this);
    }

    /**
     * Applies to the calling customized component some
     * chosen {@linkplain MyShelfieDecoration decorations}
     * on top of other decorations already present
     *
     * @param decorations the {@link MyShelfieDecoration decorations}
     *                    to apply at the component
     */
    default void applyDecoration(MyShelfieDecoration... decorations) {
        Arrays.stream(decorations).forEach(this::applyDecoration);
    }

    /**
     * Removes all decorations applied on a
     * component and set the only one passed
     *
     * @param decoration the {@link MyShelfieDecoration decoration}
     *                   to apply at the component
     */
    default void applyDecorationFromZero(MyShelfieDecoration decoration) {
        getCustomizedNode().setEffect(null);
        applyDecoration(decoration);
    }

    /**
     * Removes all decorations applied on a
     * component and applies the
     * new chosen decorations
     *
     * @param decorations the {@link MyShelfieDecoration decoration}
     *                    to apply at the component
     */
    default void applyDecorationFromZero(MyShelfieDecoration... decorations) {
        getCustomizedNode().setEffect(null);
        applyDecoration(decorations);
    }

    /**
     * Retrieve the element that has to be customized
     *
     * @return the element that needs to be customized
     */
    Node getCustomizedNode();

    /**
     * Retrieves the list of default decorations
     * that are applied on a customized
     * {@linkplain MyShelfieComponent component}
     *
     * @return the list of default decorations
     */
    List<MyShelfieDecoration> getBaseDecorations();
}
