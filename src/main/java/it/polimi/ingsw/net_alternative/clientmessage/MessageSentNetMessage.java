package it.polimi.ingsw.net_alternative.clientmessage;

import it.polimi.ingsw.net_alternative.ClientMessage;
import it.polimi.ingsw.net_alternative.ClientDispatcherInterface;

import java.util.List;

public class MessageSentNetMessage implements ClientMessage {

    private String nickname;

    private List<String> nicknameReceivers;

    private String text;

    public MessageSentNetMessage(String nickname, List<String> nicknameReceivers, String text) {
        this.nickname = nickname;
        this.nicknameReceivers = nicknameReceivers;
        this.text = text;
    }

    public String getNickname() {
        return nickname;
    }

    public List<String> getNicknameReceivers() {
        return nicknameReceivers;
    }

    public String getText() {
        return text;
    }

    @Override
    public void dispatch(ClientDispatcherInterface clientDispatcherInterface) {
        clientDispatcherInterface.dispatch(this);
    }
}
