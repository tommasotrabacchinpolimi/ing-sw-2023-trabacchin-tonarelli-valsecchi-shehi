package it.polimi.ingsw.controller.exceptions;

public class WrongChosenTilesFromBoardException extends RuntimeException{
    private static final String DEF_MESSAGE = "The chosen tiles are not acceptable.";

    public WrongChosenTilesFromBoardException(){
        super(DEF_MESSAGE);
    }

    public WrongChosenTilesFromBoardException(String message){
        super(message);
    }

    public WrongChosenTilesFromBoardException(String message, Throwable cause){
        super(message, cause);
    }

    public WrongChosenTilesFromBoardException(Throwable cause){
        super(DEF_MESSAGE, cause);
    }
}
