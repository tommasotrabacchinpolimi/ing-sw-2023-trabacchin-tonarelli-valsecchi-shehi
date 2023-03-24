package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *  LineCommonGoal is a class that represents a generic CommonGoal which is satisfied if the BookShelf contains a given number
 *  of lines (rows or columns) each having a given number of different tile type.
 */
public class LineCommonGoal extends CommonGoal implements Serializable {
    private static final long serialVersionUID = 83625649L;
    /**
     * Increment of row, that is set to 1 iff the lines that have to satisfy the condition are rows of a matrix
     */
    private int incRow;
    /**
     * Increment of column, that is set to 1 iff the lines that have to satisfy the condition are columns of a matrix
     */
    private int incCol;
    /**
     * The number of lines that needs to satisfy the condition in order to complete the Goal
     */
    private int linesNumber;

    /**
     * The minimum number of tiles that must be found in the lines to satisfy the goal
     */
    private int numberOfTiles;
    /**
     * The array that contains the number of different TileType that each lines needs to have in order to satisfy the Goal
     */
    private int[] differentTiles;

    /**
     * The method used for creating the SquareCommonGoal.
     * @param incRow         Increment of row, that is set to 1 iff the lines that have to satisfy the condition are rows of a matrix
     * @param incCol         Increment of column, that is set to 1 iff the lines that have to satisfy the condition are columns of a matrix
     * @param linesNumber    The number of lines that needs to satisfy the condition in order to complete the Goal
     * @param numberOfTiles  The minimum number of tiles that must be found in the lines to satisfy the goal
     * @param differentTiles The array that contains the number of different TileType that each lines needs to have in order to satisfy the Goal
     */
    public LineCommonGoal(int incRow, int incCol, int linesNumber, int numberOfTiles, int[] differentTiles) {
        this.incRow = incRow;
        this.incCol = incCol;
        this.linesNumber = linesNumber;
        this.numberOfTiles = numberOfTiles;
        this.differentTiles = differentTiles;
    }

    /**
     * Get the minimum number of tiles that must be found in the lines to satisfy the goal
     * @return the minimum number of tiles that must be found in the lines to satisfy the goal
     */
    public int getNumberOfTiles() {
        return numberOfTiles;
    }

    /**
     * Set the minimum number of tiles that must be found in the lines to satisfy the goal
     * @param numberOfTiles the minimum number of tiles that must be found in the lines to satisfy the goal
     */
    public void setNumberOfTiles(int numberOfTiles) {
        this.numberOfTiles = numberOfTiles;
    }


    /**
     * Get the increment of row, that is set to 1 iff the lines that have to satisfy the condition are rows of a matrix
     * @return incRow value
     */
    public int getIncRow() {
        return incRow;
    }


    /**
     * Sets the increment of row, that is set to 1 iff the lines that have to satisfy the condition are rows of a matrix
     * @param incRow The value of incRow
     */
    public void setIncRow(int incRow) {
        this.incRow = incRow;
    }


    /**
     * Get the increment of row, that is set to 1 iff the lines that have to satisfy the condition are columns of a matrix
     * @return incCol value
     */
    public int getIncCol() {
        return incCol;
    }


    /**
     * Sets the increment of row, that is set to 1 iff the lines that have to satisfy the condition are columns of a matrix
     * @param incCol The value of incCol
     */
    public void setIncCol(int incCol) {
        this.incCol = incCol;
    }


    /**
     * Get the number of lines that needs to satisfy the condition in order to complete the Goal
     * @return the number of lines that needs to satisfy the condition in order to complete the Goal
     */
    public int getLinesNumber() {
        return linesNumber;
    }


    /**
     * Set the number of lines that needs to satisfy the condition in order to complete the Goal
     * @param linesNumber The number of lines that needs to satisfy the condition in order to complete the Goal
     */
    public void setLinesNumber(int linesNumber) {
        this.linesNumber = linesNumber;
    }


    /**
     * Get The array that contains the number of different TileType that each lines needs to have in order to satisfy the Goal
     * @return The array that contains the number of different TileType that each lines needs to have in order to satisfy the Goal
     */
    public int[] getDifferentTiles() {
        return differentTiles;
    }


    /**
     * Set the array that contains the number of different TileType that each lines needs to have in order to satisfy the Goal
     * @param differentTiles The array that contains the number of different TileType that each lines needs to have in order to satisfy the Goal
     */
    public void setDifferentTiles(int[] differentTiles) {
        this.differentTiles = differentTiles;
    }


