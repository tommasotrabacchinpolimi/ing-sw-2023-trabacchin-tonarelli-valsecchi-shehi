package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link StairCommonGoal#StairCommonGoal StairCommonGoal} is a class that represents a generic {@link CommonGoal#CommonGoal CommonGoal} which is satisfied if the BookShelf contains
 * a given number of columns (in ours case 5) of increasing or decreasing height. Starting from the first column on the left or on the right,
 * each next column must be made of exactly one more tile. In the BookShelf, tiles can be of any type.
 *
 *  @author Melanie Tonarelli
 *  @version 1.0, 24/04/23
 *  @see CommonGoal
 */
public class StairCommonGoal extends CommonGoal implements Serializable {
    private static final long serialVersionUID = 142749503L;
    /**
     * Number of columns that forms the staircase
     */
    private int numberOfColumns;

    /**
     * The method used for creating the {@link SquareCommonGoal#SquareCommonGoal SquareCommonGoal}
     * @param numberOfColumns number of columns that forms the staircase
     */
    public StairCommonGoal(int numberOfColumns, String description){
        super(description);
        this.numberOfColumns = numberOfColumns;
    }

    /**
     * Get the number of columns that forms the staircase
     * @return the number of columns that forms the staircase
     */
    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    /**
     * Set the number of columns that forms the staircase
     * @param numberOfColumns the number of columns that forms the staircase
     */
    public void setNumberOfColumns(int numberOfColumns) {
        this.numberOfColumns = numberOfColumns;
    }

