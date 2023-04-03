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
        if(scoringTokens.size()==0) return 0;
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
     * The method returns {@code null} if the {@link CommonGoal} is not satisfied for the {@code bookShelf} argument.
     * If the common goal is satisfied then the method returns a list of the {@link EntryPatternGoal EntryPatternGoals}
     * representing the {@link TileType tiles} in the {@link BookShelf} that satisfy the {@link CommonGoal}.
     *
     * @param bookShelf the {@link BookShelf bookshelf} to be checked
     * @return <ul><li>{@code null} if the {@link CommonGoal} is not satisfied</li>
     * <li>list of {@link EntryPatternGoal} that satisfied the {@link CommonGoal} otherwise</li></ul>
     */
    public abstract List<EntryPatternGoal> rule(TileType[][] bookShelf);

}
