package it.polimi.ingsw.utils.color;

/**
 * Represents the text color attribute for MyShelfie.
 *
 * <p>This class extends the {@link ColorMyShelfieAttribute} class and provides methods to set the text color
 * using either 8-bit ANSI color codes or true color codes.</p>
 *
 * <p>Usage example:</p>
 * <pre>{@code
 * TextColorMyShelfieAttribute textColor = new TextColorMyShelfieAttribute(255, 0, 0);
 * }</pre>
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 * @since 2023-06-28
 * @see ColorMyShelfieAttribute
 */
class TextColorMyShelfieAttribute extends ColorMyShelfieAttribute {

    /**
     * Constructs a new {@code TextColorMyShelfieAttribute} object with the specified RGB values.
     *
     * @param r the red component of the RGB color (0-255)
     * @param g the green component of the RGB color (0-255)
     * @param b the blue component of the RGB color (0-255)
     */
    TextColorMyShelfieAttribute(int r, int g, int b) {
        super(r, g, b);
    }

    /**
     * {@inheritDoc}
     *
     * <p>Returns the ANSI color prefix based on whether the color is a true color or an 8-bit color.</p>
     *
     * @return the ANSI color prefix
     */
    @Override
    protected String getColorAnsiPrefix() {
        String ANSI_8BIT_COLOR_PREFIX = "38;5;";
        String ANSI_TRUE_COLOR_PREFIX = "38;2;";

        return isTrueColor() ? ANSI_TRUE_COLOR_PREFIX : ANSI_8BIT_COLOR_PREFIX;
    }
}
