package it.polimi.ingsw.controller.listeners;

import java.util.List;

public interface OnMessageSentListener {
    public void onMessageSent(String nicknameSender, List<String> nicknameReceivers, String text);
}
