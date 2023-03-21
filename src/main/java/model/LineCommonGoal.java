package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LineCommonGoal extends CommonGoal implements Serializable {
    private static final long serialVersionUID = 83625649L;
    private int incRow;
    private int incCol;
    private int linesNumber;
    private int[] differentTiles;

    public LineCommonGoal(int incRow, int incCol, int linesNumber, int[] differentTiles) {
        this.incRow = incRow;
        this.incCol = incCol;
        this.linesNumber = linesNumber;
        this.differentTiles = differentTiles;
    }

    public int getIncRow() {
        return incRow;
    }

    public void setIncRow(int incRow) {
        this.incRow = incRow;
    }

    public int getIncCol() {
        return incCol;
    }

    public void setIncCol(int incCol) {
        this.incCol = incCol;
    }

    public int getLinesNumber() {
        return linesNumber;
    }

    public void setLinesNumber(int linesNumber) {
        this.linesNumber = linesNumber;
    }

    public int[] getDifferentTiles() {
        return differentTiles;
    }

    public void setDifferentTiles(int[] differentTiles) {
        this.differentTiles = differentTiles;
    }

    @Override
    public List<EntryPatternGoal> rule(TileType[][] bookShelf) {
        if (incRow == 1)
            return verifyRows(bookShelf);
        else return verifyColumns(bookShelf);
    }

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

            if (counterTile == matrix[0].length && containsNumber(alreadyFoundType.size())) {
                counterRows++;
                for(int j = 0; j < matrix[i].length; j++){
                    element = new EntryPatternGoal(j, i, matrix[i][j]);
                    result.add(element);
                }
                if(counterRows == linesNumber) return result;
            }
            else {
                result.clear();
            }
        }

        return null;
    }


    private List<EntryPatternGoal> verifyColumns(TileType[][] matrix){
        int counterColumns = 0, counterTile;
        List<TileType> alreadyFoundType = new ArrayList<>();
        List<EntryPatternGoal> result = new ArrayList<EntryPatternGoal>();
        EntryPatternGoal element = null;

        for (int i = 0; i <= matrix[0].length-1 ; i++) {
            alreadyFoundType.clear();
            counterTile = 0;
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[j][i] != null) {
                    counterTile++;
                    if (!alreadyFoundType.contains(matrix[j][i]))
                        alreadyFoundType.add(matrix[j][i]);
                }
            }

            if (counterTile == matrix.length && containsNumber(alreadyFoundType.size())) {
                counterColumns++;
                for(int j = 0; j < matrix.length; j++){
                    element = new EntryPatternGoal(i, j, matrix[j][i]);
                    result.add(element);
                }
                if(counterColumns == linesNumber) return result;
            }
            else {
                result.clear();
            }
        }

        return null;
    }

    private boolean containsNumber(int count){
        for (int i = 0; i < differentTiles.length; i++){
            if (differentTiles[i] == count)
                return true;
        }
        return false;
    }
}
