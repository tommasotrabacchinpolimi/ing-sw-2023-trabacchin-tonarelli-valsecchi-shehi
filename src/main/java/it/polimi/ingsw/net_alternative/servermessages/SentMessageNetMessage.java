package it.polimi.ingsw.net_alternative.servermessages;

import it.polimi.ingsw.controller.ClientInterface;
import it.polimi.ingsw.net_alternative.ServerMessage;
import it.polimi.ingsw.net_alternative.ServerDispatcherInterface;

public class SentMessageNetMessage implements ServerMessage {

    private String text;
    private String[] receiversNickname;
    public SentMessageNetMessage(String text, String[] receiversNickname) {
        this.text = text;
        this.receiversNickname = receiversNickname;
    }

    public String getText() {
        return text;
    }

    public String[] getReceiversNickname() {
        return receiversNickname;
    }

    @Override
    public void dispatch(ServerDispatcherInterface serverDispatcher, ClientInterface view) {
        serverDispatcher.dispatch(this, view);
    }
}
