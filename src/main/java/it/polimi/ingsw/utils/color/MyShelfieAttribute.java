package it.polimi.ingsw.utils.color;

import static it.polimi.ingsw.utils.color.MyShelfieColor.*;

/**
 * Abstracts ANSI codes with intuitive names. It maps a description (e.g. RED_TEXT) with a code (e.g. 31).
 * @see <a href="https://en.wikipedia.org/wiki/ANSI_escape_code#Escape_sequences">Wikipedia, for a list of all codes available</a>
 * @see <a href="https://stackoverflow.com/questions/4842424/list-of-ansi-color-escape-sequences/33206814#33206814">StackOverflow, for a list of codes with examples</a>
 */
public abstract class MyShelfieAttribute {

    /**
     * @return The Attribute's ansi escape code.
     */
    @Override
    public abstract String toString();

    /**
     * @return Clears any format. Restores the terminal's default format.
     */
    public static MyShelfieAttribute CLEAR() {
        return new SimpleMyShelfieAttribute("0");
    }

    public static MyShelfieAttribute BOLD() {
        return new SimpleMyShelfieAttribute("1");
    }

    public static MyShelfieAttribute ITALIC() {
        return new SimpleMyShelfieAttribute("3");
    }

    // Colors (foreground)
    public static MyShelfieAttribute RED_TEXT() {
        return TEXT_COLOR(RED_RUBY.getRedValue(), RED_RUBY.getGreenValue(), RED_RUBY.getBlueValue());
    }

    public static MyShelfieAttribute GREEN_TEXT() {
        return TEXT_COLOR(APPLE_GREEN.getRedValue(), APPLE_GREEN.getGreenValue(), APPLE_GREEN.getBlueValue());
    }

    public static MyShelfieAttribute YELLOW_TEXT() {
        return TEXT_COLOR(GAMBOGE.getRedValue(), GAMBOGE.getGreenValue(), GAMBOGE.getBlueValue());
    }

    public static MyShelfieAttribute BLUE_TEXT() {
        return TEXT_COLOR(BICE_BLUE.getRedValue(), BICE_BLUE.getGreenValue(), BICE_BLUE.getBlueValue());
    }

    public static MyShelfieAttribute MAGENTA_TEXT() {
        return TEXT_COLOR(MAGENTA.getRedValue(), MAGENTA.getGreenValue(), MAGENTA.getBlueValue());
    }

    public static MyShelfieAttribute CYAN_TEXT() {
        return TEXT_COLOR(VERDIGRIS.getRedValue(), VERDIGRIS.getGreenValue(), VERDIGRIS.getBlueValue());
    }

    public static MyShelfieAttribute WHITE_TEXT() {
        return TEXT_COLOR(BONE.getRedValue(), BONE.getGreenValue(), BONE.getBlueValue());
    }

    // Colors (background)
    public static MyShelfieAttribute RED_BACK() {
        return BACK_COLOR(RED_RUBY.getRedValue(), RED_RUBY.getGreenValue(), RED_RUBY.getBlueValue());
    }

    public static MyShelfieAttribute GREEN_BACK() {
        return BACK_COLOR(APPLE_GREEN.getRedValue(), APPLE_GREEN.getGreenValue(), APPLE_GREEN.getBlueValue());
    }

    public static MyShelfieAttribute YELLOW_BACK() {
        return BACK_COLOR(GAMBOGE.getRedValue(), GAMBOGE.getLightenGreenValue(), GAMBOGE.getLightenBlueValue());
    }

    public static MyShelfieAttribute BLUE_BACK() {
        return BACK_COLOR(BICE_BLUE.getRedValue(), BICE_BLUE.getGreenValue(), BICE_BLUE.getBlueValue());
    }

    public static MyShelfieAttribute MAGENTA_BACK() {
        return BACK_COLOR(MAGENTA.getRedValue(), MAGENTA.getGreenValue(), MAGENTA.getBlueValue());
    }

    public static MyShelfieAttribute CYAN_BACK() {
        return BACK_COLOR(VERDIGRIS.getRedValue(), VERDIGRIS.getGreenValue(), VERDIGRIS.getBlueValue());
    }

