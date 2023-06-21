package it.polimi.ingsw.net_alternative.servermessages;

import it.polimi.ingsw.controller.ClientInterface;
import it.polimi.ingsw.net_alternative.ServerMessage;
import it.polimi.ingsw.net_alternative.ServerDispatcherInterface;

public class JoinGameNetMessage implements ServerMessage {
    private String nickname;
     public JoinGameNetMessage(String nickname) {
         this.nickname = nickname;
     }

     public String getNickname() {
         return this.nickname;
     }

    @Override
    public void dispatch(ServerDispatcherInterface serverDispatcher, ClientInterface view) {
        serverDispatcher.dispatch(this, view);
    }
}
