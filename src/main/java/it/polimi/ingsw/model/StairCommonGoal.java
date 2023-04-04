package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.JSONExclusionStrategy.ExcludedFromJSON;

import java.io.Serializable;
import java.util.*;

/**
 * StairCommonGoal is a class that represents a generic {@link CommonGoal CommonGoal} which is satisfied if the
 * {@link BookShelf BookShelf} contains a given number of columns of increasing or decreasing height.
 * Starting from the first column on the left or on the right, each next column must be made of exactly one more tile.
 * In the {@link BookShelf}, {@link TileSubject tile} can be of any {@link TileType type}.
 *
 *  @author Melanie Tonarelli
 *  @version 1.0, 24/03/23
 *  @see CommonGoal
 *  @see BookShelf
 *  @see EntryPatternGoal
 */
public class StairCommonGoal extends CommonGoal implements Serializable {
    private static final long serialVersionUID = 142749503L;
    /**
     * Number of columns that forms the staircase
     * @see StairCommonGoal#getNumberOfColumns()  getNumberOfColumns()
     * @see StairCommonGoal#setNumberOfColumns(int) setNumberOfColumns(int)
     */
    @ExcludedFromJSON
    private int numberOfColumns;

    public StairCommonGoal(int numberOfColumns) {
        super();
        this.numberOfColumns = numberOfColumns;
    }

    /**
     * Constructor that sets the fields of the class to the parameter passed
     * @param numberOfColumns Number of columns that forms the staircase
     * @param description Textual description of the goal.
     *
     * @see StairCommonGoal#numberOfColumns
     * @see CommonGoal#CommonGoal(String) CommonGoal(String description)
     */
    public StairCommonGoal(String description, int numberOfColumns){
        super(description);
        this.numberOfColumns = numberOfColumns;
    }

    public StairCommonGoal(Stack<Integer> scoringTokens, String description, int numberOfColumns) {
        super(scoringTokens, description);
        this.numberOfColumns = numberOfColumns;
    }

    /**
     * Return the number of columns that forms the staircase
     * @return Number of columns that forms the staircase
     * @see StairCommonGoal#numberOfColumns
     */
    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    /**
     * Set the number of columns that forms the staircase
     * @param numberOfColumns Number of columns that forms the staircase
     * @see StairCommonGoal#numberOfColumns
     */
    public void setNumberOfColumns(int numberOfColumns) {
        this.numberOfColumns = numberOfColumns;
    }

    /**
     * Method that return <code>null</code> if and only if the {@link StairCommonGoal StairCommonGoal} is not satisfied for the <code>bookShelf</code> passes as argument.
     * If the goal is satisfied then the method returns the list of {@link EntryPatternGoal#EntryPatternGoal EntryPatterGoal} representing the
     * cell in the <code>bookShelf</code> forming the staircase made up of {@link StairCommonGoal#numberOfColumns} columns.
     * @param bookShelf Matrix of {@link TileType} referring to the player's <code>bookShelf</code>
     * @return <code>null</code> if the <code>bookShelf</code> doesn't satisfy the condition, otherwise the list of {@link EntryPatternGoal#EntryPatternGoal EntryPatterGoal} that verify the rule of the goal.
     *
     * @apiNote In particular, the method control if there are <code>numberOfColumns</code> columns
     *   of increasing or decreasing height; starting from the first column on the left or on the right, each next column must be made of exactly one more tile.
     * @see TileType
     * @see EntryPatternGoal
     */
    @Override
    public List<EntryPatternGoal> rule(TileType[][] bookShelf) {
        int numRows, numColumns;
        TileType[][] copied_bookshelf;
        List<EntryPatternGoal> result = new ArrayList<>();

        if (bookShelf == null) return null;
        else {
            copied_bookshelf =  Arrays.stream(bookShelf).map(TileType[]::clone).toArray(TileType[][]::new); //added to make this thread safe
            numRows = copied_bookshelf.length;
            numColumns = copied_bookshelf[0].length;
        }
        if(numberOfColumns <= 0) return null; // nothing is to be returned if arguments are illegal
        if (numColumns < numberOfColumns || numRows < numberOfColumns){ // nothing is to be returned if arguments are illegal
            return null;
        }

        for (int i = numRows-1; i >= 0; i--){
            for (int j = numColumns-1; j >= 0; j--){
                if (wellFormedIndex(i, j, copied_bookshelf)){
                    result = rightStair(i, j, copied_bookshelf);
                    if (result != null) return result;

                    result = leftStair(i, j, copied_bookshelf);
                    if (result != null) return result;
                }
            }
        }

        return null;
    }

    /**
     * Method that checks if starting from the cell indicated in the <code>matrix</code> there is a staircase
     * from right to left corner (the lowest step of the staircase to the right and the highest one to the left).
     * It returns <code>null</code> if there aren't any groups of tiles forming a staircase pattern, otherwise it returns a
     * list of {@link EntryPatternGoal#EntryPatternGoal EntryPatterGoal} that forms the staircase in the <code>bookShelf</code>.
     * @param startIndexRow Index of the row where the first rung of the stair is positioned
     * @param startIndexColumn Index of the column where the first rung of the stair is positioned
     * @param matrix matrix of {@link TileType} referring to the player's <code>bookShelf</code>
     * @return <code>null</code> if  <code>bookShelf</code> doesn't have a right stair, otherwise the list of {@link EntryPatternGoal#EntryPatternGoal EntryPatterGoal} that forms the staircase in the <code>bookshelf</code>
     *
     * @see EntryPatternGoal
     * @see TileType
     * @see StairCommonGoal#rule(TileType[][])
     */
    private List<EntryPatternGoal> rightStair(int startIndexRow, int startIndexColumn, TileType[][] matrix){
        List<EntryPatternGoal> result = new ArrayList<>();
        int numRows = matrix.length, numColumns = matrix[0].length, counter = startIndexRow ;

        if (startIndexColumn + numberOfColumns > numColumns) { //Not enough columns for right stair
            return null;
        }

        for (int i = startIndexColumn; i < startIndexColumn+numberOfColumns && counter >= 0 && counter <= numRows; i++){
            if (controlColumn(i, counter, matrix) == null) return null;
            result.addAll(Objects.requireNonNull(controlColumn(i, counter, matrix)));
            counter--;
        }

        return result;

    }

