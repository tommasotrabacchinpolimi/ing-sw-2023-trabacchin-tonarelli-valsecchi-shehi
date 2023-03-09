package model;

public enum TileSubject {
    GRAY_CAT( TileType.CAT ),
    ORANGE_CAT( TileType.CAT ),
    BLACK_CAT( TileType.CAT ),
    MUSIC_TROPHY( TileType.TROPHY ),
    CHAMPION_TROPHY( TileType.TROPHY ),
    OLYMPIC_TROPHY( TileType.TROPHY ),
    LOVE_FRAME( TileType.FRAME ),
    MONUMENT_FRAME( TileType.FRAME ),
    DEGREE_FRAME( TileType.FRAME ),
    BASIL_PLANT( TileType.PLANT ),
    BONSAI_PLANT( TileType.PLANT ),
    MONSTERA_PLANT( TileType.PLANT ),
    MONOPOLY_GAME( TileType.GAME ),
    RISIKO_GAME( TileType.GAME ),
    DICTIONARY_BOOK( TileType.BOOK ),
    COMIC_BOOK( TileType.BOOK );

    final private TileType tileType;
    final private int occurrency;

    TileSubject(TileType tileType, int occurrency) {
        this.tileType = tileType;
        this.occurrency = occurrency;
    }

    TileSubject(TileType tileType ) {
        this.tileType = tileType;
        this.occurrency = 3;
    }

    public TileType getTileType() {
        return tileType;
    }

    public int getOccurrency() {
        return occurrency;
    }

    public String getTileTypeName(){
        return this.tileType.toString();
    }
}
