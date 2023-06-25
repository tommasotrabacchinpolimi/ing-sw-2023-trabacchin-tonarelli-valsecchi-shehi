package it.polimi.ingsw.model.exceptions;

public class NoMoreTileSubjectsLeftInTheBag extends RuntimeException {
    private static final String DEF_MESSAGE = "No more tile subject in the bag";

    public NoMoreTileSubjectsLeftInTheBag() {
        super(DEF_MESSAGE);
    }

}