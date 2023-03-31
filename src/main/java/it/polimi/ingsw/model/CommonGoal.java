package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.List;
import java.util.Stack;

public abstract class CommonGoal implements Serializable {
    private static final long serialVersionUID = 285236373L;

    /**
     * Values of the available score that will be assigned to the next player that will satisfy that exact CommonGoal
     */
    private final Stack<Integer> scoringTokens;
    /**
     * Textual description of the Common Goal
     */
    private final String description;

    public CommonGoal(Stack<Integer> scoringTokens, String description) {
        this.scoringTokens = scoringTokens;
        this.description = description;
    }

    public CommonGoal(String description){
        this.scoringTokens = new Stack<>();
        this.description = description;
    }

    public CommonGoal(int numberPlayer, String description){
        this.scoringTokens = new Stack<>();
        initScoringTokens(numberPlayer);
        this.description = description;
    }

    public int getAvailableScore() {
        return scoringTokens.pop();
    }

    public void initScoringTokens(int numberOfPlayers){
        if(numberOfPlayers == 4) this.scoringTokens.push(2);
        this.scoringTokens.push(4);
        if (numberOfPlayers >= 3) this.scoringTokens.push(6);
        this.scoringTokens.push(8);
    }

    /**
     * Get textual description of the Common Goal
     * @return textual description of the Common Goal
     */
    public String getDescription() {
        return description;
    }


    /**
     * The method returns null if the CommonGoal is not satisfied for the bookShelf passes as argument.
     * If the CommonGoal is satisfied then the method returns the list of the EntryPatternGoals representing
     * the tiles in the BookShelf that satisfy the CommonGoal.
     * @param bookShelf the BookShelf to check for the LineCommonGoal
     * @return null if the CommonGoal is not satisfied, otherwise the list of EntryPatternGoal that satisfied the CommonGoal
     */
    public abstract List<EntryPatternGoal> rule(TileType[][] bookShelf);

}
