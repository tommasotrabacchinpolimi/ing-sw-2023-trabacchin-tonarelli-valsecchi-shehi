package it.polimi.ingsw.controller.listeners;

import java.util.List;

public interface OnMessageSentListener {
    public void onMessageSent(String nicknameSender, List<String> nicknameReceivers, String text);
    public void onMessagesSentUpdate(List<String> senderNicknames, List<List<String>> receiverNicknames, List<String> texts);
}
