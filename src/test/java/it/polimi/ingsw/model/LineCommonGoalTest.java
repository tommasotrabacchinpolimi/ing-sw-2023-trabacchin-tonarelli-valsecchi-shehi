package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class LineCommonGoalTest {

    @Test
    void getNumberOfTiles() {
        int incRow = 1, incCol = 0, numberLines = 2, numberTiles = 4;
        int[] differentTiles = {6};
        LineCommonGoal goal = new LineCommonGoal(incRow, incCol, numberLines, numberTiles, differentTiles);
        assertEquals(numberTiles, goal.getNumberOfTiles());
    }

    @Test
    void setNumberOfTiles() {
        int incRow = 1, incCol = 0, numberLines = 2, numberTiles = 4;
        int[] differentTiles = {6};
        LineCommonGoal goal = new LineCommonGoal(incRow, incCol, numberLines, numberTiles, differentTiles);
        assertEquals(numberTiles, goal.getNumberOfTiles());
        numberTiles = 6;
        goal.setNumberOfTiles(numberTiles);
        assertEquals(numberTiles, goal.getNumberOfTiles());
    }

    @Test
    void getIncRow() {
        int incRow, incCol = 0, numberLines = 2, numberTiles = 4;
        int[] differentTiles = {6};
        Random rnd = new Random();
        incRow = rnd.nextInt(0, 1);
        LineCommonGoal goal = new LineCommonGoal(incRow, incCol, numberLines, numberTiles, differentTiles);
        assertEquals(incRow, goal.getIncRow());
    }

    @Test
    void setIncRow() {
        int incRow = 0, incCol = 0, numberLines = 2, numberTiles = 4;
        int[] differentTiles = {6};
        Random rnd = new Random();
        LineCommonGoal goal = new LineCommonGoal(incRow, incCol, numberLines, numberTiles, differentTiles);
        incRow = rnd.nextInt(0, 1);
        goal.setIncRow(incRow);
        assertEquals(incRow, goal.getIncRow());
    }

    @Test
    void getIncCol() {
        int incRow = 0, incCol, numberLines = 2, numberTiles = 4;
        int[] differentTiles = {6};
        Random rnd = new Random();
        incCol = rnd.nextInt(0, 1);
        LineCommonGoal goal = new LineCommonGoal(incRow, incCol, numberLines, numberTiles, differentTiles);
        assertEquals(incCol, goal.getIncCol());
    }

    @Test
    void setIncCol() {
        int incRow = 0, incCol = 0, numberLines = 2, numberTiles = 4;
        int[] differentTiles = {6};
        Random rnd = new Random();
        LineCommonGoal goal = new LineCommonGoal(incRow, incCol, numberLines, numberTiles, differentTiles);
        incCol = rnd.nextInt(0, 1);
        goal.setIncCol(incCol);
        assertEquals(incCol, goal.getIncCol());
    }

    @Test
    void getLinesNumber() {
        int incRow = 0, incCol = 1, numberLines, numberTiles = 4;
        int[] differentTiles = {6};
        Random rnd = new Random();
        numberLines = rnd.nextInt(0, 1);
        LineCommonGoal goal = new LineCommonGoal(incRow, incCol, numberLines, numberTiles, differentTiles);
        assertEquals(numberLines, goal.getLinesNumber());
    }

    @Test
    void setLinesNumber() {
        int incRow = 0, incCol = 1, numberLines = 2, numberTiles = 4;
        int[] differentTiles = {6};
        Random rnd = new Random();
        LineCommonGoal goal = new LineCommonGoal(incRow, incCol, numberLines, numberTiles, differentTiles);
        numberLines = rnd.nextInt(1, 5);
        goal.setLinesNumber(numberLines);
        assertEquals(numberLines, goal.getLinesNumber());
    }

    @Test
    void getDifferentTiles() {
        int incRow = 0, incCol = 1, numberLines = 2, numberTiles = 4;
        int[] differentTiles = {6};
        LineCommonGoal goal = new LineCommonGoal(incRow, incCol, numberLines, numberTiles, differentTiles);
        for(int i = 0; i < goal.getDifferentTiles().length; i++ ){
            assertEquals(differentTiles[i], goal.getDifferentTiles()[i]);
        }
    }

    @Test
    void setDifferentTiles() { //tests also the method removeDuplicateAndSort
        int incRow = 0, incCol = 1, numberLines = 2, numberTiles = 4;
        int[] differentTiles = {2,1,2,6};
        int[] expectedDifferentTiles = {1,2,6};
        LineCommonGoal goal = new LineCommonGoal(incRow, incCol, numberLines, numberTiles, differentTiles);
        goal.setDifferentTiles(differentTiles);
        for(int i = 0; i < goal.getDifferentTiles().length; i++ ){
            assertEquals(expectedDifferentTiles[i], goal.getDifferentTiles()[i]);
        }
    }

    @Test
    void rule() {
        int[] differentTiles; // può essere {1,2,3} oppure {5} / {6}
        int incRow, incCol; // 1 o 0, opposti incCol=(incRow+1)%2
        int numberLines; //numero di righe che devono soddisfare la condizione: 2 o 3
        int numberTiles; //per colonne sei, per righe 5
        LineCommonGoal goal;

        incCol = 5; incRow = 0;
        goal = new LineCommonGoal(incRow,incCol, 1, 1, new int[]{6});
        assertNull(goal.rule(createLineDesign(1)));

        incCol = 1; incRow = 7;
        goal = new LineCommonGoal(incRow, incCol, 1, 1, new int[]{6} );
        assertNull(goal.rule(createLineDesign(1)));

        incCol = 1; incRow = 0;
        numberLines = 7;
        goal = new LineCommonGoal(incRow, incCol, numberLines, 1, new int[]{6});
        assertNull(goal.rule(createLineDesign(1)));

        incCol = 1; incRow = 0;
        numberLines = -1;
        goal = new LineCommonGoal(incRow, incCol, numberLines, 1, new int[]{6});
        assertNull(goal.rule(createLineDesign(1)));

        incCol = 1; incRow = 0;
        numberLines = 3;
        numberTiles = 9;
        goal = new LineCommonGoal(incRow, incCol, numberLines, numberTiles, new int[]{6});
        assertNull(goal.rule(createLineDesign(1)));

        incCol = 1; incRow = 0;
        numberLines = 3;
        numberTiles = -4;
        goal = new LineCommonGoal(incRow, incCol, numberLines, numberTiles, new int[]{6});
        assertNull(goal.rule(createLineDesign(1)));

        incCol = 1; incRow = 0;
        numberLines = 3;
        numberTiles = 3;
        differentTiles = new int[]{-1,2};
        goal = new LineCommonGoal(incRow, incCol, numberLines, numberTiles, differentTiles);
        assertNull(goal.rule(createLineDesign(1)));

        incCol = 1; incRow = 0;
        numberLines = 3;
        numberTiles = 3;
        differentTiles = new int[]{1,6};
        goal = new LineCommonGoal(incRow, incCol, numberLines, numberTiles, differentTiles);
        assertNull(goal.rule(createLineDesign(1)));

        incCol = 0; incRow = 0;
        for(int i = 1; i <= 3; i++){
            goal = new LineCommonGoal(incRow, incCol, 1, 1, new int[]{6});
            assertNull(goal.rule(createLineDesign(i)));
        }

        incRow = 1; incCol = 1;
        for(int i = 1; i <= 3; i++){
            goal = new LineCommonGoal(incRow, incCol, 1, 1, new int[]{6});
            assertNull(goal.rule(createLineDesign(i)));
        }

        //incRow = 1
        incRow = 1; incCol = 0; numberLines = 2; numberTiles = 5; differentTiles = new int[]{5};
        goal = new LineCommonGoal(incRow, incCol, numberLines, numberTiles, differentTiles);
        for(int i = 0; i < getExpectedResult(incRow,incCol,numberLines,numberTiles,differentTiles,1).size(); i++){
            assertEquals(getExpectedResult(incRow,incCol,numberLines,numberTiles,differentTiles,1).get(i).toString(),
                    goal.rule(createLineDesign(1)).get(i).toString());
        }
        assertNull(goal.rule(createLineDesign(2)));

        numberLines = 4; differentTiles = new int[]{1,2,3};
        goal = new LineCommonGoal(incRow, incCol, numberLines, numberTiles, differentTiles);

        for(int i = 0; i < getExpectedResult(incRow,incCol,numberLines,numberTiles,differentTiles,1).size(); i++){
            assertEquals(getExpectedResult(incRow,incCol,numberLines,numberTiles,differentTiles,1).get(i).toString(),
                    goal.rule(createLineDesign(1)).get(i).toString());
        }
        for(int i = 0; i < getExpectedResult(incRow,incCol,numberLines,numberTiles,differentTiles,2).size(); i++){
            assertEquals(getExpectedResult(incRow,incCol,numberLines,numberTiles,differentTiles,2).get(i).toString(),
                    goal.rule(createLineDesign(2)).get(i).toString());
        }

        //INCROW = 0
        incRow = 0; incCol = 1; numberLines = 2; numberTiles = 6; differentTiles = new int[]{6};
        goal = new LineCommonGoal(incRow, incCol, numberLines, numberTiles, differentTiles);
        assertNull(goal.rule(createLineDesign(1)));
        assertNull(goal.rule(createLineDesign(2)));

        numberLines = 3; differentTiles = new int[]{1,2,3};
        goal = new LineCommonGoal(incRow,incCol,numberLines,numberTiles,differentTiles);

        for(int i = 0; i < getExpectedResult(incRow,incCol,numberLines,numberTiles,differentTiles,1).size(); i++){
            assertEquals(getExpectedResult(incRow,incCol,numberLines,numberTiles,differentTiles,1).get(i).toString(),
                    goal.rule(createLineDesign(1)).get(i).toString());
        }
        for(int i = 0; i < getExpectedResult(incRow,incCol,numberLines,numberTiles,differentTiles,2).size(); i++){
            assertEquals(getExpectedResult(incRow,incCol,numberLines,numberTiles,differentTiles,2).get(i).toString(),
                    goal.rule(createLineDesign(2)).get(i).toString());
        }

        assertNull(goal.rule(null));

    }

    private TileType[][] createLineDesign(int number) {
        switch (number) {
            case 1 -> {
                return new TileType[][]{
                        {TileType.CAT,  TileType.CAT, TileType.CAT, TileType.CAT, TileType.PLANT},
                        {TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT},
                        {TileType.CAT, TileType.CAT, TileType.TROPHY, TileType.CAT, TileType.BOOK},
                        {TileType.TROPHY, TileType.PLANT, TileType.FRAME, TileType.CAT, TileType.GAME},
                        {TileType.CAT, TileType.CAT, TileType.CAT, TileType.BOOK, TileType.CAT},
                        {TileType.GAME, TileType.FRAME, TileType.PLANT, TileType.BOOK, TileType.CAT}
                };
            }

            case 2 -> {
                return new TileType[][]{
                        {null,TileType.CAT , null, TileType.CAT,TileType.CAT},
                        {null, TileType.CAT, null, TileType.CAT ,  TileType.CAT},
                        {TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT, TileType.GAME},
                        {TileType.PLANT, TileType.PLANT, TileType.PLANT, TileType.CAT, TileType.GAME},
                        {TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT},
                        {TileType.GAME, TileType.GAME, TileType.PLANT, TileType.BOOK, TileType.GAME}
                };
            }

            /*case 3 -> {
                return new TileType[][]{
                        {TileType.PLANT, TileType.PLANT, TileType.GAME, null, null},
                        {TileType.TROPHY, TileType.CAT, TileType.CAT, null, null},
                        {TileType.BOOK, TileType.FRAME, TileType.TROPHY, null, null},
                        {TileType.CAT, TileType.PLANT, TileType.PLANT, TileType.CAT, TileType.GAME},
                        {TileType.FRAME, TileType.CAT, TileType.FRAME, TileType.CAT, TileType.CAT},
                        {TileType.GAME, TileType.GAME, TileType.BOOK, TileType.BOOK, TileType.CAT}
                };
            }
            */

            default -> {
                return null;
            }
        }
    }


    private List<EntryPatternGoal> getExpectedResult(int incRow, int incCol, int numberLines, int numberTiles, int[] differentTiles, int number){
        List<EntryPatternGoal> result = new ArrayList<>();
        switch(number){
            case 1 -> {
                switch(incRow){
                    case 0 -> {
                        switch(numberLines){
                            case 3 -> {
                                {
                                    result.add(new EntryPatternGoal(0,0,TileType.CAT));
                                    result.add(new EntryPatternGoal(1,0,TileType.CAT));
                                    result.add(new EntryPatternGoal(2,0,TileType.CAT));
                                    result.add(new EntryPatternGoal(3,0,TileType.TROPHY));
                                    result.add(new EntryPatternGoal(4,0,TileType.CAT));
                                    result.add(new EntryPatternGoal(5,0,TileType.GAME));
                                    result.add(new EntryPatternGoal(0,1,TileType.CAT));
                                    result.add(new EntryPatternGoal(1,1,TileType.CAT));
                                    result.add(new EntryPatternGoal(2,1,TileType.CAT));
                                    result.add(new EntryPatternGoal(3,1,TileType.PLANT));
                                    result.add(new EntryPatternGoal(4,1,TileType.CAT));
                                    result.add(new EntryPatternGoal(5,1,TileType.FRAME));
                                    result.add(new EntryPatternGoal(0,3,TileType.CAT));
                                    result.add(new EntryPatternGoal(1,3,TileType.CAT));
                                    result.add(new EntryPatternGoal(2,3,TileType.CAT));
                                    result.add(new EntryPatternGoal(3,3,TileType.CAT));
                                    result.add(new EntryPatternGoal(4,3,TileType.BOOK));
                                    result.add(new EntryPatternGoal(5,3,TileType.BOOK));
                                    return result;
                                }
                            }

                            default -> { // comprende numberLines == 2 && numberTiles == 6 && differentTiles.equals(new int[]{6})
                                return null;
                            }
                        }

                    }

                    case 1 -> {
                        switch(numberLines){
                            case 2 -> {
                                {
                                    result.add(new EntryPatternGoal(3,0,TileType.TROPHY));
                                    result.add(new EntryPatternGoal(3,1,TileType.PLANT));
                                    result.add(new EntryPatternGoal(3,2,TileType.FRAME));
                                    result.add(new EntryPatternGoal(3,3,TileType.CAT));
                                    result.add(new EntryPatternGoal(3,4,TileType.GAME));
                                    result.add(new EntryPatternGoal(5,0,TileType.GAME));
                                    result.add(new EntryPatternGoal(5,1,TileType.FRAME));
                                    result.add(new EntryPatternGoal(5,2,TileType.PLANT));
                                    result.add(new EntryPatternGoal(5,3,TileType.BOOK));
                                    result.add(new EntryPatternGoal(5,4,TileType.CAT));
                                    return result;
                                }
                            }

                            case 4 -> {
                                result.add(new EntryPatternGoal(0,0,TileType.CAT));
                                result.add(new EntryPatternGoal(0,1,TileType.CAT));
                                result.add(new EntryPatternGoal(0,2,TileType.CAT));
                                result.add(new EntryPatternGoal(0,3,TileType.CAT));
                                result.add(new EntryPatternGoal(0,4,TileType.PLANT));
                                result.add(new EntryPatternGoal(1,0,TileType.CAT));
                                result.add(new EntryPatternGoal(1,1,TileType.CAT));
                                result.add(new EntryPatternGoal(1,2,TileType.CAT));
                                result.add(new EntryPatternGoal(1,3,TileType.CAT));
                                result.add(new EntryPatternGoal(1,4,TileType.CAT));
                                result.add(new EntryPatternGoal(2,0,TileType.CAT));
                                result.add(new EntryPatternGoal(2,1,TileType.CAT));
                                result.add(new EntryPatternGoal(2,2,TileType.TROPHY));
                                result.add(new EntryPatternGoal(2,3,TileType.CAT));
                                result.add(new EntryPatternGoal(2,4,TileType.BOOK));
                                result.add(new EntryPatternGoal(4,0,TileType.CAT));
                                result.add(new EntryPatternGoal(4,1,TileType.CAT));
                                result.add(new EntryPatternGoal(4,2,TileType.CAT));
                                result.add(new EntryPatternGoal(4,3,TileType.BOOK));
                                result.add(new EntryPatternGoal(4,4,TileType.CAT));
                                return result;
                            }

                            default -> {
                                return null;
                            }
                        }
                    }

                    default -> {
                        return null;
                    }
                }
            }

            case 2 -> {
                switch(incRow){
                    case 0 -> {
                        switch(numberLines){
                            case 3 -> {
                                {
                                    result.add(new EntryPatternGoal(0,1,TileType.CAT));
                                    result.add(new EntryPatternGoal(1,1,TileType.CAT));
                                    result.add(new EntryPatternGoal(2,1,TileType.CAT));
                                    result.add(new EntryPatternGoal(3,1,TileType.PLANT));
                                    result.add(new EntryPatternGoal(4,1,TileType.CAT));
                                    result.add(new EntryPatternGoal(5,1,TileType.GAME));
                                    result.add(new EntryPatternGoal(0,3,TileType.CAT));
                                    result.add(new EntryPatternGoal(1,3,TileType.CAT));
                                    result.add(new EntryPatternGoal(2,3,TileType.CAT));
                                    result.add(new EntryPatternGoal(3,3,TileType.CAT));
                                    result.add(new EntryPatternGoal(4,3,TileType.CAT));
                                    result.add(new EntryPatternGoal(5,3,TileType.BOOK));
                                    result.add(new EntryPatternGoal(0,4,TileType.CAT));
                                    result.add(new EntryPatternGoal(1,4,TileType.CAT));
                                    result.add(new EntryPatternGoal(2,4,TileType.GAME));
                                    result.add(new EntryPatternGoal(3,4,TileType.GAME));
                                    result.add(new EntryPatternGoal(4,4,TileType.CAT));
                                    result.add(new EntryPatternGoal(5,4,TileType.GAME));
                                    return result;
                                }
                            }

                            default -> {
                                return null;
                            }
                        }
                    }

                    case 1 -> {
                        switch (numberLines){
                            case 4 -> {
                                {
                                    result.add(new EntryPatternGoal(2,0,TileType.CAT));
                                    result.add(new EntryPatternGoal(2,1,TileType.CAT));
                                    result.add(new EntryPatternGoal(2,2,TileType.CAT));
                                    result.add(new EntryPatternGoal(2,3,TileType.CAT));
                                    result.add(new EntryPatternGoal(2,4,TileType.GAME));
                                    result.add(new EntryPatternGoal(3,0,TileType.PLANT));
                                    result.add(new EntryPatternGoal(3,1,TileType.PLANT));
                                    result.add(new EntryPatternGoal(3,2,TileType.PLANT));
                                    result.add(new EntryPatternGoal(3,3,TileType.CAT));
                                    result.add(new EntryPatternGoal(3,4,TileType.GAME));
                                    result.add(new EntryPatternGoal(4,0,TileType.CAT));
                                    result.add(new EntryPatternGoal(4,1,TileType.CAT));
                                    result.add(new EntryPatternGoal(4,2,TileType.CAT));
                                    result.add(new EntryPatternGoal(4,3,TileType.CAT));
                                    result.add(new EntryPatternGoal(4,4,TileType.CAT));
                                    result.add(new EntryPatternGoal(5,0,TileType.GAME));
                                    result.add(new EntryPatternGoal(5,1,TileType.GAME));
                                    result.add(new EntryPatternGoal(5,2,TileType.PLANT));
                                    result.add(new EntryPatternGoal(5,3,TileType.BOOK));
                                    result.add(new EntryPatternGoal(5,4,TileType.GAME));
                                    return result;
                                }
                            }

                            default -> {
                                return null;
                            }
                        }
                    }

                    default -> {
                        return null;
                    }
                }
            }

            default -> {
                return null;
            }
        }
    }


}