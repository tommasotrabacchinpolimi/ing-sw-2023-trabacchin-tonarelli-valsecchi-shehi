package it.polimi.ingsw.view.gui.customcomponents.decorations;

import javafx.scene.Node;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

    /**
     * Used to load image file from the project structure
     *
     * @param filePath     Complete path from the root of the
     *                     resource folder to the file itself
     *
     * @return The {@linkplain String complete path} to the
     * desired file if it is found, {@code null} otherwise.
     * @apiNote <p>The {@code filePath} parameter must specify also
     * the file extension type</p>
     * <p>The returned path is expressed as a string</p>
     */
    @Nullable
    default String getMyShelfieResourceStringPath(String filePath) {
        return String.valueOf(getOptionalMyShelfieResource(filePath, (String) null).orElse(null));
    }

    /**
     * Used to load image file from the project structure
     *
     * @param filePath     Complete path from the root of the
     *                     resource folder to the file itself
     *
     * @return The {@linkplain URL complete path} to the
     * desired file if it is found, {@code null} otherwise.
     * @apiNote The {@code filePath} parameter must specify also
     * the file extension type
     */
    @Nullable
    default URL getMyShelfieResource(String filePath) {
        return getOptionalMyShelfieResource(filePath, (String) null).orElse(null);
    }

    /**
     * Used to load image file from the project structure
     *
     * @param filePath     Complete path from the root of the
     *                     resource folder to the file itself
     * @param errormessage an error message to display at video
     *                     if the resource specified is not
     *                     found in the project structure
     *
     * @return The {@linkplain URL complete path} to the
     * desired file if it is found, {@code null} otherwise.
     * @apiNote The {@code filePath} parameter must specify also
     * the file extension type
     */
    @Nullable
    default URL getMyShelfieResource(String filePath, String errormessage) {
        return getOptionalMyShelfieResource(filePath, errormessage).orElse(null);
    }

    /**
     * Used to load image file from the project structure
     * If the main file path specified doesn't correspond
     * to a file in the project, the second path is
     * considered
     *
     * @param mainPath     Complete path from the root of the
     *                     resource folder to the file itself
     * @param supportPath  A second path that corresponds to
     *                     another file in the project.
     * @param errormessage an error message to display at video
     *                     if the resource specified is not
     *                     found in the project structure
     *
     * @return The {@linkplain URL complete path} to the
     * desired file if it is found, the path to the support
     * file if the first one is not found; in the end
     * {@code null} if neither the first nor the second file
     * is found
     *
     * @apiNote <p>The {@code filePath} parameter must specify also
     * the file extension type</p>
     * <p>The {@code supportPath} specified as second
     * parameter should contain a standard file that is
     * certainly reachable in the project</p>
     */
    @Nullable
    default URL getMyShelfieResource(String mainPath, String supportPath, String errormessage) {
        return getOptionalMyShelfieResource(mainPath, errormessage).orElse(
                getOptionalMyShelfieResource(supportPath).orElse(null)
        );
    }

    /**
     * Load image file from the project structure if it is found,
     * returns an {@link Optional empty optional} if the file
     * doesn't exist
     *
     * @param filePath     complete path from the root of the
     *                     resource folder to the file itself
     * @param errormessage the optional error message to
     *                     display at video if the resource
     *                     specified is not found in the
     *                     project structure
     * @return the {@linkplain URL complete path} to the
     * desired file if it is found, null otherwise.
     * @apiNote <p>Error message can be omitted, but in that
     * case a default message will be printed on screen.</p>
     * <p>More than one message can be specified, but only
     * the first one is considered.</p>
     * <p>The {@code filePath} parameter must specify also
     * the file extension type</p>
     */
    static Optional<URL> getOptionalMyShelfieResource(String filePath, String... errormessage) {

        Optional<URL> fileURL = Optional.empty();

        try {
            fileURL = Optional.ofNullable(MyShelfieComponent.class.getResource(filePath));
        } catch (NullPointerException e) {
            System.out.println(Arrays.stream(errormessage).findFirst().orElse("Can't load requested resource"));
        }

        return fileURL;
    }
}
