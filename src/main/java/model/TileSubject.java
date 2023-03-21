package model;

import java.io.Serializable;

public enum TileSubject implements Serializable {
    GRAY_CAT( TileType.CAT, TileSubject.NUMBER_OF_TILE ), //Gatti1.3
    ORANGE_CAT( TileType.CAT, TileSubject.NUMBER_OF_TILE ), //Gatti1.1
    BLACK_CAT( TileType.CAT, TileSubject.RESERVE_TILE ), //Gatti1.2
    MUSIC_TROPHY( TileType.TROPHY, TileSubject.RESERVE_TILE ), //Trofei1.2
    CHAMPION_TROPHY( TileType.TROPHY, TileSubject.NUMBER_OF_TILE ), //Trofei1.3
    GYM_TROPHY( TileType.TROPHY, TileSubject.NUMBER_OF_TILE ), //Trofei1.1
    LOVE_FRAME( TileType.FRAME, TileSubject.NUMBER_OF_TILE ), //Cornici1.3
    MEMORIES_FRAME( TileType.FRAME, TileSubject.NUMBER_OF_TILE ), //Cornici1.1
    DEGREE_FRAME( TileType.FRAME, TileSubject.RESERVE_TILE ), //Cornici1.2
    BASIL_PLANT( TileType.PLANT, TileSubject.NUMBER_OF_TILE ), //Piante1.3
    GREEN_PLANT( TileType.PLANT, TileSubject.RESERVE_TILE ), //Piante1.2
    MONSTERA_PLANT( TileType.PLANT, TileSubject.NUMBER_OF_TILE ), //Piante1.1
    MONOPOLY_GAME( TileType.GAME, TileSubject.NUMBER_OF_TILE ), //Giochi1.2
    RISIKO_GAME( TileType.GAME, TileSubject.NUMBER_OF_TILE ), //Giochi1.1
    CHESS_GAME( TileType.GAME, TileSubject.RESERVE_TILE ), //Giochi1.3
    DICTIONARY_BOOK( TileType.BOOK, TileSubject.NUMBER_OF_TILE ), //Libri1.3
    COMIC_BOOK( TileType.BOOK, TileSubject.RESERVE_TILE ), //Libri1.1
    NOTE_BOOK(TileType.BOOK, TileSubject.NUMBER_OF_TILE ); //Libri1.2

    private static final long serialVersionUID = 5341537492L;
    private static final int NUMBER_OF_TILE = 7; //the default number of tile
    private static final int RESERVE_TILE = 8; //the tile that has one object more than others
    final private TileType tileType;
    final private int occurrence;

    TileSubject( TileType tileType, int occurrence ) {
        this.tileType = tileType;
        this.occurrence = occurrence;
    }

    TileSubject( TileType tileType ) {
        this.tileType = tileType;
        this.occurrence = TileSubject.NUMBER_OF_TILE;
    }

    public TileType getTileType() {
        return tileType;
    }

    public int getOccurrence() {
        return occurrence;
    }

    public String getTileTypeName(){
        return this.tileType.toString();
    }

    @Override
    public String toString() {
        return this.name();
    }
}
