package it.polimi.ingsw.utils.color;

/**
 * Abstracts ANSI codes with intuitive names. It maps a command (e.g. CLEAR_SCREEN) with a code.
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
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

    @Override
    public String toString() {
        return _code;
    }
}
