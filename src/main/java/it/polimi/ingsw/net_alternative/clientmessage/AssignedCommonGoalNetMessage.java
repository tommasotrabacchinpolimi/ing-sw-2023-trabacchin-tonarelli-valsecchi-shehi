package it.polimi.ingsw.net_alternative.clientmessage;

import it.polimi.ingsw.net_alternative.ClientMessage;
import it.polimi.ingsw.net_alternative.ClientDispatcherInterface;

/**
 * Represents a client message indicating that a new CommonGoal has been Assigned.
 * <br>
 * Extends {@link ClientMessage} Interface.
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi

 */
public class AssignedCommonGoalNetMessage implements ClientMessage {

    /**
     * Description of the CommonGoal
     */
    private final String description;
    /**
     * number of the CommonGoal
     */
    private final int n;

    /**
     * Id of the commonGoal
     */
    private final String id;

    /**
     * Constructor of the class
     * @param description Is the Description of the CommonGoal
     * @param n Is the number of the CommonGoal
     */
    public AssignedCommonGoalNetMessage(String description, int n, String id) {
        this.description = description;
        this.n = n;
        this.id = id;
    }

    /**
     * Getter method to get the description
     * @return The description of the CommonGoal
     */
    public String getDescription() {
        return description;
    }

    /**
     * Getter Method for the number of the CommonGoal
     * @return the number of the Common Goal
     */
    public int getN() {
        return n;
    }

    /**
     * Getter method for the common goal id
     * @return the common goal id
     */
    public String getId() {
        return id;
    }

    /**
     * It dispatches the message to the Client.
     * @param clientDispatcherInterface is the Handler of the CommonGoal.
     */
    @Override
    public void dispatch(ClientDispatcherInterface clientDispatcherInterface) {
        clientDispatcherInterface.dispatch(this);
    }


}
