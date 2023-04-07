package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.JSONExclusionStrategy.ExcludedFromJSON;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 *  {@link LineCommonGoal} is a class that represents a generic {@link CommonGoal} which is satisfied if the {@link BookShelf} contains a given number
 *  of lines (rows or columns) each having a given number of different {@link TileType tile type}.
 *
 *  @author Melanie Tonarelli
 *  @version 1.0, 25/03/23
 *  @apiNote Valid combination of the parameters values are the following :
 *          (incRow = 1, incCol = 0, linesNumber = 4, numberOfTiles = 5, differentTiles = {1,2,3}),
 *          (incRow = 1, incCol = 0, linesNumber = 2, numberOfTiles = 5, differentTiles = {5}),
 *          (incRow = 0, incCol = 1, linesNumber = 3, numberOfTiles = 6, differentTiles = {1,2,3}) e
 *          (incRow = 0, incCol = 1, linesNumber = 2, numberOfTiles = 6, differentTiles = {6})
 *  @see CommonGoal
 *  @see BookShelf
 *  @see EntryPatternGoal
 *  @see TileType
 */
public class LineCommonGoal extends CommonGoal implements Serializable {
    @Serial
    @ExcludedFromJSON
    private static final long serialVersionUID = 83625649L;
    /**
     * Increment of row, that is set to 1 if and only if the lines that have to satisfy the given condition are rows of a matrix.
     * @apiNote if <code>incRow</code> is set to 1, then {@link LineCommonGoal#incCol} must be set to 0.
     * @see LineCommonGoal#getIncRow()
     * @see LineCommonGoal#setIncRow(int)
     * @see LineCommonGoal#incCol
     */
    @ExcludedFromJSON
    private int incRow;
    /**
     * Increment of column, that is set to 1 if and only if the lines that have to satisfy the given condition are columns of a matrix.
     * @apiNote if <code>incCol</code> is set to 1, then {@link LineCommonGoal#incRow} must be set to 0.
     * @see LineCommonGoal#getIncCol()
     * @see LineCommonGoal#setIncCol(int)
     * @see LineCommonGoal#incRow
     */
    @ExcludedFromJSON
    private int incCol;
    /**
     * The number of lines that needs to satisfy the condition in order to complete the Goal
     * @see LineCommonGoal#getLinesNumber()
     * @see LineCommonGoal#setLinesNumber(int)
     */
    @ExcludedFromJSON
    private int linesNumber;

    /**
     * The minimum number of tiles that must be found in the lines to satisfy the given condition.
     * @see LineCommonGoal#getNumberOfTiles()
     * @see LineCommonGoal#setNumberOfTiles(int)
     */
    @ExcludedFromJSON
    private int numberOfTiles;
    /**
     * The array that contains the number of different {@link TileType tile types} that each lines needs to have in order to satisfy the Goal. The array of int is ordered.
     * @apiNote If <code>differentTiles = {6}</code> then in the lines there has to be 6 different {@link TileType tile type} in order to satisfy the given condition.
     * If <code>differentTiles = {1, 2, 3}</code> then in the lines there has to be maximum 3 different {@link TileType tile type} in order to satisfy the given condition; in other words,the lines must be formed of 1, 2 or 3 different {@link TileType tile types}.
     */
    @ExcludedFromJSON
    private int[] differentTiles;

    public LineCommonGoal(int incRow, int incCol, int linesNumber, int numberOfTiles, int[] differentTiles) {
        super();
        this.incRow = incRow;
        this.incCol = incCol;
        this.linesNumber = linesNumber;
        this.numberOfTiles = numberOfTiles;
        this.differentTiles = removeDuplicateAndSort(differentTiles);
    }

    /**
     * Used to create the SquareCommonGoal.
     * @param incRow         Increment of row, that is set to 1 if and only if the lines that have to satisfy the condition are rows of a matrix.
     * @param incCol         Increment of column, that is set to 1 if and only if the lines that have to satisfy the condition are columns of a matrix.
     * @param linesNumber    The number of lines that needs to satisfy the condition.
     * @param numberOfTiles  The minimum number of tiles that must be found in the lines.
     * @param differentTiles The array that contains the number of different {@link TileType} that each lines needs to have.
     * @param description    The textual description of the goal.
     *
     * @apiNote The array differentTiles is sorted automatically and doesn't have duplicate elements.
     * @see LineCommonGoal#incRow
     * @see LineCommonGoal#incCol
     * @see LineCommonGoal#linesNumber
     * @see LineCommonGoal#numberOfTiles
     * @see LineCommonGoal#differentTiles
     */
    public LineCommonGoal(String description, int incRow, int incCol, int linesNumber, int numberOfTiles, int[] differentTiles) {
        super(description);
        this.incRow = incRow;
        this.incCol = incCol;
        this.linesNumber = linesNumber;
        this.numberOfTiles = numberOfTiles;
        this.differentTiles = removeDuplicateAndSort(differentTiles);
    }

