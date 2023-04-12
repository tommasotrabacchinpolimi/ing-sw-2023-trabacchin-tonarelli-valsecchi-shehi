package it.polimi.ingsw.personalexceptions;

public class NoTileTakenException extends RuntimeException {
    private static final String DEF_MESSAGE = "No Tile object is been taken from the Board";

    public NoTileTakenException() {
        super(DEF_MESSAGE);
    }
    public NoTileTakenException(String message) {
        super(message);
    }

    public NoTileTakenException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoTileTakenException(Throwable cause) {
        super(DEF_MESSAGE, cause);
    }
}
