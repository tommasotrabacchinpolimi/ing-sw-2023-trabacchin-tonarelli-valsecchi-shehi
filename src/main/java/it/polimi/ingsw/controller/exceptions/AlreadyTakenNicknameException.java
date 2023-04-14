package it.polimi.ingsw.controller.exceptions;

public class AlreadyTakenNicknameException extends RuntimeException{
    private static final String DEF_MESSAGE = "Nickname is not available";

    public AlreadyTakenNicknameException(){
        super(DEF_MESSAGE);
    }

    public AlreadyTakenNicknameException(String message){
        super(message);
    }

    public AlreadyTakenNicknameException(String message, Throwable cause){
        super(message, cause);
    }

    public AlreadyTakenNicknameException(Throwable cause){
        super(DEF_MESSAGE, cause);
    }
}
