package it.polimi.ingsw.net_alternative.clientmessage;

import it.polimi.ingsw.net_alternative.ClientMessage;
import it.polimi.ingsw.net_alternative.ClientDispatcherInterface;

/**
 * Represents a client message indicating an update on the last player.
 * <p>
 * The {@code LastPlayerUpdatedNetMessage} class is a client message that notifies the client about an update on the
 * last player in the game. It contains the nickname of the last player.
 * </p>
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi

 */
public class LastPlayerUpdatedNetMessage implements ClientMessage {

    /**
     * The nickname of the last player
     */
    private final String nicknameLastPlayer;

    /**
     * Constructs a new {@code LastPlayerUpdatedNetMessage} with the specified nickname of the last player.
     *
     * @param nicknameLastPlayer the nickname of the last player
     */
    public LastPlayerUpdatedNetMessage(String nicknameLastPlayer) {
        this.nicknameLastPlayer = nicknameLastPlayer;
    }

    /**
     * Returns the nickname of the last player.
     *
     * @return the nickname of the last player
     */
    public String getNicknameLastPlayer() {
        return nicknameLastPlayer;
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
