package it.polimi.ingsw.net_alternative;

import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.net_alternative.servermessages.*;

import java.util.HashMap;
import java.util.Map;

public class ServerDispatcher implements ServerDispatcherInterface{

    private final ControllerDispatcher controllerDispatcher;
    public ServerDispatcher(ControllerDispatcher controllerDispatcher) {
        this.controllerDispatcher = controllerDispatcher;
    }

    @Override
    public void dispatch(CreateGameNetMessage createGameNetMessage, ClientInterface clientInterface) {
        try{
            controllerDispatcher.createGame(clientInterface, createGameNetMessage.getNickname(), createGameNetMessage.getNumberOfPlayer());
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dispatch(JoinGameNetMessage joinGameNetMessage, ClientInterface view) {
        try{
            controllerDispatcher.joinGame(view, joinGameNetMessage.getNickname());
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void dispatch(QuitGameNetMessage quitGameNetMessage, ClientInterface view) {
        controllerDispatcher.quitGame(view);
    }

    @Override
    public void dispatch(SentMessageNetMessage sentMessageNetMessage, ClientInterface view) {
        controllerDispatcher.sentMessage(view, sentMessageNetMessage.getText(), sentMessageNetMessage.getReceiversNickname());
    }

    @Override
    public void dispatch(DragTilesToBookShelfNetMessage dragTilesToBookShelfNetMessage, ClientInterface view) {
        controllerDispatcher.dragTilesToBookShelf(view, dragTilesToBookShelfNetMessage.getChosenTiles(), dragTilesToBookShelfNetMessage.getChosenColumn());
    }

    @Override
    public void dispatch(NopNetMessage nopNetMessage) {
        controllerDispatcher.nop();
    }

}
