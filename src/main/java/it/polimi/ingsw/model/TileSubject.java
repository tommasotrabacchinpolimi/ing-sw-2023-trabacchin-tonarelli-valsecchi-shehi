package it.polimi.ingsw.model;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * @author Tommaso Trabacchin
 * @author Melanie Tonarelli
 * @author Emanuele Valsecchi
 * @author Adem Shehi
 * @version 1.0
 * @since 08/03/2023
 */

public enum TileSubject implements Serializable {
    CAT_GRAY( TileType.CAT ),
    CAT_ORANGE( TileType.CAT),
    CAT_BLACK( TileType.CAT ),
    TROPHY_MUSIC( TileType.TROPHY),
    TROPHY_CHAMPION( TileType.TROPHY),
    TROPHY_GYM( TileType.TROPHY),
    FRAME_LOVE( TileType.FRAME),
    FRAME_MEMORIES( TileType.FRAME),
    FRAME_DEGREE( TileType.FRAME ),
    PLANT_BASIL( TileType.PLANT ),
    PLANT_GREEN( TileType.PLANT),
    PLANT_MONSTERA( TileType.PLANT),
    GAME_MONOPOLY( TileType.GAME),
    GAME_RISIKO( TileType.GAME ),
    GAME_CHESS( TileType.GAME),
    BOOK_DICTIONARY( TileType.BOOK),
    BOOK_COMIC( TileType.BOOK ),
    BOOK_NOTE(TileType.BOOK);

    private static final long serialVersionUID = 5341537492L;
    final private TileType tileType;

    TileSubject( TileType tileType ) {
        this.tileType = tileType;
    }

    public TileType getTileType() {
        return this.tileType;
    }

    @NotNull
    @Contract(pure = true)
    public String getTileTypeName(){
        return this.tileType.toString();
    }

    @Override
    public String toString() {
        return this.name();
    }
}
