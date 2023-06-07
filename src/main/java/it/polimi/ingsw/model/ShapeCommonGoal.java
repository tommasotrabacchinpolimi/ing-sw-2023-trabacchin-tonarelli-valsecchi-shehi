package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.JSONExclusionStrategy.ExcludedFromJSON;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;


/**
 * ShapeCommonGoal is a class that represents a generic {@link CommonGoal CommonGoal} which is satisfied if the
 * {@link BookShelf BookShelf} contains a particular shape of the same tile type. TODO
 *
 *  @apiNote Valid combination of the parameters values are the following : .... TO DO
 *
 *  @see CommonGoal
 *  @see BookShelf
 *  @see EntryPatternGoal
 *
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 * @version 2.0
 * @since 03/04/2023
 */
public class ShapeCommonGoal extends CommonGoal implements Serializable {
    @Serial
    @ExcludedFromJSON
    private static final long serialVersionUID = 746524795L;
    /**
      * the list of array elements needed to this class to implement the Shape-CommonGoal
      *
      * @see #getRuleShape()
      *
      * @apiNote The key of this class is this parameter that contains offsets needed
      * to finds if there is a common-goal. This class makes the check of 3 candidate
      * common-goals (Common-goal 2, 3, 10).
      */
    private List<Integer[]> ruleShape;

    /**
     * Constructor that calls the constructor of superclass {@link CommonGoal}.
     * @see CommonGoal#CommonGoal()
     */
    public ShapeCommonGoal() {
        super();
    }

    /**
     * Constructor that sets the fields of the class to the parameter passed.
     * @param ruleShape the list of arrays that permit to implement the function rule.
     * @see CommonGoal#CommonGoal()
     */
    public ShapeCommonGoal(List<Integer[]> ruleShape) {
        super();
        this.ruleShape = ruleShape;
    }

    /**
     * Constructor that sets the fields of the class to the parameter passed.
     * @param description Textual description of the goal.
     * @param ruleShape the list of arrays that permit to implement the function rule.
     * @see CommonGoal#CommonGoal(String) CommonGoal(String description)
     */
    public ShapeCommonGoal(String description, List<Integer[]> ruleShape) {
        super(description);
        this.ruleShape = ruleShape;
    }

    /**
     * Constructor that sets the fields of the class to the parameter passed.
     * @param description Textual description of the goal.
     * @param ruleShape the list of arrays that permit to implement the function rule.
     * @param scoringTokens Scoring tokens stack.
     * @see CommonGoal#CommonGoal(Stack, String)
     */
    public ShapeCommonGoal(Stack<Integer> scoringTokens, String description, List<Integer[]> ruleShape) {
        super(scoringTokens, description);
        this.ruleShape = ruleShape;
    }

    /**
     * Method that return the {@link #ruleShape} list.
     * @return the list of arrays that contains the offsets that permit to implement
     * for more detailed explanation.
     * For more click here: [{@link ShapeCommonGoal#ruleShape incrementRuleShape}]
     *
     */
    public List<Integer[]> getRuleShape() {
        return this.ruleShape;
    }

    /**
     * Method that sets the {@link #ruleShape} list.
     * @param ruleShape list of integer that needs to set the value of [{@link ShapeCommonGoal#ruleShape incrementRuleshape}]
     */
    public void setRuleShape(List<Integer[]> ruleShape) {
        this.ruleShape = ruleShape;
    }

    /**
     * The method returns null if the ShapeCommonGoal is not satisfied for the bookShelf passed as argument.
     * @param bookShelf the BookShelf to check for the ShapeCommonGoal
     * @apiNote For the implementation of this method, this parameter is considered as a matrix
     * @return null if the ShapeCommonGoal is satisfied, otherwise the list of the EntryPatternGoals representing
     * the tiles in the BookShelf that satisfy the ShapeCommonGoal
     *
     */
    @Override
    public List<EntryPatternGoal> rule(TileType[][] bookShelf) {
        boolean check = true;
        TileType type;
        List<EntryPatternGoal> result = new ArrayList<EntryPatternGoal>(ruleShape.size()+1);
        List<Integer[]> ruleCopy = new ArrayList<>(ruleShape);

        for(int i = 0; i < bookShelf.length; i++) {
            if(i == bookShelf.length/2 + 1) {
                for (Integer[] integers : ruleCopy) {
                    integers[1] *= -1;
                }

            }
            for(int j = 0; j < bookShelf[0].length; j++) {

                if(!check)
                    result.clear();

                result.add( new EntryPatternGoal(i, j, bookShelf[i][j]) );

                check = true;

                if (!(verifyInField(bookShelf[0].length, bookShelf.length, j, i, ruleCopy))) {
                    check = false;
                } else {
                    type = bookShelf[i][j];
                    for(Integer[] temp : ruleCopy) {
                        int rowProcessed = i + temp[0];
                        int columnProcessed = j + temp[1];

                        if((type == null) ||
                                (!(type.equals(bookShelf[rowProcessed][columnProcessed])))) {
                            check = false;
                            break;
                        }

                        result.add(new EntryPatternGoal(rowProcessed, columnProcessed, bookShelf[rowProcessed][columnProcessed]));
                    }

                    if (check && verifySurrounding(result, bookShelf)) {
                        return result;
                    }
                }
            }
        }

        return null;
    }

