package model;

import java.lang.reflect.Type;

public class ChatMessage {
    private Player from;
    private Player[] to;
    private String text;

    public Player getFrom() {
        return from;
    }

    public String getText() {
        return text;
    }

    public Player[] getTo() {
        return to;
    }

    public void setFrom(Player from) {
        this.from = from;
    }

    public void setTo(Player[] to) {
        this.to = to;
    }

    public void setText(String text) {
        this.text = text;
    }


}