    /**
     * Method that return null if and only if the {@link StairCommonGoal#StairCommonGoal StairCommonGoal} is not satisfied for the <code>bookShelf</code> passes as argument.
     * If the {@link StairCommonGoal#StairCommonGoal StairCommonGoal} is satisfied then the method returns the list of the {@link EntryPatternGoal#EntryPatternGoal EntryPatterGoal} representing the
     * tiles in the <code>bookShelf</code> that satisfy the {@link StairCommonGoal#StairCommonGoal StairCommonGoal}. In particular, the method control if there are <code>numberOfColumns</code> columns
     * of increasing or decreasing height; starting from the first column on the left or on the right, each next column must be made of exactly one more tile.
     * @param bookShelf matrix of {@link TileType} referring to the player's <code>bookShelf</code>
     * @return <code>null</code> if the <code>bookShelf</code> doesn't satisfy the condition, otherwise the list of {@link EntryPatternGoal#EntryPatternGoal EntryPatterGoal} that verify the rule of the {@link StairCommonGoal#StairCommonGoal StairCommonGoal}
     */
    @Override
    public List<EntryPatternGoal> rule(TileType[][] bookShelf) {
        int numRows = bookShelf.length, numColumns = bookShelf[0].length;
        List<EntryPatternGoal> result = new ArrayList<>();

        for (int i = numRows-1; i >= 0; i--){
            for (int j = numColumns-1; j >= 0; j--){
                if (wellFormedIndex(i, j, bookShelf)){
                    result = rightStair(i, j, bookShelf);
                    if (result != null) return result;

                    result = leftStair(i, j, bookShelf);
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
     * list of {@link EntryPatternGoal#EntryPatternGoal EntryPatterGoal} that forms the staircase in the <code>bookShelf</code>
     * @param startIndexRow index of the row where the first rung of the stair is positioned
     * @param startIndexColumn index of the column where the first rung of the stair is positioned
     * @param matrix matrix of {@link TileType} referring to the player's <code>bookShelf</code>
     * @return <code>null</code> if  <code>bookShelf</code> doesn't have a right stair, otherwise the list of {@link EntryPatternGoal#EntryPatternGoal EntryPatterGoal} that forms the staircase in the <code>bookshelf</code>
     */
    private List<EntryPatternGoal> rightStair(int startIndexRow, int startIndexColumn, TileType[][] matrix){
        List<EntryPatternGoal> result = new ArrayList<>();
        int numRows = matrix.length, numColumns = matrix[0].length, counter = startIndexRow ;

        if (startIndexColumn + numberOfColumns > numColumns) { //Not enough columns for right stair
            return null;
        }

        for (int i = startIndexColumn; i < startIndexColumn+numberOfColumns && counter >= 0 && counter <= numRows; i++){
            if (controlColumn(i, counter, matrix) == null) return null;
            result.addAll(controlColumn(i, counter, matrix));
            counter--;
        }

        return result;

    }

    /**
     * Method that checks if starting from the cell indicated in the <code>matrix</code> there is a staircase
     * from left to right (the lowest step of the staircase to the left and the highest one to the right).
     * It returns <code>null</code> if there aren't any groups of tiles forming a staircase pattern, otherwise it returns a
     * list of {@link EntryPatternGoal#EntryPatternGoal EntryPatterGoal} that forms the staircase in the <code>bookShelf</code>
     * @param startIndexRow index of the row where the first rung of the stair is positioned
     * @param startIndexColumn index of the column where the first rung of the stair is positioned
     * @param matrix matrix of {@link TileType} referring to the player's <code>bookShelf</code>
     * @return <code>null</code> if  <code>bookShelf</code> doesn't have a left stair, otherwise list of {@link EntryPatternGoal#EntryPatternGoal EntryPatterGoal} that forms the staircase in the <code>bookShelf</code>
     */
    private List<EntryPatternGoal> leftStair(int startIndexRow, int startIndexColumn, TileType[][] matrix){
        List<EntryPatternGoal> result = new ArrayList<>();
        int numRows = matrix.length, numColumns = matrix[0].length, counter = startIndexRow;

        if (startIndexColumn-numberOfColumns+1 < 0) { //Not enough columns for left stair
            return null;
        }

        for (int i = startIndexColumn; i >= startIndexColumn-numberOfColumns+1 && counter >= 0 && counter <= numRows; i--){
            if ( controlColumn(i, counter, matrix) == null ) return null;
            result.addAll(controlColumn(i, counter, matrix));
            counter--;
        }

        return result;
    }

    /**
     * Method that checks if in the indicated column of the matrix, the cell of indexes (<code>indexMinRow</code>,<code>indexColumn</code>)
     * is the first cell having a tile starting from the top downwards.
     * The method returns <code>true</code> if and only if the condition described above is satisfied by the passed parameters.
     * @param indexColumn index of the column which is considered in the <code>matrix</code>
     * @param indexMinRow index of the possible highest row containing a tile
     * @param matrix matrix of {@link TileType} referring to the player's <code>bookShelf</code>
     * @return <code>null</code> if the column doesn't satisfy the condition described, otherwise the list of {@link EntryPatternGoal#EntryPatternGoal EntryPatterGoal} in the given column
     */
    private List<EntryPatternGoal> controlColumn(int indexColumn,int indexMinRow, TileType[][] matrix){
        List<EntryPatternGoal> result = new ArrayList<>();

        if (matrix[indexMinRow][indexColumn]==null) return null;
        if (indexMinRow>0){
            if (matrix[indexMinRow-1][indexColumn]!=null) return null;
        }
        for (int j=matrix.length-1; j >= indexMinRow; j--){
            result.add(new EntryPatternGoal(indexColumn, j, matrix[j][indexColumn]));
        }
        return result;
    }

    /**
     * Method that return <code>true</code> if and only if a <code>matrix</code> of dimensione <code>numRows*numColumns</code> can contain a staircase
     * made of a given number of columns and having the first rung in the cell of indexes (<code>startIndexRow</code>, <code>startIndexColumn</code>)
     * @param startIndexRow index of the row where the first rung of the stair is positioned
     * @param startIndexColumn index of the column where the first rung of the stair is positioned
     * @param matrix matrix of {@link TileType} referring to the player's <code>bookshelf</code>
     * @return <code>true</code> if and only if the matrix has the right dimension to contain a staircase having the first rung in (<code>startIndexRow</code>, <code>startIndexColumn</code>)
     */
    private boolean wellFormedIndex(int startIndexRow, int startIndexColumn, TileType[][] matrix){
        int numRows = matrix.length, numColumns = matrix[0].length;

        //there aren't enough rows above startIndexRow to create a staircase
        if (startIndexRow-numberOfColumns+1 < 0){
            return false;
        }
        //there aren't enough columns from startIndexColumn to create both a left staircase and a right staircase
        if (startIndexColumn+numberOfColumns > numColumns && startIndexColumn-numberOfColumns+1 < 0){
            return false;
        }

        return true;
    }

}
