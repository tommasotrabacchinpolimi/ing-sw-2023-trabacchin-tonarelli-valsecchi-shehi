package it.polimi.ingsw.model.exceptions;

public class NoTileTakenException extends RuntimeException {
    private static final String DEF_MESSAGE = "No Tile object has been taken from the Board";

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
