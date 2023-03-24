package model;

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
        EntryPatternGoal cell = new EntryPatternGoal(col, row, TileType.CAT);
        assertEquals(col, cell.getColumn());
    }

    @Test
    void getRow() {
        int row, col;
        Random rnd = new Random();
        row = rnd.nextInt(6);
        col = rnd.nextInt(5);
        EntryPatternGoal cell = new EntryPatternGoal(col, row, TileType.CAT);
        assertEquals(row, cell.getRow());
    }

    @Test
    void getTileType() {
        int row, col;
        Random rnd = new Random();
        row = rnd.nextInt(6);
        col = rnd.nextInt(5);
        EntryPatternGoal cell = new EntryPatternGoal(col, row, TileType.CAT);
        assertEquals(TileType.CAT, cell.getTileType());
    }
}