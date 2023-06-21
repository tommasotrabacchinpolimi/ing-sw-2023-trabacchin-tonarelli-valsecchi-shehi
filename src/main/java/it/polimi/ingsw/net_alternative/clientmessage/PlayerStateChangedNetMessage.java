package it.polimi.ingsw.net_alternative.clientmessage;

import it.polimi.ingsw.model.PlayerState;
import it.polimi.ingsw.net_alternative.ClientMessage;
import it.polimi.ingsw.net_alternative.ClientDispatcherInterface;

public class PlayerStateChangedNetMessage implements ClientMessage {

    private String nickname;

    private PlayerState playerState;

    public PlayerStateChangedNetMessage(String nickname, PlayerState playerState) {
        this.nickname = nickname;
        this.playerState = playerState;
    }

    public String getNickname() {
        return nickname;
    }

    public PlayerState getPlayerState() {
        return playerState;
    }

    @Override
    public void dispatch(ClientDispatcherInterface clientDispatcherInterface) {
        clientDispatcherInterface.dispatch(this);
    }
}
