package it.polimi.ingsw.utils.color;

/**
 * Provides a fluent API to generate
 * <a href="https://en.wikipedia.org/wiki/ANSI_escape_code">ANSI escape sequences</a>
 * by specifying {@link MyShelfieAttribute}s of your format.
 */
public class MyShelfieAnsi {
    /**
     * Escape character used to start an ANSI code
     */
    private static final char ESC = 27;
    /**
     * System property for line separator.
     */
    private static final String NEWLINE = System.getProperty("line.separator");

    /**
     * Every Ansi escape code begins with this PREFIX.
     */
    public static final String PREFIX = ESC + "[";
    /**
     * Two options must be separated by this SEPARATOR.
     */
    public static final String SEPARATOR = ";";
    /**
     * Every Ansi escape code must end with this POSTFIX.
     */
    public static final String POSTFIX = "m";
    /**
     * Shorthand for the Ansi code that resets to the terminal's default format.
     */
    public static final String RESET = PREFIX + MyShelfieAttribute.CLEAR() + POSTFIX;

    /**
     * @param myShelfieAttributes ANSI attributes to format a text.
     * @return The ANSI code that describes all those attributes together.
     */
    public static String generateCode(MyShelfieAttribute... myShelfieAttributes) {
        StringBuilder builder = new StringBuilder();

        builder.append(PREFIX);
        for (Object option : myShelfieAttributes) {
            String code = option.toString();
            if (code.equals(""))
                continue;
            builder.append(code);
            builder.append(SEPARATOR);
        }
        builder.append(POSTFIX);

        // because code must not end with SEPARATOR
        return builder.toString().replace(SEPARATOR + POSTFIX, POSTFIX);
    }

    /**
     * @param myShelfieCommand ANSI command to apply to terminal.
     * @return The ANSI code that describes that command.
     */
    public static String generateCode(MyShelfieCommand myShelfieCommand) {
        return PREFIX + myShelfieCommand;
    }

    /**
     * @param myShelfieCommand Ansi command to apply to terminal.
     * @return The formatted string, ready to be printed.
     */
    public static String colorize(MyShelfieCommand myShelfieCommand) {
        return generateCode(myShelfieCommand);
    }

    /**
     * @param text     String to format.
     * @param ansiCode Ansi code to format each message's lines.
     * @return The formatted string, ready to be printed.
     * @apiNote Every formatted line should: <ul>
     *          <li>1) start with a code that sets the format</li>
     *          <li>2) end with a code that resets the format</li></ul>
     *          This prevents "spilling" the format to other independent prints, which
     *          is noticeable when the background is colored.
     */
    public static String colorize(String text, String ansiCode) {
        StringBuilder output = new StringBuilder();
        output.append(ansiCode);
        // Each line needs to end the current format (RESET) and start it on the next line.
        // This avoids spilling, ie. a long line without text but formatted background
        String enclosedFormatting = text.replace(NEWLINE, RESET + NEWLINE + ansiCode);
        output.append(enclosedFormatting);
        output.append(RESET);
        return output.toString();
    }

    /**
     * @param text       String to format.
     * @param myShelfieAttributes ANSI attributes to format a text.
     * @return The formatted string, ready to be printed.
     */
    public static String colorize(String text, MyShelfieAttribute... myShelfieAttributes) {
        String ansiCode = generateCode(myShelfieAttributes);
        return colorize(text, ansiCode);
    }

    /**
     * @param text       String to format.
     * @param attributes Object containing format attributes.
     * @return The formatted string, ready to be printed.
     */
    public static String colorize(String text, MyShelfieAnsiFormat attributes) {
        return colorize(text, attributes.toArray());
    }

}
