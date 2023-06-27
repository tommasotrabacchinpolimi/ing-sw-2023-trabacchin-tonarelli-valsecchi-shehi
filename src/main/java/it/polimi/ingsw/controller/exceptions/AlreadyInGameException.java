package it.polimi.ingsw.controller.exceptions;

/**
 * An exception indicating that the user is already in a game and needs to leave it
 * before creating or joining a new game.
 * Extends the {@linkplain Exception} class.
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public class AlreadyInGameException extends Exception{
    /**
     * Default error message.
     */
    private static final String DEF_MESSAGE = "You are already in a game and you need to leave it first to create or join a new game.";

    /**
     * Constructs a new AlreadyInGameException with the default error message.
     */
    public AlreadyInGameException(){
        super(DEF_MESSAGE);
    }

}
