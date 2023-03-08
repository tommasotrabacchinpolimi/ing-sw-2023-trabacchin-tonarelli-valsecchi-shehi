package model;

public class BookShelf {
    private static final int R = 6;
    private static final int C = 5;
    private Tile[][] tiletaken;

    public BookShelf() {
        this.tiletaken = new Tile[R][C];
    }

    public void setTiletaken(Tile[][] tiletaken) {
        this.tiletaken = tiletaken;
    }

    public Tile[][] getTiletaken() {
        return tiletaken;
    }

    public boolean isFull(){

        for( int i = 0; i < R; ++i ){
            for( int j = 0; j < C; ++j ){
                if( tiletaken[i][j] == null )
                    return false;
            }
        }

        return true;
    }
}
