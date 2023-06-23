package it.polimi.ingsw.net_alternative.clientmessage;

import it.polimi.ingsw.net_alternative.ClientMessage;
import it.polimi.ingsw.net_alternative.ClientDispatcherInterface;

/**
 * Represents message for the client indicating a change in the current player.
 * <p>
 * The {@code CurrentPlayerChangedListenerNetMessage} class is a client message that notifies the client about a change
 * in the current player during the game. It contains the nickname of the new current player.
 * </p>
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi

 */
public class CurrentPlayerChangedListenerNetMessage implements ClientMessage {

    private final String nickname;

    /**
     * Constructs a new {@code CurrentPlayerChangedListenerNetMessage} with the specified nickname.
     *
     * @param nickname the nickname of the new current player
     */
    public CurrentPlayerChangedListenerNetMessage(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Returns the nickname of the new current player.
     *
     * @return the nickname of the new current player
     */
    public String getNickname() {
        return nickname;
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
