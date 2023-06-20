package it.polimi.ingsw.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Stack;

/**
 * <p>This class is used to define a generic standard structure for all common goal cards.
 * Each common goal card have its own {@link #description explanation} and a set of {@link #scoringTokens points} associated.
 * The set of points associated to the card are ordered from the higher to the lower and only the higher number present can be
 * retrieved, which is also the top-leve number. Then the functioning of the points is a sort of stack.</p>
 *
 * <p>Default value for:
 * <ul>
 *     <li>{@link #scoringTokens scoring tokens stack} is not a {@code null} reference, but an empty sets of element</li>
 *     <li>{@link #description description} is "Empty description"</li>
 * </ul>
 * </p>
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 * @version 3.0
 * @since 11/04/2023
 *
 */
public abstract class CommonGoal implements Serializable {
    @Serial
    private static final long serialVersionUID = 285236373L;

    /**
     * Values of the available score that will be assigned to the next player that will satisfy that exact CommonGoal.
     * This field represents the scoring tokens associated with the common goal card.
     *
     * @see CommonGoal
     */
    private final Stack<Integer> scoringTokens;

    /**
     * Explanation of the Common Goal Card
     *
     * @see CommonGoal
     */
    private final String description;

    /**
     * A unique id representing the common goal and is used
     * to distinguish different common goal of the same type
     */
    private final String id;

    /**
     * <p>Create a common goal with default values for both {@linkplain #scoringTokens scoring tokens stack} and
     * {@linkplain #description explanation of the card}</p>
     *
     * @see CommonGoal
     */
    public CommonGoal() {
        this(new Stack<>(), "No description", "error_common_goal");
    }

    /**
     * <p>Create a common goal with default values for {@linkplain #scoringTokens scoring tokens stack}. On the other
     * hand the {@linkplain #description explanation of the card} is set according to the parameter</p>
     *
     * @param description explanation of common goal card
     */
    public CommonGoal(String description, String id){
        this(new Stack<>(), description, id);
    }

    /**
     * <p>Create a common goal with values corresponding to the parameter for both
     * {@linkplain #scoringTokens scoring tokens stack} and {@linkplain #description explanation of the card}</p>
     *
     * @param scoringTokens scoring tokens stack
     * @param description explanation of common goal card
     */
    public CommonGoal(Stack<Integer> scoringTokens, String description, String id) {
        this.scoringTokens = scoringTokens;
        this.description = description;
        this.id = id;
    }

    /**
     * @return all scoring tokens top to bottom ordered
     */
    public Stack<Integer> getScoringTokens() {
        return scoringTokens;
    }

    /**
     * Method that look at the available score of the {@link CommonGoal} without changing it.
     * @return the available score of the {@link CommonGoal}.
     */
    public int getAvailableScore() {
        if(scoringTokens.size()==0)
            return 0;
        else {
            return scoringTokens.peek();
        }
    }

    /**
     * Removes the value at the top of the {@link #scoringTokens} stack and returns that value as the value of this function.
     * @return the previous value at the top of the {@link #scoringTokens} stack.
     */
    public int removeAvailableScore() {
        if(scoringTokens.size()==0)
            return 0;
        else {
            return scoringTokens.pop();
        }
    }

    /**
     * Get textual description of the Common Goal
     * @return textual description of the Common Goal
     */
    public String getDescription() {
        return description;
    }

    /**
     * Retrieve the unique ID associated to each common goal.
     *
     * @return an ID representing the common goal
     */
    public String getId() {
        return id;
    }

    /**
     * <p>The method returns {@code null} if the {@link CommonGoal} is not satisfied for the {@code bookShelf} argument.</p>
     * <p>If the common goal is satisfied then the method returns a list of the {@link EntryPatternGoal EntryPatternGoals}
     * representing the {@link TileType tiles} in the {@link BookShelf} that satisfy the {@link CommonGoal}.</p>
     *
     * @param bookShelf the {@link BookShelf bookshelf} to be checked
     * @return <ul><li>{@code null} if the {@link CommonGoal} is not satisfied</li>
     * <li>list of {@link EntryPatternGoal} that satisfied the {@link CommonGoal} otherwise</li></ul>
     */
    public abstract List<EntryPatternGoal> rule(TileType[][] bookShelf);

    /**
     * Overriding toString() default method.
     * @return a {@link String} representing the {@link CommonGoal}.
     * @apiNote Resulting String will be displayed on different lines as follows:
     * <pre>
     *  LineCommonGoal{
     *      Scoring Tokens:
     *      Description:
     *  }
     *  </pre>
     */
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder("\tScoring Tokens: ");

        scoringTokens.forEach(s -> res.append(s).append(", "));

        res.append(System.getProperty("line.separator"))
                .append("\tDescription: ")
                .append(description);

        return res.toString();
    }
}
