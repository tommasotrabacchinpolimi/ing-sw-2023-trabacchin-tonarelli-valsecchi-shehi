package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

//100%
class EntryPatternGoalTest {

    @Test
    void getColumn() {
        int row, col;
        Random rnd = new Random();

        row = rnd.nextInt(6);
        col = rnd.nextInt(5);
        EntryPatternGoal cell = new EntryPatternGoal(row, col, TileType.CAT);
        assertEquals(col, cell.getColumn());

        EntryPatternGoal cell1 = new EntryPatternGoal();
        assertEquals(0, cell1.getColumn());
    }

    @Test
    void getRow() {
        int row, column;
        Random rnd = new Random();

        row = rnd.nextInt(6);
        column = rnd.nextInt(5);
        EntryPatternGoal cell = new EntryPatternGoal(row, column, "CAT");
        assertEquals(row, cell.getRow());

        EntryPatternGoal cell1 = new EntryPatternGoal();
        assertEquals(0, cell1.getRow());
    }

    @Test
    void getTileType() {
        int row, col;
        Random rnd = new Random();

        row = rnd.nextInt(6);
        col = rnd.nextInt(5);
        EntryPatternGoal cell = new EntryPatternGoal(row, col, TileType.CAT);

        assertEquals(TileType.CAT, cell.getTileType());

        EntryPatternGoal cell1 = new EntryPatternGoal();
        assertNull(cell1.getTileType());
    }

    @Test
    void fromStringToTileType(){
        assertThrows(IllegalArgumentException.class, () -> {new EntryPatternGoal(2, 3, "D");});
    }

    @Test
    void getArrayIndexes(){
        int row, col;
        Random rnd = new Random();

        row = rnd.nextInt(6);
        col = rnd.nextInt(5);
        EntryPatternGoal cell = new EntryPatternGoal(row, col, TileType.CAT);
        assertArrayEquals(new int[]{row, col}, cell.getArrayIndexes());
    }

    @Test
    void testToString() {
        EntryPatternGoal entryPatternGoal = new EntryPatternGoal();

        assertEquals("EntryPatternGoal{row=0, column=0, tileType=null}", entryPatternGoal.toString());

        EntryPatternGoal entryPatternGoal1 = new EntryPatternGoal(1,2,TileType.CAT);
        assertEquals("EntryPatternGoal{row=1, column=2, tileType=CAT}", entryPatternGoal1.toString());
    }

    @Test
    void testEquals() {
        EntryPatternGoal entryPatternGoal = new EntryPatternGoal();
        EntryPatternGoal entryPatternGoal1 = new EntryPatternGoal();

        assertEquals(entryPatternGoal1, entryPatternGoal1);
        assertNotEquals(entryPatternGoal, null);
        assertEquals(entryPatternGoal1, entryPatternGoal);

        EntryPatternGoal entryPatternGoal2 = new EntryPatternGoal(1,2,"CAT");
        assertNotEquals(entryPatternGoal1, entryPatternGoal2);
    }

    @Test
    void testHashCode() {
        EntryPatternGoal entryPatternGoal = new EntryPatternGoal();
        EntryPatternGoal entryPatternGoal1 = new EntryPatternGoal();

        assertEquals(entryPatternGoal1.hashCode(), entryPatternGoal.hashCode());

        EntryPatternGoal entryPatternGoal2 = new EntryPatternGoal(1, 2, "CAT");

        assertNotEquals(entryPatternGoal1.hashCode(), entryPatternGoal2.hashCode());
    }

}