package it.polimi.ingsw.controller.listeners;

import java.util.List;

/**
 * A listener interface for notifying when a message is sent.
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public interface OnMessageSentListener {
    /**
     * Called when a message is sent by a sender to one or more receivers.
     * @param nicknameSender The nickname of the sender.
     * @param nicknameReceivers The nicknames of the receivers.
     * @param text The content of the message.
     */
    public void onMessageSent(String nicknameSender, List<String> nicknameReceivers, String text);

    /**
     * Called when multiple messages are sent by multiple senders to multiple receivers.
     * @param senderNicknames The nicknames of the senders.
     * @param receiverNicknames The nicknames of the receivers for each message.
     * @param texts The contents of the messages.
     */
    public void onMessagesSentUpdate(List<String> senderNicknames, List<List<String>> receiverNicknames, List<String> texts);
}
