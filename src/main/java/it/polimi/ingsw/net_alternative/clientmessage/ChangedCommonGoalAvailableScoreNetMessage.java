package it.polimi.ingsw.net_alternative.clientmessage;

import it.polimi.ingsw.net_alternative.ClientMessage;
import it.polimi.ingsw.net_alternative.ClientDispatcherInterface;

public class ChangedCommonGoalAvailableScoreNetMessage implements ClientMessage {

    private int score;

    private int numberOfCommonGoal;

    public ChangedCommonGoalAvailableScoreNetMessage(int score, int numberOfCommonGoal) {
        this.score = score;
        this.numberOfCommonGoal = numberOfCommonGoal;
    }

    public int getScore() {
        return score;
    }

    public int getNumberOfCommonGoal() {
        return numberOfCommonGoal;
    }

    @Override
    public void dispatch(ClientDispatcherInterface clientDispatcherInterface) {
        clientDispatcherInterface.dispatch(this);
    }
}
