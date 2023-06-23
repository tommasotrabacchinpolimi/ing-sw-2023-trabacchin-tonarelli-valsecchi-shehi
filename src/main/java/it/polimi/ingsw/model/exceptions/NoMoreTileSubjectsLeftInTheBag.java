package it.polimi.ingsw.model.exceptions;

public class NoMoreTileSubjectsLeftInTheBag extends RuntimeException {
    private static final String DEF_MESSAGE = "No more tile subject in the bag";

    public NoMoreTileSubjectsLeftInTheBag() {
        super(DEF_MESSAGE);
    }

    public NoMoreTileSubjectsLeftInTheBag(String message) {
        super(message);
    }

    public NoMoreTileSubjectsLeftInTheBag(String message, Throwable cause) {
        super(message, cause);
    }

    public NoMoreTileSubjectsLeftInTheBag(Throwable cause) {
        super(DEF_MESSAGE, cause);
    }
}