package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TileTypeTest {

    @Test
    void testToString() {
        assertEquals("CAT", TileType.CAT.toString());
    }
}