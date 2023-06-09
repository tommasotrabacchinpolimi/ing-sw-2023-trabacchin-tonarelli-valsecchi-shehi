package it.polimi.ingsw.utils.color;

class TextColorMyShelfieAttribute extends ColorMyShelfieAttribute {

    /**
     * {@inheritDoc}
     */
    TextColorMyShelfieAttribute(int colorNumber) {
        super(colorNumber);
    }

    /**
     * {@inheritDoc}
     */
    TextColorMyShelfieAttribute(int r, int g, int b) {
        super(r, g, b);
    }

    @Override
    protected String getColorAnsiPrefix() {
        String ANSI_8BIT_COLOR_PREFIX = "38;5;";
        String ANSI_TRUE_COLOR_PREFIX = "38;2;";

        return isTrueColor() ? ANSI_TRUE_COLOR_PREFIX : ANSI_8BIT_COLOR_PREFIX;
    }

}
