package it.polimi.ingsw.model.exceptions;

public class NotEnoughSpaceInBookShelfException extends RuntimeException{

    private static final String DEF_MESSAGE = "Bookshelf has not enough space to hold all elements";

    public NotEnoughSpaceInBookShelfException() {
        super(DEF_MESSAGE);
    }
    public NotEnoughSpaceInBookShelfException(String message) {
        super(message);
    }

    public NotEnoughSpaceInBookShelfException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughSpaceInBookShelfException(Throwable cause) {
        super(DEF_MESSAGE, cause);
    }
}
