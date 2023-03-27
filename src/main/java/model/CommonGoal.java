package model;

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
     * The method returns null if the CommonGoal is not satisfied for the bookShelf passes as argument.
     * If the CommonGoal is satisfied then the method returns the list of the EntryPatternGoals representing
     * the tiles in the BookShelf that satisfy the CommonGoal.
     * @param bookShelf the BookShelf to check for the LineCommonGoal
     * @return null if the CommonGoal is not satisfied, otherwise the list of EntryPatternGoal that satisfied the CommonGoal
     */
    public abstract List<EntryPatternGoal> rule(TileType[][] bookShelf);

}
