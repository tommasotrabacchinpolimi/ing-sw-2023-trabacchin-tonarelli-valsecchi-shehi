package model;

import java.io.Serializable;

public enum TileSubject implements Serializable {
    GRAY_CAT( TileType.CAT ), //Gatti1.3
    ORANGE_CAT( TileType.CAT), //Gatti1.1
    BLACK_CAT( TileType.CAT ), //Gatti1.2
    MUSIC_TROPHY( TileType.TROPHY), //Trofei1.2
    CHAMPION_TROPHY( TileType.TROPHY), //Trofei1.3
    GYM_TROPHY( TileType.TROPHY), //Trofei1.1
    LOVE_FRAME( TileType.FRAME), //Cornici1.3
    MEMORIES_FRAME( TileType.FRAME), //Cornici1.1
    DEGREE_FRAME( TileType.FRAME ), //Cornici1.2
    BASIL_PLANT( TileType.PLANT ), //Piante1.3
    GREEN_PLANT( TileType.PLANT), //Piante1.2
    MONSTERA_PLANT( TileType.PLANT), //Piante1.1
    MONOPOLY_GAME( TileType.GAME), //Giochi1.2
    RISIKO_GAME( TileType.GAME ), //Giochi1.1
    CHESS_GAME( TileType.GAME), //Giochi1.3
    DICTIONARY_BOOK( TileType.BOOK), //Libri1.3
    COMIC_BOOK( TileType.BOOK ), //Libri1.1
    NOTE_BOOK(TileType.BOOK); //Libri1.2

    private static final long serialVersionUID = 5341537492L;
    final private TileType tileType;

    TileSubject( TileType tileType ) {
        this.tileType = tileType;
    }

    public TileType getTileType() {
        return tileType;
    }

    public String getTileTypeName(){
        return this.tileType.toString();
    }

    @Override
    public String toString() {
        return this.name();
    }
}
