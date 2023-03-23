package model;

import java.io.Serializable;

public class EntryPatternGoal implements Serializable {
    final int column;
    final int row;
    final TileType tileType;

    public EntryPatternGoal(){
        column = 0;
        row = 0;
        tileType = null;
    }

    public EntryPatternGoal(int column, int row, TileType tileType) {
        this.column = column;
        this.row = row;
        this.tileType = tileType;
    }

    public EntryPatternGoal(int column, int row, String tileTypeName) {
        this.column = column;
        this.row = row;
        this.tileType = fromStringToTileType(tileTypeName);
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public TileType getTileType() {
        return tileType;
    }

    public int[] getArrayIndexes() {
        return new int[]{row, column};
    }

    @Override
    public String toString() {
        return "EntryPatternGoal{" +
                "column=" + column + "," +
                "row=" + row + "," +
                "tileType=" + tileType +
                '}';
    }

    private TileType fromStringToTileType(String tileTypeName){
        try {
            return TileType.valueOf(tileTypeName);
        } catch(IllegalArgumentException | NullPointerException e) {
            return null;
        }
    }
}
