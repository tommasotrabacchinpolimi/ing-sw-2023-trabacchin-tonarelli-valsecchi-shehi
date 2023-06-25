package it.polimi.ingsw.controller.exceptions;

public class AlreadyInGameException extends Exception{
    private static final String DEF_MESSAGE = "You are already in a game and you need to leave it first to create or join a new game.";

    public AlreadyInGameException(){
        super(DEF_MESSAGE);
    }

}
