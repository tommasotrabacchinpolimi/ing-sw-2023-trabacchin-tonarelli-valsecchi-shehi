package it.polimi.ingsw.model.exceptions;

/**
 * Exception thrown when there is not enough space in the bookshelf to hold all elements.
 * @see it.polimi.ingsw.model.BookShelf
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public class NotEnoughSpaceInBookShelfException extends RuntimeException{
    /**
     * Default error message.
     */
    private static final String DEF_MESSAGE = "Bookshelf has not enough space to hold all elements";

    /**
     * Constructs a NotEnoughSpaceInBookShelfException with a default error message.
     */
    public NotEnoughSpaceInBookShelfException() {
        super(DEF_MESSAGE);
    }

    /**
     * Constructs a NotEnoughSpaceInBookShelfException with a specified error message.
     * @param message the error message
     */
    public NotEnoughSpaceInBookShelfException(String message) {
        super(message);
    }
}
