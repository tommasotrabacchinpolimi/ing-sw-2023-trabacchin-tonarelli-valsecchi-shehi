package it.polimi.ingsw.controller.exceptions;

public class WrongChosenTilesFromBoardException extends Exception{
    private static final String DEF_MESSAGE = "The chosen tiles are not acceptable.";

    public WrongChosenTilesFromBoardException(){
        super(DEF_MESSAGE);
    }

    public WrongChosenTilesFromBoardException(String message){
        super(message);
    }

}
