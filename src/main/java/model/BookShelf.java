package model;

public class BookShelf {
    private static final int R = 6;
    private static final int C = 5;
    private TileSubject[][] tileSubjectTaken;

    public BookShelf() {
        this.tileSubjectTaken = new TileSubject[R][C];
    }

    public void setTileSubjectTaken(TileSubject[][] tileSubjectTaken) {
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
}
