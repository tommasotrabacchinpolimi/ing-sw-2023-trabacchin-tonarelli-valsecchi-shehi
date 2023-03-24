package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * StairCommonGoal is a class that represents a generic CommonGoal which is satisfied if the BookShelf contains
 * a given number of columns (in ours case 5) of increasing or decreasing height. Starting from the first column on the left or on the right,
 * each next column must be made of exactly one more tile. In the BookShelf, tiles can be of any type.
 */
public class StairCommonGoal extends CommonGoal implements Serializable {
    private static final long serialVersionUID = 142749503L;
    /**
     * Number of columns that forms the staircase
     */
    private int numberOfColumns;

    /**
     * The method used for creating the SquareCommonGoal
     * @param numberOfColumns number of columns that forms the staircase
     */
    public StairCommonGoal(int numberOfColumns, String description){
        super(description);
        this.setAvailableScore(8);
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
     * Method that return null if and only if the StairCommonGoal is not satisfied for the BookShelf passes as argument.
     * If the SquareCommonGoal is satisfied then the method returns the list of the EntryPatternGoals representing the
     * tiles in the BookShelf that satisfy the SquareCommonGoal. In particular, the method control if there are five columns
     * of increasing or decreasing height; starting from the first column on the left or on the right, each next column must
     * be made of exactly one more tile.
     * @param bookShelf matrix of TileType referring to the player's bookshelf
     * @return list of EntryPatternGoal that verify the rule of the StairCommonGoal
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
     * Method that checks if starting from the cell indicated in the matrix there is a staircase
     * from right to left corner (the lowest step of the staircase to the right and the highest one to the left).
     * It returns null if there aren't any groups of tiles forming a staircase pattern, otherwise it return a
     * list of EntryPatterGoal that forms the staircase in the bookshelf
     * @param startIndexRow index of the row where the first rung of the stair is positioned
     * @param startIndexColumn index of the column where the first rung of the stair is positioned
     * @param matrix matrix of TileType referring to the player's bookshelf
     * @return list of EntryPatternGoal that forms the staircase in the bookshelf
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
     * Method that checks if starting from the cell indicated in the matrix there is a staircase
     * from left to right (the lowest step of the staircase to the left and the highest one to the right).
     * It returns null if there aren't any groups of tiles forming a staircase pattern, otherwise it return a
     * list of EntryPatterGoal that forms the staircase in the bookshelf
     * @param startIndexRow index of the row where the first rung of the stair is positioned
     * @param startIndexColumn index of the column where the first rung of the stair is positioned
     * @param matrix matrix of TileType referring to the player's bookshelf
     * @return list of EntryPatternGoal that forms the staircase in the bookshelf
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
     * Method that checks if in the indicated column of the matrix, the cell of indices (indexMinRow,indexColumn)
     * is the first cell having a tile starting from the top downwards.
     * The method returns true if and only if the condition described above is satisfied by the passed parameters.
     * @param indexColumn index of the column which is considered in the matrix
     * @param indexMinRow index of the possible highest row containing a tile
     * @param matrix matrix of TileType referring to the player's bookshelf
     * @return null if the column satisfied the condition described, otherwise the list of EntryPatterGoal
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
     * Method that return true if and only if a matrix of dimensione numRows*numColumns can contain a staircase
     * made of a given number of columns and having the first rung in the cell of indices (startIndexRow,startIndexColumn)
     * @param startIndexRow index of the row where the first rung of the stair is positioned
     * @param startIndexColumn index of the column where the first rung of the stair is positioned
     * @param matrix matrix of TileType referring to the player's bookshelf
     * @return true if and only if the matrix has the right dimension to contain a staircase starting from
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