    /**
     * Method that checks if starting from the cell indicated in the <code>matrix</code> there is a staircase
     * from left to right (the lowest step of the staircase to the left and the highest one to the right).
     * It returns <code>null</code> if there aren't any groups of tiles forming a staircase pattern, otherwise it returns a
     * list of {@link EntryPatternGoal#EntryPatternGoal EntryPatterGoal} that forms the staircase in the <code>bookShelf</code>
     * @param startIndexRow Index of the row where the first rung of the stair is positioned
     * @param startIndexColumn Index of the column where the first rung of the stair is positioned
     * @param matrix Matrix of {@link TileType} referring to the player's <code>bookShelf</code>
     * @return <code>null</code> if  <code>bookShelf</code> doesn't have a left stair, otherwise list of {@link EntryPatternGoal#EntryPatternGoal EntryPatterGoal} that forms the staircase in the <code>bookShelf</code>
     * @see EntryPatternGoal
     * @see TileType
     * @see StairCommonGoal#rule(TileType[][])
     */
    private List<EntryPatternGoal> leftStair(int startIndexRow, int startIndexColumn, TileType[][] matrix){
        List<EntryPatternGoal> result = new ArrayList<>();
        int numRows = matrix.length, numColumns = matrix[0].length, counter = startIndexRow;

        if (startIndexColumn-numberOfColumns+1 < 0) { //Not enough columns for left stair
            return null;
        }

        for (int i = startIndexColumn; i >= startIndexColumn-numberOfColumns+1 && counter >= 0 && counter <= numRows; i--){
            if ( controlColumn(i, counter, matrix) == null ) return null;
            result.addAll(Objects.requireNonNull(controlColumn(i, counter, matrix)));
            counter--;

        }

        return result;
    }

    /**
     * Method that checks if in the indicated column of the matrix, the cell of indexes (<code>indexMinRow</code>, <code>indexColumn</code>)
     * is the first cell having a {@link TileType tile} starting from the top downwards.
     * The method returns the list of {@link EntryPatternGoal} that satisfy the condition described above is satisfied by the passed parameters.
     * @param indexColumn Index of the column which is considered in the <code>matrix</code>
     * @param indexMinRow Index of the possible highest row containing a tile
     * @param matrix matrix of {@link TileType} referring to the player's <code>bookShelf</code>
     * @return <code>null</code> if the column doesn't satisfy the condition described, otherwise the list of {@link EntryPatternGoal#EntryPatternGoal EntryPatterGoal} in the given column
     *
     * @apiNote The first cell in a given column of a matrix having a tile is the full cell having the lowest index of row
     * @see EntryPatternGoal
     * @see TileType
     */
    private List<EntryPatternGoal> controlColumn(int indexColumn,int indexMinRow, TileType[][] matrix){
        List<EntryPatternGoal> result = new ArrayList<>();

        if (matrix[indexMinRow][indexColumn]==null) return null;
        if (indexMinRow>0){
            if (matrix[indexMinRow-1][indexColumn]!=null) return null;
        }
        for (int j=matrix.length-1; j >= indexMinRow; j--){
            result.add(new EntryPatternGoal(j, indexColumn, matrix[j][indexColumn]));
        }
        return result;
    }

    /**
     * Method that return <code>true</code> if and only if a <code>matrix</code> of dimensione <code>numRows*numColumns</code> can contain a staircase
     * made of a given number of columns and having the first rung in the cell of indexes (<code>startIndexRow</code>, <code>startIndexColumn</code>)
     * @param startIndexRow index of the row where the first rung of the stair is positioned
     * @param startIndexColumn index of the column where the first rung of the stair is positioned
     * @param matrix matrix of {@link TileType} referring to the player's <code>bookshelf</code>
     * @return <code>true</code> if and only if the matrix has the right dimension to contain a staircase having the first rung in (<code>startIndexRow</code>, <code>startIndexColumn</code>), <code>false</code> otherwise
     *
     * @see TileType
     */
    private boolean wellFormedIndex(int startIndexRow, int startIndexColumn, TileType[][] matrix){
        int numRows, numColumns;

        if(matrix == null) return false;

        numRows = matrix.length;
        numColumns = matrix[0].length;
        if(startIndexColumn < 0 || startIndexRow < 0) return false;

        //there aren't enough rows above startIndexRow to create a staircase
        if (startIndexRow-numberOfColumns+1 < 0){
            return false;
        }
        //there aren't enough columns from startIndexColumn to create both a left staircase and a right staircase
        return startIndexColumn + numberOfColumns <= numColumns || startIndexColumn - numberOfColumns + 1 >= 0;
    }

}