    public static MyShelfieAttribute WHITE_BACK() {
        return BACK_COLOR(BONE.getRedValue(), BONE.getGreenValue(), BONE.getBlueValue());
    }

    // Bright colors (foreground)
    public static MyShelfieAttribute BRIGHT_RED_TEXT() {
        return TEXT_COLOR(RED_RUBY.getLightenRedValue(), RED_RUBY.getLightenGreenValue(), RED_RUBY.getLightenBlueValue());
    }

    public static MyShelfieAttribute BRIGHT_GREEN_TEXT() {
        return TEXT_COLOR(APPLE_GREEN.getLightenRedValue(), APPLE_GREEN.getLightenGreenValue(), APPLE_GREEN.getLightenBlueValue());
    }

    public static MyShelfieAttribute BRIGHT_YELLOW_TEXT() {
        return TEXT_COLOR(GAMBOGE.getLightenRedValue(), GAMBOGE.getLightenGreenValue(), GAMBOGE.getLightenBlueValue());
    }

    public static MyShelfieAttribute BRIGHT_BLUE_TEXT() {
        return TEXT_COLOR(BICE_BLUE.getLightenRedValue(), BICE_BLUE.getLightenGreenValue(), BICE_BLUE.getLightenBlueValue());
    }

    public static MyShelfieAttribute BRIGHT_MAGENTA_TEXT() {
        return TEXT_COLOR(MAGENTA.getLightenRedValue(), MAGENTA.getLightenGreenValue(), MAGENTA.getLightenBlueValue());
    }

    public static MyShelfieAttribute BRIGHT_CYAN_TEXT() {
        return TEXT_COLOR(VERDIGRIS.getLightenRedValue(), VERDIGRIS.getLightenGreenValue(), VERDIGRIS.getLightenBlueValue());
    }

    public static MyShelfieAttribute BRIGHT_WHITE_TEXT() {
        return TEXT_COLOR(BONE.getLightenRedValue(), BONE.getLightenGreenValue(), BONE.getLightenBlueValue());
    }

    // Bright colors (background)
    public static MyShelfieAttribute BRIGHT_RED_BACK() {
        return BACK_COLOR(RED_RUBY.getLightenRedValue(), RED_RUBY.getLightenGreenValue(), RED_RUBY.getLightenBlueValue());
    }

    public static MyShelfieAttribute BRIGHT_GREEN_BACK() {
        return BACK_COLOR(APPLE_GREEN.getLightenRedValue(), APPLE_GREEN.getLightenGreenValue(), APPLE_GREEN.getLightenBlueValue());
    }

    public static MyShelfieAttribute BRIGHT_YELLOW_BACK() {
        return BACK_COLOR(GAMBOGE.getLightenRedValue(), GAMBOGE.getLightenGreenValue(), GAMBOGE.getLightenBlueValue());
    }

    public static MyShelfieAttribute BRIGHT_BLUE_BACK() {
        return BACK_COLOR(BICE_BLUE.getLightenRedValue(), BICE_BLUE.getLightenGreenValue(), BICE_BLUE.getLightenBlueValue());
    }

    public static MyShelfieAttribute BRIGHT_MAGENTA_BACK() {
        return BACK_COLOR(MAGENTA.getLightenRedValue(), MAGENTA.getLightenGreenValue(), MAGENTA.getLightenBlueValue());
    }

    public static MyShelfieAttribute BRIGHT_CYAN_BACK() {
        return BACK_COLOR(VERDIGRIS.getLightenRedValue(), VERDIGRIS.getLightenGreenValue(), VERDIGRIS.getLightenBlueValue());
    }

    public static MyShelfieAttribute BRIGHT_WHITE_BACK() {
        return BACK_COLOR(BONE.getLightenRedValue(), BONE.getLightenGreenValue(), BONE.getLightenBlueValue());
    }

    // Darken colors (foreground)
    public static MyShelfieAttribute DARKEN_RED_TEXT() {
        return TEXT_COLOR(RED_RUBY.getDarkenRedValue(), RED_RUBY.getDarkenGreenValue(), RED_RUBY.getDarkenBlueValue());
    }

