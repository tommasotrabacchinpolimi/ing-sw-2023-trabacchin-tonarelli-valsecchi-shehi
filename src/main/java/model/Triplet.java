package model;

public class Triplet {
    final int column;
    final int row;
    final TileType tileType;

    public Triplet(){
        column = 0;
        row = 0;
        tileType = null;
    }

    public Triplet(int column, int row, TileType tileType){
        this.column = column;
        this.row = row;
        this.tileType = tileType;
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
}