    /**
     * The method returns null if the LineCommonGoal is not satisfied for the bookShelf passes as argument.
     * If the LineCommonGoal is satisfied then the method returns the list of the EntryPatternGoals representing
     * the tiles in the BookShelf that satisfy the LineCommonGoal.
     * @param bookShelf the BookShelf to check for the LineCommonGoal
     * @return null if the LineCommonGoal is satisfied, otherwise the list of the EntryPatternGoals representing
     * the tiles in the BookShelf that satisfy the LineCommonGoal
     */
    @Override
    public List<EntryPatternGoal> rule(TileType[][] bookShelf) {
        int counterLines = 0, counterTile;
        List<TileType> alreadyFoundType = new ArrayList<>();
        List<EntryPatternGoal> result = new ArrayList<EntryPatternGoal>();
        EntryPatternGoal element = null;

        if ((incRow==1 && incCol==1)||(incRow==0 && incCol==0)) return null;

        for (int i = 0; i < bookShelf.length; i += incCol) {
            alreadyFoundType.clear();
            counterTile = 0;

            for (int j = 0; j < bookShelf[0].length; j += incRow) {
                if (bookShelf[i][j] != null) {
                    counterTile++;
                    element = new EntryPatternGoal(j, i, bookShelf[i][j]);
                    result.add(element);
                    if (!alreadyFoundType.contains(bookShelf[i][j]))
                        alreadyFoundType.add(bookShelf[i][j]);
                }

                if (incRow == 0) i++;
                if (incRow == 0 && i == bookShelf.length) {
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
                    if (j == bookShelf[0].length) return null;
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
     * Method that return true iff the parameter count is contained in the array differentTiles
     * @param count
     * @return true iff the parameter count is contained in the array differentTiles
     */
    private boolean containsNumber(int count) {
        for (int i = 0; i < differentTiles.length; i++) {
            if (differentTiles[i] == count)
                return true;
        }
        return false;
    }

    /**
     * The method returns null if the LineCommonGoal is not satisfied for the matrix passes as argument,
     * in the specif case of incRow=1 (which means that the condition needs to be satisfied by some rows).
     * If the LineCommonGoal is satisfied then the method returns the list of the EntryPatternGoals representing
     * the tiles of the rows in the matrix that satisfy the LineCommonGoal.
     * @param matrix the matrix to check for the LineCommonGoal
     * @return null if the LineCommonGoal is satisfied, otherwise the list of the EntryPatternGoals representing
     * the tiles of the rows in the matrix that satisfy the LineCommonGoal
     */
    @Deprecated
    private List<EntryPatternGoal> verifyRows(TileType[][] matrix) {
        int counterRows = 0, counterTile;
        List<TileType> alreadyFoundType = new ArrayList<>();
        List<EntryPatternGoal> result = new ArrayList<EntryPatternGoal>();
        EntryPatternGoal element = null;

        for (int i = matrix.length - 1; i >= 0; i--) {
            alreadyFoundType.clear();
            counterTile = 0;
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] != null) {
                    counterTile++;
                    if (!alreadyFoundType.contains(matrix[i][j]))
                        alreadyFoundType.add(matrix[i][j]);
                }
            }

            if (counterTile >= alreadyFoundType.size() && containsNumber(alreadyFoundType.size())) {
                counterRows++;
                for (int j = 0; j < matrix[i].length; j++) {
                    element = new EntryPatternGoal(j, i, matrix[i][j]);
                    result.add(element);
                }
                if (counterRows == linesNumber) return result;
            } else {
                result.clear();
            }
        }

        return null;
    }

    /**
     * The method returns null if the LineCommonGoal is not satisfied for the matrix passes as argument,
     * in the specif case of incCol=1 (which means that the condition needs to be satisfied by some columns).
     * If the LineCommonGoal is satisfied then the method returns the list of the EntryPatternGoals representing
     * the tiles of the columns in the matrix that satisfy the LineCommonGoal.
     * @param matrix the matrix to check for the LineCommonGoal.
     * @return null if the LineCommonGoal is satisfied, otherwise the list of the EntryPatternGoals representing
     * the tiles of the columns in the matrix that satisfy the LineCommonGoal.
     */
    @Deprecated
    private List<EntryPatternGoal> verifyColumns(TileType[][] matrix) {
        int counterColumns = 0, counterTile;
        List<TileType> alreadyFoundType = new ArrayList<>();
        List<EntryPatternGoal> result = new ArrayList<EntryPatternGoal>();
        EntryPatternGoal element = null;

        for (int i = 0; i <= matrix[0].length - 1; i++) {
            alreadyFoundType.clear();
            counterTile = 0;
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[j][i] != null) {
                    counterTile++;
                    if (!alreadyFoundType.contains(matrix[j][i]))
                        alreadyFoundType.add(matrix[j][i]);
                }
            }

            if (counterTile >= alreadyFoundType.size() && containsNumber(alreadyFoundType.size())) {
                counterColumns++;
                for (int j = 0; j < matrix.length; j++) {
                    element = new EntryPatternGoal(i, j, matrix[j][i]);
                    result.add(element);
                }
                if (counterColumns == linesNumber) return result;
            } else {
                result.clear();
            }
        }

        return null;
    }


}
