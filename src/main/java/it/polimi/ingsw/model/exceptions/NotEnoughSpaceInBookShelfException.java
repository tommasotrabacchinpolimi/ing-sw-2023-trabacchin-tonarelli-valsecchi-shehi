package it.polimi.ingsw.model.exceptions;

public class NotEnoughSpaceInBookShelfException extends RuntimeException{

    private static final String DEF_MESSAGE = "Bookshelf has not enough space to hold all elements";

    public NotEnoughSpaceInBookShelfException() {
        super(DEF_MESSAGE);
    }

    public NotEnoughSpaceInBookShelfException(String message) {
        super(message);
    }

}
