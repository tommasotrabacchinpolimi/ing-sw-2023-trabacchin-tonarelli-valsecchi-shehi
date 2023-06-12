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
        assertEquals("CAT_GRAY", TileSubject.CAT_GRAY.toString());
        assertEquals("CAT_ORANGE", TileSubject.CAT_ORANGE.toString());
        assertEquals("CAT_BLACK", TileSubject.CAT_BLACK.toString());
        assertEquals("TROPHY_MUSIC", TileSubject.TROPHY_MUSIC.toString());
    }
}