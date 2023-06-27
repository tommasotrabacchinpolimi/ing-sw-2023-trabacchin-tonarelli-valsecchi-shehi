package it.polimi.ingsw.controller.exceptions;

/**
 * An exception indicating that the chosen tiles from the board are not acceptable.
 * Extends the {@linkplain Exception} class.
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public class WrongChosenTilesFromBoardException extends Exception{
    /**
     * Default error message
     */
    private static final String DEF_MESSAGE = "The chosen tiles are not acceptable.";

    /**
     * Constructs a new WrongChosenTilesFromBoardException with the default error message.
     */
    public WrongChosenTilesFromBoardException(){
        super(DEF_MESSAGE);
    }

    /**
     * Constructs a new WrongChosenTilesFromBoardException with the specified error message.
     * @param message the detail error message.
     */
    public WrongChosenTilesFromBoardException(String message){
        super(message);
    }

}