    public LineCommonGoal(Stack<Integer> scoringTokens, String description, int incRow, int incCol, int linesNumber, int numberOfTiles, int[] differentTiles) {
        super(scoringTokens, description);
        this.incRow = incRow;
        this.incCol = incCol;
        this.linesNumber = linesNumber;
        this.numberOfTiles = numberOfTiles;
        this.differentTiles = removeDuplicateAndSort(differentTiles);
    }

    /**
     * Get the minimum number of tiles that must be found in the lines to satisfy the goal.
     * @return the minimum number of tiles that must be found in the lines to satisfy the goal.
     *
     * @see LineCommonGoal#numberOfTiles
     */
    public int getNumberOfTiles() {
        return numberOfTiles;
    }

    /**
     * Set the minimum number of tiles that must be found in the lines to satisfy the goal.
     * @param numberOfTiles the minimum number of tiles that must be found in the lines to satisfy the goal.
     *
     * @see LineCommonGoal#numberOfTiles
     */
    public void setNumberOfTiles(int numberOfTiles) {
        this.numberOfTiles = numberOfTiles;
    }

    /**
     * Get the increment of row.
     * @return incRow value.
     * @see LineCommonGoal#incRow
     */
    public int getIncRow() {
        return incRow;
    }

    /**
     * Sets the increment of row.
     * @param incRow The value of incRow.
     * @see LineCommonGoal#incRow
     */
    public void setIncRow(int incRow) {
        this.incRow = incRow;
    }

    /**
     * Get the increment of column.
     * @return incCol value.
     * @see LineCommonGoal#incCol
     */
    public int getIncCol() {
        return incCol;
    }

    /**
     * Sets the increment of column.
     * @param incCol The value of incCol.
     * @see LineCommonGoal#incCol
     */
    public void setIncCol(int incCol) {
        this.incCol = incCol;
    }

    /**
     * Get the number of lines that needs to satisfy the condition in order to complete the goal.
     * @return The number of lines that needs to satisfy the condition in order to complete the goal.
     * @see LineCommonGoal#linesNumber
     */
    public int getLinesNumber() {
        return linesNumber;
    }

    /**
     * Set the number of lines that needs to satisfy the condition in order to complete the goal.
     * @param linesNumber The number of lines that needs to satisfy the condition in order to complete the goal.
     * @see LineCommonGoal#linesNumber
     */
    public void setLinesNumber(int linesNumber) {
        this.linesNumber = linesNumber;
    }

    /**
     * Get The array that contains the number of different {@link TileType} that each lines needs to have.
     * @return The array that contains the number of different {@link TileType} that each lines needs to have.
     * @see LineCommonGoal#differentTiles
     * @see TileType
     */
    public int[] getDifferentTiles() {
        return differentTiles;
    }

    /**
     * Set the array that contains the number of different {@link TileType} that each lines needs to have.
     * @param differentTiles The array that contains the number of different {@link TileType} that each lines needs to have.
     * @apiNote The array differentTiles passed as parameter is sorted automatically and the duplicate element are deleted automatically.
     * @see LineCommonGoal#differentTiles
     * @see TileType
     */
    public void setDifferentTiles(int[] differentTiles) {
        this.differentTiles = removeDuplicateAndSort(differentTiles);
    }

