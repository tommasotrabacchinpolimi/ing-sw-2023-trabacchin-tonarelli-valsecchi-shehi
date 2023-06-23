package it.polimi.ingsw.net_alternative.clientmessage;

import  it.polimi.ingsw.model.EntryPatternGoal;
import it.polimi.ingsw.net_alternative.ClientMessage;
import it.polimi.ingsw.net_alternative.ClientDispatcherInterface;

import java.util.List;
import java.util.Map;

/**
 * Represents a client message indicating that a personal goal has been assigned to a player.
 * <br>
 * Extends {@link ClientMessage} Interface.
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 */
public class AssignedPersonalGoalNetMessage implements ClientMessage {

    private final String nickname;

    private final List<EntryPatternGoal> goalPattern;


    private final Map<Integer, Integer> scoreMap;

    /**
     * Constructs an instance of AssignedPersonalGoalNetMessage.
     *
     * @param nickname     the nickname of the player to whom the personal goal is assigned
     * @param goalPattern  the list of entry pattern goals representing the assigned personal goal
     * @param scoreMap     the map associating scores to each goal
     */
    public AssignedPersonalGoalNetMessage(String nickname, List<EntryPatternGoal> goalPattern, Map<Integer, Integer> scoreMap) {
        this.nickname = nickname;
        this.goalPattern = goalPattern;
        this.scoreMap = scoreMap;
    }

    /**
     * Returns the nickname of the player to whom the personal goal is assigned.
     *
     * @return the nickname of the player
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Returns the list of entry pattern goals representing the assigned personal goal.
     *
     * @return the list of entry pattern goals
     */
    public List<EntryPatternGoal> getGoalPattern() {
        return goalPattern;
    }

    /**
     * Returns the map associating scores to each goal.
     *
     * @return the score map
     */
    public Map<Integer, Integer> getScoreMap() {
        return scoreMap;
    }

    /**
     * Dispatches the message to the client dispatcher for handling.
     *
     * @param clientDispatcherInterface the client dispatcher interface
     */
    @Override
    public void dispatch(ClientDispatcherInterface clientDispatcherInterface) {
        clientDispatcherInterface.dispatch(this);
    }
}
