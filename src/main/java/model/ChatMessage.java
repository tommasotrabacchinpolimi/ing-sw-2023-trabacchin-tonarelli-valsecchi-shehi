package model;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class ChatMessage implements Serializable {
    private static final long serialVersionUID = 82642348L;
    private Player from;
    private List<Player> to;
    private String text;

    public Player getFrom() {
        return from;
    }

    public String getText() {
        return text;
    }

    public List<Player> getTo() {
        return to;
    }

    public void setFrom(Player from) {
        this.from = from;
    }

    public void setTo(Player to) {
        this.to.add(to);
    }

    public void setToAll(List<Player> to){
        this.to.addAll(to);
    }

    public void setText(String text) {
        this.text = text;
    }


}
