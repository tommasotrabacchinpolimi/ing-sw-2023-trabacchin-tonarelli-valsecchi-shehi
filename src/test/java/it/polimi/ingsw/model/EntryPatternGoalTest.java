package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

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
        int row, col;
        Random rnd = new Random();
        row = rnd.nextInt(6);
        col = rnd.nextInt(5);
        EntryPatternGoal cell = new EntryPatternGoal(col, row, "CAT");
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
    void getArrayIndexes(){
        int row, col;
        Random rnd = new Random();
        row = rnd.nextInt(6);
        col = rnd.nextInt(5);
        EntryPatternGoal cell = new EntryPatternGoal(row, col, TileType.CAT);
        assertArrayEquals(new int[]{row, col}, cell.getArrayIndexes());
    }
}