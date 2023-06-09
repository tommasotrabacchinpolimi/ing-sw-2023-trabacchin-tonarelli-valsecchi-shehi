package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TileSubjectTest {

    @Test
    void getTileType() {
        assertEquals(TileType.CAT, TileSubject.CAT_BLACK.getTileType());
    }

    @Test
    void getTileTypeName() {
        assertEquals(TileType.CAT.toString(), TileSubject.CAT_BLACK.getTileTypeName());
    }

    @Test
    void testToString() {
        assertEquals("GRAY_CAT", TileSubject.CAT_GRAY.toString());
        assertEquals("ORANGE_CAT", TileSubject.CAT_ORANGE.toString());
        assertEquals("BLACK_CAT", TileSubject.CAT_BLACK.toString());
        assertEquals("MUSIC_TROPHY", TileSubject.TROPHY_MUSIC.toString());
    }
}