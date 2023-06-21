package it.polimi.ingsw.net_alternative.clientmessage;

import it.polimi.ingsw.net_alternative.ClientMessage;
import it.polimi.ingsw.net_alternative.ClientDispatcherInterface;

public class AssignedCommonGoalNetMessage implements ClientMessage {

    private String description;

    private int n;

    public AssignedCommonGoalNetMessage(String description, int n) {
        this.description = description;
        this.n = n;
    }

    public String getDescription() {
        return description;
    }

    public int getN() {
        return n;
    }

    @Override
    public void dispatch(ClientDispatcherInterface clientDispatcherInterface) {
        clientDispatcherInterface.dispatch(this);
    }


}
