package it.polimi.ingsw.net_alternative.clientmessage;

import it.polimi.ingsw.net_alternative.ClientMessage;
import it.polimi.ingsw.net_alternative.ClientDispatcherInterface;

import java.util.List;

/**
 * Represents a message to the client, notifying that a message was sent
 * <br>
 * Extends {@link ClientMessage} Interface
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi

 */
public class MessageSentNetMessage implements ClientMessage {

    /**
     * Nickname of the sender of the message
     */
    private final String nickname;

    /**
     * nicknames of all the receivers of the message
     */
    private final List<String> nicknameReceivers;

    /**
     * This variable is used to represent the text of the message
     */
    private final String text;

    /**
     * Constructor of the class
     * @param nickname the nickname of the player that sends the message
     * @param nicknameReceivers The nickname of the player that receives the message
     * @param text the message being sent itself
     */
    public MessageSentNetMessage(String nickname, List<String> nicknameReceivers, String text) {
        this.nickname = nickname;
        this.nicknameReceivers = nicknameReceivers;
        this.text = text;
    }

    /**
     * Getter method that gives the nickname of the sender of the message
     * @return the nickname of the sender of the message
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Getter method that gives the nickname of the receiver of the message
     * @return the nickname of the receiver of the message
     */
    public List<String> getNicknameReceivers() {
        return nicknameReceivers;
    }

    /**
     * Getter method that gives the message being sent
     * @return the text of the message (the message sent itself)
     */
    public String getText() {
        return text;
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
