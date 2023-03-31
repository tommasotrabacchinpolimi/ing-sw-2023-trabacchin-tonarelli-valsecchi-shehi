package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.List;

public abstract class CommonGoal implements Serializable {
    private static final long serialVersionUID = 285236373L;
    private static final int INITIAL_VALUE = 8;

    /**
     * Values of the available score that will be assigned to the next player that will satisfy that exact CommonGoal
     */
    private int availableScore;
    /**
     * Textual description of the Common Goal
     */
    private String description;

    public CommonGoal(int availableScore, String description) {
        this.availableScore = availableScore;
        this.description = description;
    }

    public CommonGoal(String description){
        this.availableScore = INITIAL_VALUE;
        this.description = description;
    }

    /**
     * Get the values of the available score that will be assigned to the next player that will satisfy that exact CommonGoal
     * @return Values of the available score that will be assigned to the next player that will satisfy that exact CommonGoal
     */
    public int getAvailableScore() {
        return availableScore;
    }

    /**
     * Set values of the available score that will be assigned to the next player that will satisfy that exact CommonGoal
     * @param availableScore value used to set availableScore
     */
    public void setAvailableScore( int availableScore ) {
        this.availableScore = availableScore;
    }

    /**
     * Get textual description of the Common Goal
     * @return textual description of the Common Goal
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set textual description of the Common Goal
     * @param description textual description of the Common Goal
     */
    public void setDescription( String description ) {
        this.description = description;
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
