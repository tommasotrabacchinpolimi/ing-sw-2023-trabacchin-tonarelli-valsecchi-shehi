package it.polimi.ingsw.net_alternative.clientmessage;

import it.polimi.ingsw.net_alternative.ClientMessage;
import it.polimi.ingsw.net_alternative.ClientDispatcherInterface;

/**
 * Represents message for the client indicating a change in the available score for common goals.
 * It implements the {@link ClientMessage} interface.
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi

 */
public class ChangedCommonGoalAvailableScoreNetMessage implements ClientMessage {

    private final int score;

    private final int numberOfCommonGoal;

    /**
     * Constructor of the class
     * @param score the available score
     * @param numberOfCommonGoal number of the commonGoal
     */
    public ChangedCommonGoalAvailableScoreNetMessage(int score, int numberOfCommonGoal) {
        this.score = score;
        this.numberOfCommonGoal = numberOfCommonGoal;
    }

    /**
     * Getter method to have the available score
     * @return the score of the commonGoal
     */
    public int getScore() {
        return score;
    }

    /**
     * Getter method to get the number of the commonGoal
     * @return the number of the CommonGoal
     */
    public int getNumberOfCommonGoal() {
        return numberOfCommonGoal;
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
