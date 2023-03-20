package model;

import java.io.Serializable;
import java.util.List;

public class LineCommonGoal extends CommonGoal implements Serializable {
    private static final long serialVersionUID = 83625649L;
    private int incRow;
    private int incCol;
    private int linesNumber;
    private int[] differentTiles;

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
        return null;
    }
}
