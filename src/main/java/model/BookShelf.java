package model;

import java.io.Serializable;

public class BookShelf implements Serializable {
    private static final long serialVersionUID = 9828497462L;
    public static final int R = 6;
    public static final int C = 5;
    private TileSubject[][] tileSubjectTaken;

    public BookShelf() {
        this.tileSubjectTaken = new TileSubject[R][C];
    }

    public void setTileSubjectTaken( TileSubject[][] tileSubjectTaken ) {
        this.tileSubjectTaken = tileSubjectTaken;
    }

    public TileSubject[][] getTileSubjectTaken() {
        return tileSubjectTaken;
    }

    public boolean isFull(){
        for( int i = 0; i < R; ++i ){
            for( int j = 0; j < C; ++j ){
                if( tileSubjectTaken[i][j] == null )
                    return false;
            }
        }
        return true;
    }

    private TileType getTypeFromSubject(int r, int c){
        if(this.getTileSubjectTaken()[r][c] == null) return null;
        return this.getTileSubjectTaken()[r][c].getTileType();
    }

    public TileType[][] convertToTileTypeMatrix(){
        TileType[][] matrix = new TileType[R][C];

        for (int i = 0; i < R; i++ ){
            for (int j = 0; j < C; j++) {
                matrix[i][j] = this.getTypeFromSubject(i, j);
            }
        }
        return matrix;
    }
}
