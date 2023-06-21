package it.polimi.ingsw.net_alternative.clientmessage;

import it.polimi.ingsw.net_alternative.ClientMessage;
import it.polimi.ingsw.net_alternative.ClientDispatcherInterface;

public class CurrentPlayerChangedListenerNetMessage implements ClientMessage {

    private String nickname;

    public CurrentPlayerChangedListenerNetMessage(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }


    @Override
    public void dispatch(ClientDispatcherInterface clientDispatcherInterface) {
        clientDispatcherInterface.dispatch(this);
    }
}
