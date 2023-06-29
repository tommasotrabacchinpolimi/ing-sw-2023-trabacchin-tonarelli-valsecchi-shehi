package it.polimi.ingsw.controller.exceptions;

public class WrongNumberOfPlayersException extends Exception{
    /**
     * Default error message
     */
    private static final String DEF_MESSAGE = "The number of players must be between 2 and 4!";

    /**
     * Constructs a new WrongChosenTilesFromBoardException with the default error message.
     */
    public WrongNumberOfPlayersException(){
        super(DEF_MESSAGE);
    }
}
