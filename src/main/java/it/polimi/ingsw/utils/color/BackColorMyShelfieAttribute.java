package it.polimi.ingsw.utils.color;

/**
 * A class representing the background color attribute.
 * Extends the {@linkplain ColorMyShelfieAttribute} class.
 */
class BackColorMyShelfieAttribute extends ColorMyShelfieAttribute {

    /**
     * * {@inheritDoc}
     * Constructor for true-color.
     * @param r A number (0-255) that represents the red component.
     * @param g A number (0-255) that represents the green component.
     * @param b A number (0-255) that represents the blue component.
     * @throws IllegalArgumentException if any of the color components is not within the range [0-255].
     */
    BackColorMyShelfieAttribute(int r, int g, int b) {
        super(r, g, b);
    }

    /**
     * Returns the ANSI prefix for the background color.
     * @return the ANSI prefix for the background color
     * @see ColorMyShelfieAttribute#getColorAnsiPrefix()
     */
    @Override
    protected String getColorAnsiPrefix() {
        String ANSI_8BIT_COLOR_PREFIX = "48;5;";
        String ANSI_TRUE_COLOR_PREFIX = "48;2;";

        return isTrueColor() ? ANSI_TRUE_COLOR_PREFIX : ANSI_8BIT_COLOR_PREFIX;
    }

}