    public static MyShelfieAttribute DARKEN_GREEN_TEXT() {
        return TEXT_COLOR(APPLE_GREEN.getDarkenRedValue(), APPLE_GREEN.getDarkenGreenValue(), APPLE_GREEN.getDarkenBlueValue());
    }

    public static MyShelfieAttribute DARKEN_YELLOW_TEXT() {
        return TEXT_COLOR(GAMBOGE.getDarkenRedValue(), GAMBOGE.getDarkenGreenValue(), GAMBOGE.getDarkenBlueValue());
    }

    public static MyShelfieAttribute DARKEN_BLUE_TEXT() {
        return TEXT_COLOR(BICE_BLUE.getDarkenRedValue(), BICE_BLUE.getDarkenGreenValue(), BICE_BLUE.getDarkenBlueValue());
    }

    public static MyShelfieAttribute DARKEN_MAGENTA_TEXT() {
        return TEXT_COLOR(MAGENTA.getDarkenRedValue(), MAGENTA.getDarkenGreenValue(), MAGENTA.getDarkenBlueValue());
    }

    public static MyShelfieAttribute DARKEN_CYAN_TEXT() {
        return TEXT_COLOR(VERDIGRIS.getDarkenRedValue(), VERDIGRIS.getDarkenGreenValue(), VERDIGRIS.getDarkenBlueValue());
    }

    public static MyShelfieAttribute DARKEN_WHITE_TEXT() {
        return TEXT_COLOR(BONE.getDarkenRedValue(), BONE.getDarkenGreenValue(), BONE.getDarkenBlueValue());
    }

    // Darken colors (background)
    public static MyShelfieAttribute DARKEN_RED_BACK() {
        return BACK_COLOR(RED_RUBY.getDarkenRedValue(), RED_RUBY.getDarkenGreenValue(), RED_RUBY.getDarkenBlueValue());
    }

    public static MyShelfieAttribute DARKEN_GREEN_BACK() {
        return BACK_COLOR(APPLE_GREEN.getDarkenRedValue(), APPLE_GREEN.getDarkenGreenValue(), APPLE_GREEN.getDarkenBlueValue());
    }

    public static MyShelfieAttribute DARKEN_YELLOW_BACK() {
        return BACK_COLOR(GAMBOGE.getDarkenRedValue(), GAMBOGE.getDarkenGreenValue(), GAMBOGE.getDarkenBlueValue());
    }

    public static MyShelfieAttribute DARKEN_BLUE_BACK() {
        return BACK_COLOR(BICE_BLUE.getDarkenRedValue(), BICE_BLUE.getDarkenGreenValue(), BICE_BLUE.getDarkenBlueValue());
    }

    public static MyShelfieAttribute DARKEN_MAGENTA_BACK() {
        return BACK_COLOR(MAGENTA.getDarkenRedValue(), MAGENTA.getDarkenGreenValue(), MAGENTA.getDarkenBlueValue());
    }

    public static MyShelfieAttribute DARKEN_CYAN_BACK() {
        return BACK_COLOR(VERDIGRIS.getDarkenRedValue(), VERDIGRIS.getDarkenGreenValue(), VERDIGRIS.getDarkenBlueValue());
    }

    public static MyShelfieAttribute DARKEN_WHITE_BACK() {
        return BACK_COLOR(BONE.getDarkenRedValue(), BONE.getDarkenGreenValue(), BONE.getDarkenBlueValue());
    }

    // Complex colors
    /**
     *
     * @param r A number (0-255) that represents the red component.
     * @param g A number (0-255) that represents the green component.
     * @param b A number (0-255) that represents the blue component.
     * @return An Attribute that represents a foreground with a true color.
     */
    public static MyShelfieAttribute TEXT_COLOR(int r, int g, int b) {
        return new TextColorMyShelfieAttribute(r, g, b);
    }

    /**
     *
     * @param r A number (0-255) that represents the red component.
     * @param g A number (0-255) that represents the green component.
     * @param b A number (0-255) that represents the blue component.
     * @return An Attribute that represents a background with a true color.
     */
    public static MyShelfieAttribute BACK_COLOR(int r, int g, int b) {
        return new BackColorMyShelfieAttribute(r, g, b);
    }
}

