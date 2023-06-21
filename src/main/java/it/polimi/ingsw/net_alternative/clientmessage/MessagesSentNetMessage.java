package it.polimi.ingsw.net_alternative.clientmessage;

import it.polimi.ingsw.net_alternative.ClientMessage;
import it.polimi.ingsw.net_alternative.ClientDispatcherInterface;

import java.util.List;

public class MessagesSentNetMessage implements ClientMessage {

    private List<String> senderNicknames;

    private List<List<String>> receiverNicknames;

    private List<String> texts;

    public MessagesSentNetMessage(List<String> senderNicknames, List<List<String>> receiverNicknames, List<String> texts) {
        this.senderNicknames = senderNicknames;
        this.receiverNicknames = receiverNicknames;
        this.texts = texts;
    }

    public List<String> getSenderNicknames() {
        return senderNicknames;
    }

    public List<List<String>> getReceiverNicknames() {
        return receiverNicknames;
    }

    public List<String> getTexts() {
        return texts;
    }

    @Override
    public void dispatch(ClientDispatcherInterface clientDispatcherInterface) {
        clientDispatcherInterface.dispatch(this);
    }
}
