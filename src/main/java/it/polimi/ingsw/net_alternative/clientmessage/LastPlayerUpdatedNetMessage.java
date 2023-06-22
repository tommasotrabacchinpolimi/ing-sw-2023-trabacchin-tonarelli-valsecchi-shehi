package it.polimi.ingsw.net_alternative.clientmessage;

import it.polimi.ingsw.net_alternative.ClientMessage;
import it.polimi.ingsw.net_alternative.ClientDispatcherInterface;

public class LastPlayerUpdatedNetMessage implements ClientMessage {

    private final String nicknameLastPlayer;

    public LastPlayerUpdatedNetMessage(String nicknameLastPlayer) {
        this.nicknameLastPlayer = nicknameLastPlayer;
    }

    public String getNicknameLastPlayer() {
        return nicknameLastPlayer;
    }


    @Override
    public void dispatch(ClientDispatcherInterface clientDispatcherInterface) {
        clientDispatcherInterface.dispatch(this);
    }
}
