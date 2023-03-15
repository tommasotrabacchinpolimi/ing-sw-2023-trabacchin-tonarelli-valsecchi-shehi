package model;

import java.io.Serializable;
import java.util.List;

public class PersonalGoal implements Serializable {
    private static final long serialVersionUID = 52353836745724632L;
    private List<Triplet> goalPattern;

    public PersonalGoal(){

    }

    public PersonalGoal(List<Triplet> goalPattern) {
        this.goalPattern = goalPattern;
    }

    public List<Triplet> getGoalPattern() {
        return goalPattern;
    }

    public void setGoalPattern(List<Triplet> goalPattern) {
        this.goalPattern = goalPattern;
    }
}
