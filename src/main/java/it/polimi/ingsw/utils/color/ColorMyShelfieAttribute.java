package it.polimi.ingsw.utils.color;

import static java.lang.String.valueOf;

/**
 * An abstract class representing a colored attribute in MyShelfie.
 * Extends the {@linkplain MyShelfieAttribute} class.
 */
abstract class ColorMyShelfieAttribute extends MyShelfieAttribute {

    protected final String[] _color;

    /**
     * Constructor (8-bit color).
     * @param colorNumber A number (0-255) that represents an 8-bit color.
     * @throws IllegalArgumentException if the colorNumber is not within the range [0-255].
     */
    ColorMyShelfieAttribute(int colorNumber) {
        if (0 <= colorNumber && colorNumber <= 255) {
            _color = new String[]{valueOf(colorNumber)};
        } else
            throw new IllegalArgumentException("Color must be a number inside range [0-255]. Received: " + colorNumber);
    }

    /**
     * Constructor (true-color).
     *
     * @param r A number (0-255) that represents the red component.
     * @param g A number (0-255) that represents the green component.
     * @param b A number (0-255) that represents the blue component.
     * @throws IllegalArgumentException if any of the color components is not within the range [0-255].
     */
    ColorMyShelfieAttribute(int r, int g, int b) {
        if ((0 <= r && r <= 255) && (0 <= g && g <= 255) && (0 <= b && b <= 255)) {
            _color = new String[]{valueOf(r), valueOf(g), valueOf(b)};
        } else
            throw new IllegalArgumentException(
                    String.format("Color components must be a number inside range [0-255]. Received: %d, %d, %d", r, g, b));
    }

    /**
     * Checks if the color is a true-color.
     * @return true if the color is a true-color, false otherwise
     */
    protected boolean isTrueColor() {
        return (_color.length == 3 );
    }

    /**
     * Returns the ANSI prefix for the color.
     * @return the ANSI prefix for the color
     */
    protected abstract String getColorAnsiPrefix();

    /**
     * Returns the ANSI code for the color.
     * @return the ANSI code for the color
     */
    protected String getColorAnsiCode() {
        if (isTrueColor())
            return _color[0] + MyShelfieAnsi.SEPARATOR + _color[1] + MyShelfieAnsi.SEPARATOR + _color[2];
        else
            return _color[0];
    }

    /**
     * Returns a string representation of the colored attribute.
     * The string includes the ANSI prefix and ANSI code for the color.
     * @return a string representation of the colored attribute
     */
    @Override
    public String toString() {
        return getColorAnsiPrefix() + getColorAnsiCode();
    }

}
