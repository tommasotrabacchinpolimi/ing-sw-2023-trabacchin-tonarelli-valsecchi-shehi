package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TileSubjectTest {

    @Test
    void getTileType() {
        assertEquals(TileType.CAT.toString(), TileSubject.BLACK_CAT.getTileType().toString());
    }

    @Test
    void getTileTypeName() {
        assertEquals(TileType.CAT.toString(), TileSubject.BLACK_CAT.getTileTypeName());
    }

    @Test
    void testToString() {
        assertEquals("BLACK_CAT", TileSubject.BLACK_CAT.toString());
    }
}