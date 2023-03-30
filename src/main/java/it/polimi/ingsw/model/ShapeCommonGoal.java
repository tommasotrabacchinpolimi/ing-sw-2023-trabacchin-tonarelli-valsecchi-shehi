package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.*;

public class ShapeCommonGoal extends CommonGoal implements Serializable {
    private static final long serialVersionUID = 746524795L;
    private int tileNumber;
    /**
     * the a list of array elements needed to this class to implement the Shape-CommonGoal
     *
     * @see #getRuleShape()
     *
     * @apiNote The key of this class is this parameter that contains offsets needed
     * to finds if there is a common-goal. This class makes the check of 3 candidate
     * common-goals (Common-goal 2, 3, 10).
     */
    private List<Integer[]> ruleShape;

    /**
     *
     * @param tileNumber it is the number of the array in the list of arrays.
     * @param ruleShape It is the list of arrays that permit to implement the function rule.
     */

    public ShapeCommonGoal(int tileNumber, List<Integer[]> ruleShape, String description) {
        super(description);
        this.tileNumber = tileNumber;
        this.ruleShape = ruleShape;
    }


    /**
     *
     * @return the number of Tiles,taken from input.
     */

    public int getTileNumber() {
        return tileNumber;
    }

    /**
     *
     * @param tileNumber Needed to set the corrent value of [{@link ShapeCommonGoal#tileNumber tileNumber}]
     */

    public void setTileNumber(int tileNumber) {
        this.tileNumber = tileNumber;
    }

    /**
     *
     * @return the list of arrays that contains the offsets that permit to implement
     * for more detailed explanation
     *
     * For more click here: [{@link ShapeCommonGoal#ruleShape incrementRuleShape}]
     *
     */

    public List<Integer[]> getRuleShape() {
        return this.ruleShape;
    }

    /**
     *
     * @param ruleShape needed to set the value of [{@link ShapeCommonGoal#ruleShape incrementRuleshape}]
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

        for(int i = 0; i < bookShelf.length; i++) {
            for(int j = 0; j < bookShelf[0].length; j++) {

                if(!check)
                    result.clear();

                result.add( new EntryPatternGoal(j, i, bookShelf[i][j]) );

                check = true;

                if (!(verifyInField(bookShelf[0].length, bookShelf.length, j, i))) {
                    check = false;
                } else {
                    type = bookShelf[i][j];
                    for(Integer[] temp : ruleShape) {
                        int rowProcessed = i + temp[0];
                        int columnProcessed = j + temp[1];

                        if((type == null) ||
                                (!(type.equals(bookShelf[rowProcessed][columnProcessed])))) {
                            check = false;
                            break;
                        }

                        result.add(new EntryPatternGoal(columnProcessed, rowProcessed, bookShelf[rowProcessed][columnProcessed]));
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
     * @param indexRow It is the index of row of the current element being verified
     * @return If an element with indexes (i,j), added to the correspective array of the list incrementRuleShape
     * @see #ruleShape
     *
     */
    private boolean verifyInField(int maxColumnDim, int maxRowDim, int indexColumn, int indexRow) {
        for(Integer[] temp : ruleShape) {
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
     *          score commongoal.
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

    private boolean verifyTileType(TileType[][] bookshelff, List<Integer[]> list) {
        boolean result = true;
        TileType type = bookshelff[0][0];
        for (int i=1; i<list.size(); i++)
        {
            if(!(type.equals(bookshelff[list.get(i)[0]][list.get(i)[1]])));
            //ho una lista di posizioni.
        }
        return false;
    }

}