package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TileSubjectTest {

    @Test
    void getTileType() {
        assertEquals(TileType.CAT, TileSubject.BLACK_CAT.getTileType());
    }

    @Test
    void getTileTypeName() {
        assertEquals(TileType.CAT.toString(), TileSubject.BLACK_CAT.getTileTypeName());
    }

    @Test
    void testToString() {
        assertEquals("GRAY_CAT", TileSubject.GRAY_CAT.toString());
        assertEquals("ORANGE_CAT", TileSubject.ORANGE_CAT.toString());
        assertEquals("BLACK_CAT", TileSubject.BLACK_CAT.toString());
        assertEquals("MUSIC_TROPHY", TileSubject.MUSIC_TROPHY.toString());
    }
}