    /**
     *
     * @param maxColumnDim It is the length of the bookshelf, tells where is the vertical limit to check
     *                     for tails.
     * @param maxRowDim    It is the width of the bookshelf, tells where is the horizontal limit to check
     *                     for tails.
     * @param indexColumn  It is the index of column of the current element being verified
     * @param indexRow     It is the index of row of the current element being verified
     * @return If an element with indexes (i,j), added to the corresponding array of the list incrementRuleShape
     * @see #ruleShape
     *
     */
    private boolean verifyInField(int maxColumnDim, int maxRowDim, int indexColumn, int indexRow, List<Integer[]> ruleCopy) {
        for(Integer[] temp : ruleCopy) {
            if(!((indexColumn + temp[1] < maxColumnDim) &&
                    (indexRow + temp[0] < maxRowDim) && (indexColumn + temp[1] >= 0) && indexRow + temp[0] >= 0))
                return false;
        }
        return true;
    }

    /**
     *
     * @param candidate Is a list of tails inside the bookshelf that is candidate to be one the 3 shapes
     *                  that permit to score CommonGoal
     * @param bookShelf It is the same bookShelf being passed as parameter to the rule method
     * @return If around the exact shape needed to be done there is another tail of the same type.
     * @apiNote It is very important to verify that because for example a diagonal of 6 elements does not
     *          score common goal.
     */
    private boolean verifySurrounding(List<EntryPatternGoal> candidate, TileType[][] bookShelf){
        for(EntryPatternGoal e : candidate) {
            int row = e.getRow() - 1;
            int column = e.getColumn() - 1;
            for(int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if(insideBookshelfBound(row + i,column + j, bookShelf.length, bookShelf[0].length) &&
                            notInShape(row + i, column + j, candidate)){
                            if(e.getTileType().equals(bookShelf[row + i][column + j])){
                                return false;
                            }
                    }
                }
            }
        }
        return  true;
    }

    /**
     *
     * @param row
     * @param column
     * @param candidate
     * @return
     */
    private boolean notInShape(int row, int column, List<EntryPatternGoal> candidate) {
        for(EntryPatternGoal e : candidate) {
            if((e.getArrayIndexes()[0] == row) &&
                    (e.getArrayIndexes()[1] == column)) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @param row it is the row index of the element to check
     * @param column it is the column index of the element to check
     * @param maxHeight It is the maximum column index being allowed (Bookshelf Height)
     * @param maxWidth It is the maximum row index being allowed (Bookshelf Width)
     * @return If an element with index row, column is or not part of the bookshelf
     */
    private boolean insideBookshelfBound(int row, int column, int maxHeight, int maxWidth){
        return ((row >= 0 &&  row < maxHeight) && (column >= 0  && column < maxWidth));
    }

    /**
     * Overriding toString() default method.
     * @return a {@link String} representing the {@link ShapeCommonGoal}.
     * @apiNote Resulting String will be displayed on different lines as follows:
     * <pre>
     *     ShapeCommonGoal{
     *              Scoring Tokens:
     *              Description:
     *              Rule Shape:
     *           }
     * </pre>
     */
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();

        res.append("ShapeCommonGoal{")
                .append(System.getProperty("line.separator"))
                .append(super.toString())
                .append(System.getProperty("line.separator"))
                .append("\tRule Shape:[");

        ruleShape.forEach(x -> res
                .append(System.getProperty("line.separator"))
                .append("\t\t(")
                .append(x[0])
                .append(", ")
                .append(x[1])
                .append(")"));
        res.append(System.getProperty("line.separator"))
                .append("\t]")
                .append(System.getProperty("line.separator"))
                .append("}");

        return res.toString();
    }

}