package it.polimi.ingsw.controller.exceptions;

/**
 * An exception indicating that the nickname is already taken and not available.
 * Extends the {@linkplain  Exception} class.
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public class AlreadyTakenNicknameException extends Exception{
    /**
     * Default error message.
     */
    private static final String DEF_MESSAGE = "Nickname is not available";

    /**
     * Constructs a new AlreadyTakenNicknameException with the default error message.
     */
    public AlreadyTakenNicknameException(){
        super(DEF_MESSAGE);
    }

}
