package it.polimi.ingsw.net_alternative.clientmessage;

import it.polimi.ingsw.net_alternative.ClientMessage;
import it.polimi.ingsw.net_alternative.ClientDispatcherInterface;

import java.util.List;

/**
 * Represents the chat being sent.
 * <br>
 * It can be utilized when a client lost his connection and needs to sent again all the chat.
 * <br>
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi

 */
public class MessagesSentNetMessage implements ClientMessage {

    private final List<String> senderNicknames;

    private final List<List<String>> receiverNicknames;

    private final List<String> texts;

    /**
     * Constructor of the class
     * @param senderNicknames the list of nicknames that sent messages
     * @param receiverNicknames the list of nicknames that received messages
     * @param texts messages being sent
     */
    public MessagesSentNetMessage(List<String> senderNicknames, List<List<String>> receiverNicknames, List<String> texts) {
        this.senderNicknames = senderNicknames;
        this.receiverNicknames = receiverNicknames;
        this.texts = texts;
    }

    /**
     * Getter method to get all nickname senders of messages
     * @return the list of nickname senders
     */
    public List<String> getSenderNicknames() {
        return senderNicknames;
    }

    /**
     * Getter method to get all nickname receivers of messages
     * @return the list of nickname receivers
     */
    public List<List<String>> getReceiverNicknames() {
        return receiverNicknames;
    }

    /**
     * Getter method to get all messages being sent
     * @return messages being sent
     */
    public List<String> getTexts() {
        return texts;
    }

    /**
     * It dispatches the message to the client
     * @param clientDispatcherInterface is the handler of the message.
     */
    @Override
    public void dispatch(ClientDispatcherInterface clientDispatcherInterface) {
        clientDispatcherInterface.dispatch(this);
    }
}
