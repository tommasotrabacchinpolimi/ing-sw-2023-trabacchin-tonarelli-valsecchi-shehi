package it.polimi.ingsw.utils.color;

/**
 * Abstracts ANSI codes with intuitive names. It maps a command (e.g. CLEAR_SCREEN) with a code.
 */
public class MyShelfieCommand {

    private final String _code;

    /**
     * Constructor. Maps a command to an Ansi code.
     *
     * @param code Ansi code that represents the command.
     */
    MyShelfieCommand(String code) {
        _code = code;
    }

    /**
     * @return Clears the terminal's text, e.g. just like the command-line `clear`.
     */
    public static MyShelfieCommand CLEAR_SCREEN() {
        return new MyShelfieCommand("H\\033[2J\"");
    }

    @Override
    public String toString() {
        return _code;
    }
}
