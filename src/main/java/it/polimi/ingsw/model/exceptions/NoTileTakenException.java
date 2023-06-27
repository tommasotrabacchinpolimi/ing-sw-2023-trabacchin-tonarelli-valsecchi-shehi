package it.polimi.ingsw.model.exceptions;

/**
 * Exception thrown when no Tile object has been taken from the Board.
 */
public class NoTileTakenException extends RuntimeException {
    /**
     * Default error message
     */
    private static final String DEF_MESSAGE = "No Tile object has been taken from the Board";

    /**
     * Constructs a NoTileTakenException with a specified cause.
     * @param cause the cause of the exception
     */
    public NoTileTakenException(Throwable cause) {
        super(DEF_MESSAGE, cause);
    }
}
