package it.polimi.ingsw.net.servermessages;

import it.polimi.ingsw.controller.ClientInterface;
import it.polimi.ingsw.net.ServerMessage;
import it.polimi.ingsw.net.ServerDispatcherInterface;

/**
 *
 * Represents a server message for sending a text message to specific receivers.
 * <br>
 *Implements {@link ServerMessage} interface.
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi

 */
public class SentMessageNetMessage implements ServerMessage {

    /**
     * Represents the text of the message
     */
    private final String text;

    /**
     * Represents the receivers of the message
     */
    private final String[] receiversNickname;

    /**
     * Constructs a SentMessageNetMessage object.
     *
     * @param text               the text message to be sent
     * @param receiversNickname  an array of nicknames of the message receivers
     *
     */
    public SentMessageNetMessage(String text, String[] receiversNickname) {
        this.text = text;
        this.receiversNickname = receiversNickname;
    }


    /**
     * Returns the text message.
     *
     * @return the text message
     */
    public String getText() {
        return text;
    }

    /**
     * Returns an array of nicknames of the message receivers.
     *
     * @return an array of nicknames
     */
    public String[] getReceiversNickname() {
        return receiversNickname;
    }

    /**
     * Dispatches the message to the server dispatcher for handling.
     *
     * @param serverDispatcher the server dispatcher interface
     * @param view             the client interface
     */
    @Override
    public void dispatch(ServerDispatcherInterface serverDispatcher, ClientInterface view) {
        serverDispatcher.dispatch(this, view);
    }
}
