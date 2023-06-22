package it.polimi.ingsw.net_alternative.clientmessage;

import  it.polimi.ingsw.model.EntryPatternGoal;
import it.polimi.ingsw.net_alternative.ClientMessage;
import it.polimi.ingsw.net_alternative.ClientDispatcherInterface;

import java.util.List;
import java.util.Map;

public class AssignedPersonalGoalNetMessage implements ClientMessage {

    private String nickname;

    private List<EntryPatternGoal> goalPattern;


    private Map<Integer, Integer> scoreMap;

    public AssignedPersonalGoalNetMessage(String nickname, List<EntryPatternGoal> goalPattern, Map<Integer, Integer> scoreMap) {
        this.nickname = nickname;
        this.goalPattern = goalPattern;
        this.scoreMap = scoreMap;
    }

    public String getNickname() {
        return nickname;
    }

    public List<EntryPatternGoal> getGoalPattern() {
        return goalPattern;
    }

    public Map<Integer, Integer> getScoreMap() {
        return scoreMap;
    }

    @Override
    public void dispatch(ClientDispatcherInterface clientDispatcherInterface) {
        clientDispatcherInterface.dispatch(this);
    }
}
