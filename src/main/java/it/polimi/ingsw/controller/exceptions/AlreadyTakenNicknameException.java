package it.polimi.ingsw.controller.exceptions;

public class AlreadyTakenNicknameException extends Exception{
    private static final String DEF_MESSAGE = "Nickname is not available";

    public AlreadyTakenNicknameException(){
        super(DEF_MESSAGE);
    }

}
