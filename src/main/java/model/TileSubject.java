package model;

public enum TileSubject {
    GRAY_CAT( TileType.CAT ),
    ORANGE_CAT( TileType.CAT ),
    BLACK_CAT( TileType.CAT ),
    MUSIC_TROPHY( TileType.TROPHY ),
    GAME_TROPHY( TileType.TROPHY ),
    OLYMPIC_TROPHY( TileType.TROPHY ),
    LOVE_FRAME( TileType.FRAME ),
    MONUMENT_FRAME( TileType.FRAME );

    private TileType tileType;

    TileSubject(TileType tileType) {
        this.tileType = tileType;
    }

    public TileType getTileType() {
        return tileType;
    }

    public String getTileTypeName(){
        return this.tileType.toString();
    }
}
