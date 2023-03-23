package model;

import org.junit.jupiter.api.Test;

import javax.sound.sampled.Line;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class LineCommonGoalTest {

    @Test
    void getIncRow() {
        int incRow, incCol = 0, numberLines = 2, numberTiles = 4;
        int[] differentTiles = {6};
        Random rnd = new Random();
        incRow = rnd.nextInt(0,1);
        LineCommonGoal goal = new LineCommonGoal(incRow, incCol,numberLines, numberTiles, differentTiles );
        assertEquals(incRow, goal.getIncRow());
    }

    @Test
    void setIncRow() {
        int incRow = 0, incCol = 0, numberLines = 2, numberTiles = 4;
        int[] differentTiles = {6};
        Random rnd = new Random();
        LineCommonGoal goal = new LineCommonGoal(incRow, incCol,numberLines, numberTiles, differentTiles );
        incRow = rnd.nextInt(0,1);
        goal.setIncRow(incRow);
        assertEquals(incRow, goal.getIncRow());
    }

    @Test
    void getIncCol() {
        int incRow = 0, incCol, numberLines = 2, numberTiles = 4;
        int[] differentTiles = {6};
        Random rnd = new Random();
        incCol = rnd.nextInt(0,1);
        LineCommonGoal goal = new LineCommonGoal(incRow, incCol,numberLines, numberTiles, differentTiles );
        assertEquals(incCol, goal.getIncCol());
    }

    @Test
    void setIncCol() {
        int incRow = 0, incCol = 0, numberLines = 2, numberTiles = 4;
        int[] differentTiles = {6};
        Random rnd = new Random();
        LineCommonGoal goal = new LineCommonGoal(incRow, incCol,numberLines, numberTiles, differentTiles );
        incCol = rnd.nextInt(0,1);
        goal.setIncCol(incCol);
        assertEquals(incCol, goal.getIncCol());
    }

    @Test
    void getLinesNumber() {
        int incRow = 0, incCol = 1, numberLines, numberTiles = 4;
        int[] differentTiles = {6};
        Random rnd = new Random();
        numberLines = rnd.nextInt(0,1);
        LineCommonGoal goal = new LineCommonGoal(incRow, incCol,numberLines, numberTiles, differentTiles );
        assertEquals(numberLines, goal.getLinesNumber());
    }

    @Test
    void setLinesNumber() {
        int incRow = 0, incCol = 1, numberLines = 2, numberTiles = 4;
        int[] differentTiles = {6};
        Random rnd = new Random();
        LineCommonGoal goal = new LineCommonGoal(incRow, incCol,numberLines, numberTiles, differentTiles );
        numberLines = rnd.nextInt(1,5);
        goal.setLinesNumber(numberLines);
        assertEquals(numberLines, goal.getLinesNumber());
    }

    @Test
    void getDifferentTiles() {
        int incRow = 0, incCol = 1, numberLines = 2, numberTiles = 4;
        int[] differentTiles = {6};
        Random rnd = new Random();
        LineCommonGoal goal = new LineCommonGoal(incRow, incCol,numberLines, numberTiles, differentTiles );
        assertEquals(differentTiles, goal.getDifferentTiles());
    }

    @Test
    void setDifferentTiles() {
        int incRow = 0, incCol = 1, numberLines = 2, numberTiles = 4;
        int[] differentTiles = {6};
        Random rnd = new Random();
        LineCommonGoal goal = new LineCommonGoal(incRow, incCol,numberLines, numberTiles, differentTiles );
        goal.setDifferentTiles(differentTiles);
        assertEquals(differentTiles, goal.getDifferentTiles());
    }

    @Test
    void rule() {
    }
}