    /**
     * The method returns {@code null} if the {@link LineCommonGoal} is not satisfied for
     * the {@code bookShelf} passes as argument.
     * If the {@link LineCommonGoal} is satisfied then the method returns the list of the
     * {@link EntryPatternGoal EntryPatternGoal} representing
     * the cell in the <code>bookShelf</code> that satisfy the {@link LineCommonGoal LineCommonGoal}.
     *
     * @param bookShelf The bookShelf to check for the goal.
     * @return <code>null</code> if the goal is not satisfied, otherwise the list of the {@link EntryPatternGoal#EntryPatternGoal EntryPatternGoal} representing
     * the cell in the {@code bookShelf} that satisfy the goal.
     *
     * @see EntryPatternGoal
     * @see LineCommonGoal#containsNumber(int count)
     */
    @Override
    public List<EntryPatternGoal> rule(TileType[][] bookShelf) {
        TileType[][] copied_bookshelf;
        int counterLines = 0, counterTile;
        List<TileType> alreadyFoundType = new ArrayList<>();
        List<EntryPatternGoal> result = new ArrayList<EntryPatternGoal>();

        //illegal arguments
        if(bookShelf==null) return null;
        else copied_bookshelf = Arrays.stream(bookShelf).map(TileType[]::clone).toArray(TileType[][]::new); //added to make this thread-safe

        if((incCol!=1 && incCol!=0) || (incRow!=0 && incRow!=1)) return null; //nothing is to be returned if argument incCol and incRow doesn't have value 0 or 1.
        if((incRow==1 && incCol==1)||(incRow==0 && incCol==0)) return null; // nothing is to be returned if arguments incCol and incRow have the same values
        if(linesNumber <= 0) return null; //the argument linesNumber is illegal because it is zero or negative
        if(linesNumber>copied_bookshelf.length && incRow==1) return null; //the argument linesNumber is illegal because it is bigger then the number of rows found in the bookshelf
        if(linesNumber>copied_bookshelf[0].length && incCol==1) return null; //the argument linesNumber is illegal because it is bigger then the number of columns found in the bookshelf
        for(int i = 0; i < differentTiles.length; i++){
            if(differentTiles[i] <= 0) return null; //the argument differentTiles is illegal, because it can only have positive value elements
        }
        if(differentTiles[differentTiles.length-1] > TileType.values().length || differentTiles[differentTiles.length-1] > numberOfTiles) return null; //the argument differentTiles is illegal, this condition needs to be discussed with the other colleagues
        if(numberOfTiles<=0)return null; //the argument numberOfTiles is illegal, because it can only have positive values
        if(numberOfTiles>copied_bookshelf.length && incCol==1) return null;
        if(numberOfTiles>copied_bookshelf[0].length && incRow==1) return null;

        //checking the condition
        for (int i = 0; i < copied_bookshelf.length; i += incCol) {
            alreadyFoundType.clear();
            counterTile = 0;

            for (int j = 0; j < copied_bookshelf[0].length; j += incRow) {
                if (copied_bookshelf[i][j] != null) {
                    counterTile++;
                    result.add(new EntryPatternGoal(i, j, copied_bookshelf[i][j]));
                    if (!alreadyFoundType.contains(copied_bookshelf[i][j]))
                        alreadyFoundType.add(copied_bookshelf[i][j]);
                }

                if (incRow == 0) i++;
                if (incRow == 0 && i == copied_bookshelf.length) {
                    if (counterTile >= numberOfTiles && containsNumber(alreadyFoundType.size())) {
                        counterLines++;
                        if (counterLines == linesNumber) return result;
                    }
                    else {
                        for (int k = 0; k < counterTile; k++)
                            result.remove(result.size() - 1);
                    }
                    i = 0;
                    j++;
                    if (j == copied_bookshelf[0].length) return null;
                    counterTile = 0;
                    alreadyFoundType.clear();
                }
            }

            if (counterTile >= numberOfTiles && incRow == 1 && containsNumber(alreadyFoundType.size())) {
                counterLines++;
                if (counterLines == linesNumber) return result;
            }
            else {
                for (int k = 0; k < counterTile; k++)
                    result.remove(result.size() - 1);
            }

            if (incCol == 0) i++;
        }

        return null;
    }


    /**
     * Method that return {@code true} if and only if the parameter {@code count} is contained in the array {@link LineCommonGoal#differentTiles differentTiles}.
     * @param count Searched value inside {@link LineCommonGoal#differentTiles differentTiles}.
     * @return {@code true} if and only if the parameter {@code count} is contained in the array {@link LineCommonGoal#differentTiles differentTiles}.
     *
     * @apiNote Here is an example: given {@code differentTiles = {1, 2, 3 } } and given {@code count = 2}, the method returns <code>true</code>. Otherwise, if {@code count = 4}
     * then the method returns <code>false</code>.
     * @see LineCommonGoal#differentTiles
     */
    private boolean containsNumber(int count) {
        for (int differentTile : differentTiles) {
            if (differentTile == count)
                return true;
        }
        return false;
    }

    /**
     * Method that sort and delete duplicate elements from the given array of {@code int}.
     * @param array Array that need to be sorted and whose duplicate elements need to be deleted.
     * @return Array of {@code int} that contains all the sorted and distinct elements of the given {@code array}.
     *
     * @apiNote This method is used to sort and delete duplicates form {@link LineCommonGoal#differentTiles}.
     * @see LineCommonGoal#differentTiles
     */
    private int[] removeDuplicateAndSort(int[] array){
        List<Integer> temp = new ArrayList<>();

        for(int i: array){
            temp.add(i);
        }
        return temp.stream()
                .mapToInt(Integer::intValue)
                .distinct()
                .sorted()
                .toArray();
    }

    @Override
    public String toString() {
        return "LineCommonGoal{" +
                System.getProperty("line.separator") +
                super.toString() +
                System.getProperty("line.separator") +
                "\tIncrement Row: " + incRow +
                System.getProperty("line.separator") +
                "\tIncrement Column: " + incCol +
                System.getProperty("line.separator") +
                "\tLines Number: " + linesNumber +
                System.getProperty("line.separator") +
                "\tNumber of Tiles: " + numberOfTiles +
                System.getProperty("line.separator") +
                "\tDifferent Tiles: " + Arrays.toString(differentTiles) +
                System.getProperty("line.separator") +
                '}';
    }
}

