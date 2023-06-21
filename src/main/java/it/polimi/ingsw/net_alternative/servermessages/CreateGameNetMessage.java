package it.polimi.ingsw.net_alternative.servermessages;

import it.polimi.ingsw.controller.ClientInterface;
import it.polimi.ingsw.net_alternative.ServerMessage;
import it.polimi.ingsw.net_alternative.ServerDispatcherInterface;

import java.io.Serializable;

public class CreateGameNetMessage implements ServerMessage, Serializable {
    private final String nickname;
    private final int numberOfPlayer;
    public CreateGameNetMessage(String nickname, int numberOfPlayer) {
        this.nickname = nickname;
        this.numberOfPlayer = numberOfPlayer;
    }

    @Override
    public void dispatch(ServerDispatcherInterface serverDispatcher, ClientInterface view) {
        serverDispatcher.dispatch(this, view);
    }

    public String getNickname() {
        return nickname;
    }

    public int getNumberOfPlayer() {
        return numberOfPlayer;
    }
}
