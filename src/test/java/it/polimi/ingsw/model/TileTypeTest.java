package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TileTypeTest {

    @Test
    void toAbbreviationString() {
        for(TileType t : TileType.values()){
            assertEquals(t.name().substring(0,1), t.toAbbreviationString());
        }
    }

    @Test
    void testToString() {
        for(TileType t : TileType.values()){
            assertEquals(t.name(), t.toString());
        }
    }
}