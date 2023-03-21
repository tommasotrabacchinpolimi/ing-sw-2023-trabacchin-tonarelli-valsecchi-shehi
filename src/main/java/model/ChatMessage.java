package model;

import java.io.Serializable;
import java.util.List;

public class ChatMessage implements Serializable {
    private static final long serialVersionUID = 82642348L;
    private Player sender;
    private List<Player> receivers;
    private String text;

    public ChatMessage(Player sender, List<Player> receivers, String text) {
        this.sender = sender;
        this.receivers = receivers;
        this.text = text;
    }

    public Player getSender() {
        return sender;
    }

    public String getText() {
        return text;
    }

    public List<Player> getReceivers() {
        return receivers;
    }

    public void setSender(Player sender) {
        this.sender = sender;
    }

    public void setReceivers(Player receivers) {
        this.receivers.add(receivers);
    }

    public void setToAll(List<Player> to){
        this.receivers.addAll(to);
    }

    public void setText(String text) {
        this.text = text;
    }
}
