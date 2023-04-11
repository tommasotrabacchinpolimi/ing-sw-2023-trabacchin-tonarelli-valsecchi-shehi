package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

class TupleCommonGoalTest {

    @Test
    void getGroupsNumber() {
        int groupsNumber = 2;
        int adjacentTilesPo2 = 2;
        boolean square = false;
        boolean separated = false;
        boolean sameTypeOnly = true;
        TupleCommonGoal goal = new TupleCommonGoal("Description",groupsNumber,adjacentTilesPo2,square,separated,sameTypeOnly);
        assertEquals(groupsNumber, goal.getGroupsNumber());
    }

    @Test
    void setGroupsNumber() {
        int groupsNumber = 2;
        int adjacentTilesPo2 = 2;
        boolean square = false;
        boolean separated = false;
        boolean sameTypeOnly = true;
        TupleCommonGoal goal = new TupleCommonGoal("Description",3,adjacentTilesPo2,square,separated,sameTypeOnly);
        goal.setGroupsNumber(groupsNumber);
        assertEquals(groupsNumber, goal.getGroupsNumber());
    }

    @Test
    void getAdjacentTilesPo2() {
        int groupsNumber = 2;
        int adjacentTilesPo2 = 2;
        boolean square = false;
        boolean separated = false;
        boolean sameTypeOnly = true;
        TupleCommonGoal goal = new TupleCommonGoal("Description",groupsNumber,adjacentTilesPo2,square,separated,sameTypeOnly);
        assertEquals(adjacentTilesPo2, goal.getAdjacentTilesPo2());
    }

    @Test
    void setAdjacentTilesPo2() {
        int groupsNumber = 2;
        int adjacentTilesPo2 = 2;
        boolean square = false;
        boolean separated = false;
        boolean sameTypeOnly = true;
        TupleCommonGoal goal = new TupleCommonGoal("Description",groupsNumber,3,square,separated,sameTypeOnly);
        goal.setAdjacentTilesPo2(adjacentTilesPo2);
        assertEquals(adjacentTilesPo2, goal.getAdjacentTilesPo2());
    }

    @Test
    void isSquare() {
        int groupsNumber = 2;
        int adjacentTilesPo2 = 2;
        boolean square = false;
        boolean separated = false;
        boolean sameTypeOnly = true;
        TupleCommonGoal goal = new TupleCommonGoal("Description",groupsNumber,adjacentTilesPo2,square,separated,sameTypeOnly);
        assertEquals(square, goal.isSquare());
    }

    @Test
    void setSquare() {
        int groupsNumber = 2;
        int adjacentTilesPo2 = 2;
        boolean square = false;
        boolean separated = false;
        boolean sameTypeOnly = true;
        Stack<Integer> stack = new Stack<>();
        TupleCommonGoal goal = new TupleCommonGoal(stack,groupsNumber,adjacentTilesPo2, true,separated,sameTypeOnly);
        goal.setSquare(square);
        assertEquals(square, goal.isSquare());
    }

    @Test
    void isSeparated() {
        int groupsNumber = 2;
        int adjacentTilesPo2 = 2;
        boolean square = false;
        boolean separated = false;
        boolean sameTypeOnly = true;
        Stack<Integer> stack = new Stack<>();
        TupleCommonGoal goal = new TupleCommonGoal(stack,"Description",groupsNumber,adjacentTilesPo2,square,separated,sameTypeOnly);
        assertEquals(separated, goal.isSeparated());
    }

    @Test
    void setSeparated() {
        int groupsNumber = 2;
        int adjacentTilesPo2 = 2;
        boolean square = false;
        boolean separated = false;
        boolean sameTypeOnly = true;
        TupleCommonGoal goal = new TupleCommonGoal(groupsNumber,adjacentTilesPo2,square,true,sameTypeOnly);
        goal.setSeparated(separated);
        assertEquals(separated, goal.isSeparated());
    }

    @Test
    void isSameTypeOnly() {
        int groupsNumber = 2;
        int adjacentTilesPo2 = 2;
        boolean square = false;
        boolean separated = false;
        boolean sameTypeOnly = true;
        TupleCommonGoal goal = new TupleCommonGoal(groupsNumber,adjacentTilesPo2,square,separated,sameTypeOnly);
        assertEquals(sameTypeOnly, goal.isSameTypeOnly());
    }

    @Test
    void setSameTypeOnly() {
        int groupsNumber = 2;
        int adjacentTilesPo2 = 2;
        boolean square = false;
        boolean separated = false;
        boolean sameTypeOnly = true;
        TupleCommonGoal goal = new TupleCommonGoal(groupsNumber,adjacentTilesPo2,square,separated,false);
        goal.setSameTypeOnly(sameTypeOnly);
        assertEquals(sameTypeOnly, goal.isSameTypeOnly());
    }

    @Test
    void rule() {
        int groupNumber = 8;
        int adjacentParam = 0;
        boolean square = true;
        boolean separated = true;
        boolean sameTypeOnly = true;
        int index = 0;

       // GroupAndSquareCommonGoal common = new GroupAndSquareCommonGoal(4, "Description",
              //  groupNumber, adjacentParam, square, separated, sameTypeOnly);

        //assertTrue(compareObjects(getExpected(getBookShelf(index)),common.rule(getBookShelf(index))));
    }

    private List<EntryPatternGoal> getExpected(int index) {
        List<EntryPatternGoal> result = new ArrayList<EntryPatternGoal>();
        switch (index){
            case 1 -> {
                result.add(new EntryPatternGoal(0, 0, TileType.TROPHY));
                result.add(new EntryPatternGoal(0, 2, TileType.TROPHY));
                result.add(new EntryPatternGoal(0, 4, TileType.TROPHY));
                result.add(new EntryPatternGoal(1, 1, TileType.TROPHY));
                result.add(new EntryPatternGoal(1, 3, TileType.TROPHY));
                result.add(new EntryPatternGoal(2, 0, TileType.TROPHY));
                result.add(new EntryPatternGoal(2, 2, TileType.TROPHY));
                result.add(new EntryPatternGoal(2, 4, TileType.TROPHY));
            }
            case 2 -> {
                result.add(new EntryPatternGoal(0, 0, TileType.FRAME));
                result.add(new EntryPatternGoal(0, 2, TileType.FRAME));
                result.add(new EntryPatternGoal(0, 4, TileType.FRAME));
                result.add(new EntryPatternGoal(1, 1, TileType.FRAME));
                result.add(new EntryPatternGoal(1, 3, TileType.FRAME));
                result.add(new EntryPatternGoal(2, 0, TileType.FRAME));
                result.add(new EntryPatternGoal(2, 2, TileType.FRAME));
                result.add(new EntryPatternGoal(2, 4, TileType.FRAME));
            }
            case 3 -> {
                result.add(new EntryPatternGoal(0, 1, TileType.BOOK));
                result.add(new EntryPatternGoal(1, 2, TileType.BOOK));
                result.add(new EntryPatternGoal(1, 4, TileType.BOOK));
                result.add(new EntryPatternGoal(2, 1, TileType.BOOK));
                result.add(new EntryPatternGoal(2, 3, TileType.BOOK));
                result.add(new EntryPatternGoal(3, 2, TileType.BOOK));
                result.add(new EntryPatternGoal(4, 3, TileType.BOOK));
                result.add(new EntryPatternGoal(5, 4, TileType.BOOK));
            }

            case 4 -> {
                result.add(new EntryPatternGoal(1, 1, TileType.BOOK));
                result.add(new EntryPatternGoal(1, 3, TileType.BOOK));
                result.add(new EntryPatternGoal(2, 0, TileType.BOOK));
                result.add(new EntryPatternGoal(2, 2, TileType.BOOK));
                result.add(new EntryPatternGoal(2, 4, TileType.BOOK));
                result.add(new EntryPatternGoal(3, 1, TileType.BOOK));
                result.add(new EntryPatternGoal(3, 3, TileType.BOOK));
                result.add(new EntryPatternGoal(5, 3, TileType.BOOK));
            }
            case 5 -> {
                result.add(new EntryPatternGoal(2, 1, TileType.CAT));
                result.add(new EntryPatternGoal(2, 3, TileType.CAT));
                result.add(new EntryPatternGoal(3, 0, TileType.CAT));
                result.add(new EntryPatternGoal(3, 2, TileType.CAT));
                result.add(new EntryPatternGoal(3, 4, TileType.CAT));
                result.add(new EntryPatternGoal(4, 1, TileType.CAT));
                result.add(new EntryPatternGoal(4, 3, TileType.CAT));
                result.add(new EntryPatternGoal(0, 3, TileType.CAT));
            }

            case 6 -> {
                result.add(new EntryPatternGoal(2, 0, TileType.CAT));
                result.add(new EntryPatternGoal(2, 2, TileType.CAT));
                result.add(new EntryPatternGoal(2, 4, TileType.CAT));
                result.add(new EntryPatternGoal(3, 1, TileType.CAT));
                result.add(new EntryPatternGoal(3, 3, TileType.CAT));
                result.add(new EntryPatternGoal(4, 0, TileType.CAT));
                result.add(new EntryPatternGoal(4, 2, TileType.CAT));
                result.add(new EntryPatternGoal(4, 4, TileType.CAT));
            }
            case 7 -> {
                result.add(new EntryPatternGoal(0, 1, TileType.CAT));
                result.add(new EntryPatternGoal(1, 2, TileType.CAT));
                result.add(new EntryPatternGoal(2, 3, TileType.CAT));
                result.add(new EntryPatternGoal(3, 1, TileType.CAT));
                result.add(new EntryPatternGoal(4, 0, TileType.CAT));
                result.add(new EntryPatternGoal(4, 4, TileType.CAT));
                result.add(new EntryPatternGoal(5, 1, TileType.CAT));
                result.add(new EntryPatternGoal(5, 4, TileType.CAT));
            }
            case 8 -> {
                result.add(new EntryPatternGoal(0, 4, TileType.CAT));
                result.add(new EntryPatternGoal(3, 2, TileType.CAT));
                result.add(new EntryPatternGoal(3, 4, TileType.CAT));
                result.add(new EntryPatternGoal(4, 1, TileType.CAT));
                result.add(new EntryPatternGoal(4, 3, TileType.CAT));
                result.add(new EntryPatternGoal(5, 0, TileType.CAT));
                result.add(new EntryPatternGoal(5, 2, TileType.CAT));
                result.add(new EntryPatternGoal(5, 4, TileType.CAT));
            }
            case 9 -> {
                result.add(new EntryPatternGoal(0, 1, TileType.TROPHY));
                result.add(new EntryPatternGoal(1, 1, TileType.TROPHY));
                result.add(new EntryPatternGoal(1, 3, TileType.TROPHY));
                result.add(new EntryPatternGoal(2, 4, TileType.TROPHY));
                result.add(new EntryPatternGoal(3, 1, TileType.TROPHY));
                result.add(new EntryPatternGoal(4, 3, TileType.TROPHY));
                result.add(new EntryPatternGoal(5, 0, TileType.TROPHY));
                result.add(new EntryPatternGoal(5, 2, TileType.TROPHY));
            }
            case 10 -> {
                result.add(new EntryPatternGoal(0, 0, TileType.PLANT));
                result.add(new EntryPatternGoal(0, 4, TileType.PLANT));
                result.add(new EntryPatternGoal(1, 1, TileType.PLANT));
                result.add(new EntryPatternGoal(1, 3, TileType.PLANT));
                result.add(new EntryPatternGoal(3, 1, TileType.PLANT));
                result.add(new EntryPatternGoal(4, 3, TileType.PLANT));
                result.add(new EntryPatternGoal(5, 0, TileType.PLANT));
                result.add(new EntryPatternGoal(5, 2, TileType.PLANT));
            }
            case 11 -> {
                result.add(new EntryPatternGoal(5, 0, TileType.CAT));
                result.add(new EntryPatternGoal(1, 0, TileType.CAT));
                result.add(new EntryPatternGoal(1, 2, TileType.CAT));
                result.add(new EntryPatternGoal(2, 1, TileType.CAT));
                result.add(new EntryPatternGoal(3, 0, TileType.CAT));
                result.add(new EntryPatternGoal(3, 2, TileType.CAT));
                result.add(new EntryPatternGoal(4, 1, TileType.CAT));
                result.add(new EntryPatternGoal(4, 3, TileType.CAT));
            }
            case 12 -> {
                result.add(new EntryPatternGoal(0, 0, TileType.PLANT));
                result.add(new EntryPatternGoal(0, 2, TileType.FRAME));
                result.add(new EntryPatternGoal(1, 0, TileType.PLANT));
                result.add(new EntryPatternGoal(1, 2, TileType.FRAME));
                result.add(new EntryPatternGoal(2, 1, TileType.BOOK));
                result.add(new EntryPatternGoal(2, 3, TileType.PLANT));
                result.add(new EntryPatternGoal(3, 0, TileType.FRAME));
                result.add(new EntryPatternGoal(3, 1, TileType.BOOK));
                result.add(new EntryPatternGoal(3, 3, TileType.PLANT));
                result.add(new EntryPatternGoal(4, 0, TileType.FRAME));
                result.add(new EntryPatternGoal(4, 3, TileType.FRAME));
                result.add(new EntryPatternGoal(5, 3, TileType.FRAME));
            }
            case 13 -> {
                result.add(new EntryPatternGoal(0, 2, TileType.FRAME));
                result.add(new EntryPatternGoal(0, 4, TileType.PLANT));
                result.add(new EntryPatternGoal(1, 2, TileType.FRAME));
                result.add(new EntryPatternGoal(1, 4, TileType.PLANT));
                result.add(new EntryPatternGoal(2, 1, TileType.BOOK));
                result.add(new EntryPatternGoal(2, 3, TileType.PLANT));
                result.add(new EntryPatternGoal(3, 0, TileType.FRAME));
                result.add(new EntryPatternGoal(3, 1, TileType.BOOK));
                result.add(new EntryPatternGoal(3, 3, TileType.PLANT));
                result.add(new EntryPatternGoal(4, 0, TileType.FRAME));
                result.add(new EntryPatternGoal(4, 3, TileType.FRAME));
                result.add(new EntryPatternGoal(5, 3, TileType.FRAME));
            }
            case 14 -> {
                result.add(new EntryPatternGoal(0, 0, TileType.PLANT));
                result.add(new EntryPatternGoal(0, 4, TileType.PLANT));
                result.add(new EntryPatternGoal(1, 0, TileType.PLANT));
                result.add(new EntryPatternGoal(1, 4, TileType.PLANT));
                result.add(new EntryPatternGoal(2, 1, TileType.BOOK));
                result.add(new EntryPatternGoal(2, 3, TileType.PLANT));
                result.add(new EntryPatternGoal(3, 0, TileType.FRAME));
                result.add(new EntryPatternGoal(3, 1, TileType.BOOK));
                result.add(new EntryPatternGoal(3, 3, TileType.PLANT));
                result.add(new EntryPatternGoal(4, 0, TileType.FRAME));
                result.add(new EntryPatternGoal(4, 3, TileType.FRAME));
                result.add(new EntryPatternGoal(5, 3, TileType.FRAME));
            }
            case 15 -> {
                result.add(new EntryPatternGoal(0, 3, TileType.PLANT));
                result.add(new EntryPatternGoal(0, 4, TileType.PLANT));
                result.add(new EntryPatternGoal(1, 3, TileType.PLANT));
                result.add(new EntryPatternGoal(1, 4, TileType.PLANT));
                result.add(new EntryPatternGoal(2, 0, TileType.PLANT));
                result.add(new EntryPatternGoal(2, 1, TileType.PLANT));
                result.add(new EntryPatternGoal(3, 0, TileType.PLANT));
                result.add(new EntryPatternGoal(3, 1, TileType.PLANT));
            }
            case 16 -> {
                result.add(new EntryPatternGoal(1, 0, TileType.FRAME));
                result.add(new EntryPatternGoal(1, 1, TileType.FRAME));
                result.add(new EntryPatternGoal(2, 1, TileType.FRAME));
                result.add(new EntryPatternGoal(2, 2, TileType.FRAME));
                result.add(new EntryPatternGoal(4, 0, TileType.FRAME));
                result.add(new EntryPatternGoal(4, 1, TileType.FRAME));
                result.add(new EntryPatternGoal(5, 0, TileType.FRAME));
                result.add(new EntryPatternGoal(5, 1, TileType.FRAME));
            }
            case 17 -> {
                result.add(new EntryPatternGoal(0, 0, TileType.BOOK));
                result.add(new EntryPatternGoal(0, 1, TileType.BOOK));
                result.add(new EntryPatternGoal(1, 0, TileType.BOOK));
                result.add(new EntryPatternGoal(1, 1, TileType.BOOK));
                result.add(new EntryPatternGoal(4, 0, TileType.BOOK));
                result.add(new EntryPatternGoal(4, 1, TileType.BOOK));
                result.add(new EntryPatternGoal(5, 0, TileType.BOOK));
                result.add(new EntryPatternGoal(5, 1, TileType.BOOK));
            }
            case 18 -> {
                result.add(new EntryPatternGoal(0, 0, TileType.FRAME));
                result.add(new EntryPatternGoal(0, 3, TileType.FRAME));
                result.add(new EntryPatternGoal(1, 0, TileType.FRAME));
                result.add(new EntryPatternGoal(1, 3, TileType.FRAME));
                result.add(new EntryPatternGoal(1, 4, TileType.FRAME));
                result.add(new EntryPatternGoal(2, 0, TileType.FRAME));
                result.add(new EntryPatternGoal(2, 2, TileType.FRAME));
                result.add(new EntryPatternGoal(2, 3, TileType.FRAME));
                result.add(new EntryPatternGoal(2, 4, TileType.FRAME));
                result.add(new EntryPatternGoal(3, 0, TileType.FRAME));
                result.add(new EntryPatternGoal(3, 2, TileType.FRAME));
                result.add(new EntryPatternGoal(3, 3, TileType.FRAME));
                result.add(new EntryPatternGoal(3, 4, TileType.FRAME));
                result.add(new EntryPatternGoal(4, 2, TileType.FRAME));
                result.add(new EntryPatternGoal(4, 4, TileType.FRAME));
                result.add(new EntryPatternGoal(5, 2, TileType.FRAME));
            }
            case 19 -> {
                result.add(new EntryPatternGoal(0, 0, TileType.FRAME));
                result.add(new EntryPatternGoal(1, 0, TileType.FRAME));
                result.add(new EntryPatternGoal(2, 0, TileType.FRAME));
                result.add(new EntryPatternGoal(2, 1, TileType.FRAME));
                result.add(new EntryPatternGoal(2, 2, TileType.FRAME));
                result.add(new EntryPatternGoal(2, 3, TileType.FRAME));
                result.add(new EntryPatternGoal(3, 0, TileType.FRAME));
                result.add(new EntryPatternGoal(3, 1, TileType.FRAME));
                result.add(new EntryPatternGoal(3, 2, TileType.FRAME));
                result.add(new EntryPatternGoal(3, 3, TileType.FRAME));
                result.add(new EntryPatternGoal(4, 1, TileType.FRAME));
                result.add(new EntryPatternGoal(4, 2, TileType.FRAME));
                result.add(new EntryPatternGoal(4, 3, TileType.FRAME));
                result.add(new EntryPatternGoal(5, 1, TileType.FRAME));
                result.add(new EntryPatternGoal(5, 2, TileType.FRAME));
                result.add(new EntryPatternGoal(5, 3, TileType.FRAME));
            }

            case 20 -> {
                result.add(new EntryPatternGoal(0, 0, TileType.FRAME));
                result.add(new EntryPatternGoal(0, 4, TileType.FRAME));
                result.add(new EntryPatternGoal(1, 0, TileType.FRAME));
                result.add(new EntryPatternGoal(1, 4, TileType.FRAME));
                result.add(new EntryPatternGoal(2, 1, TileType.FRAME));
                result.add(new EntryPatternGoal(2, 2, TileType.FRAME));
                result.add(new EntryPatternGoal(4, 0, TileType.FRAME));
                result.add(new EntryPatternGoal(4, 2, TileType.FRAME));
                result.add(new EntryPatternGoal(4, 4, TileType.FRAME));
                result.add(new EntryPatternGoal(5, 0, TileType.FRAME));
                result.add(new EntryPatternGoal(5, 2, TileType.FRAME));
                result.add(new EntryPatternGoal(5, 4, TileType.FRAME));
            }




        }

        return null;
    }

    private TileType[][] getBookShelf(int index) {
        switch (index) {
            case 1 -> {
                return new TileType[][]{
                        {TileType.TROPHY, null, TileType.TROPHY, null, TileType.TROPHY},
                        {null, TileType.TROPHY, null, TileType.TROPHY, null},
                        {TileType.TROPHY, null, TileType.TROPHY, null, TileType.TROPHY},
                        {null, null, null, null, null},
                        {null, null, null, null, null},
                        {null, null, null, null, null}
                };
            }
            case 2 -> {
                return new TileType[][]{
                        {null, TileType.FRAME, null, TileType.FRAME, null},
                        {TileType.FRAME, null, TileType.FRAME, null, TileType.FRAME},
                        {null, TileType.FRAME, null, null, null},
                        {null, null, null, null, null},
                        {null, TileType.FRAME, null, null, null},
                        {null, null, null, null, TileType.FRAME}
                };
            }

            case 3 -> {
                return new TileType[][]{
                        {null, TileType.BOOK, null, null, null},
                        {null, null, TileType.BOOK, null, TileType.BOOK},
                        {null, TileType.BOOK, null, TileType.BOOK, null},
                        {null, null, TileType.BOOK, null, null},
                        {null, null, null, TileType.BOOK, null},
                        {null, null, null, null, TileType.BOOK}

                };
            }
            case 4 -> {
                return new TileType[][]{
                        {null, null, null, null, null},
                        {null, TileType.BOOK, null, TileType.BOOK, null},
                        {TileType.BOOK, null, TileType.BOOK, null, TileType.BOOK},
                        {null, TileType.BOOK, null, TileType.BOOK, null},
                        {null, null, null, null, null},
                        {null, null, TileType.BOOK, null, null},

                };
            }
            case 5 -> {
                return new TileType[][]{
                        {null, null, null, TileType.CAT, null},
                        {null, null, null, null, null},
                        {null, TileType.CAT, null, TileType.CAT, null},
                        {TileType.CAT, null, TileType.CAT, null, TileType.CAT},
                        {null, TileType.CAT, null, TileType.CAT, null},
                        {null, null, null, null, null}
                };

            }
            case 6 -> {
                return new TileType[][]{
                        {null, null, null, null, null},
                        {null, null, null, null, null},
                        {TileType.PLANT, null, TileType.PLANT, null, TileType.PLANT},
                        {null, TileType.PLANT, null, TileType.PLANT, null},
                        {TileType.PLANT, null, TileType.PLANT, null, TileType.PLANT},
                        {null, null, null, null, null}
                };
            }
            case 7 -> {
                return new TileType[][]{
                        {null, TileType.PLANT, null, null, null},
                        {null, null, TileType.PLANT, null, null},
                        {null, null, null, TileType.PLANT, null},
                        {null, TileType.PLANT, null, null, null},
                        {TileType.PLANT, null, null, null, TileType.PLANT},
                        {null, TileType.PLANT, null, TileType.PLANT, null}

                };
            }
            case 8 -> {
                return new TileType[][]{
                        {null, null, null, null, TileType.PLANT},
                        {null, null, null, null, null},
                        {null, null, null, null, null},
                        {null, null, TileType.PLANT, null, TileType.PLANT},
                        {null, TileType.PLANT, null, TileType.PLANT, null},
                        {TileType.PLANT, null, TileType.PLANT, null, TileType.PLANT}
                };
            }

            case 9 -> {
                return new TileType[][]{
                        {TileType.TROPHY, null, null, null, null},
                        {null, TileType.TROPHY, null, TileType.TROPHY, null},
                        {null, null, null, null, TileType.TROPHY},
                        {null, TileType.TROPHY, null, null, null},
                        {null, null, null, TileType.TROPHY, null},
                        {TileType.TROPHY, null, TileType.TROPHY, null, null}
                };
            }
            case 10 -> {
                return new TileType[][]{
                        {TileType.PLANT, null, null, null, TileType.PLANT},
                        {null, TileType.PLANT, null, TileType.PLANT, null},
                        {null, null, null, null, null},
                        {null, TileType.PLANT, null, null, null},
                        {null, null, null, TileType.PLANT, null},
                        {TileType.PLANT, null, TileType.PLANT, null, null}
                };
            }
            case 11 -> {
                return new TileType[][]{
                        {null, null, null, null, null},
                        {TileType.CAT, null, TileType.CAT, null, null},
                        {null, TileType.CAT, null, null, null},
                        {TileType.CAT, null, TileType.CAT, null, null},
                        {null, TileType.CAT, null, TileType.CAT, null},
                        {TileType.CAT, null, null, null, null},
                };
            }

            case 12 -> {
                return new TileType[][]{
                        {TileType.PLANT, null, TileType.FRAME, null, null},
                        {TileType.PLANT, null, TileType.FRAME, null, null},
                        {null, TileType.BOOK, null, TileType.PLANT, null},
                        {TileType.FRAME, TileType.BOOK, null, TileType.PLANT, null},
                        {TileType.FRAME, null, null, TileType.FRAME, null},
                        {null, null, null, TileType.FRAME, null},
                };
            }
            case 13 -> {
                return new TileType[][]{
                        {null, null, TileType.FRAME, null, TileType.PLANT},
                        {null, null, TileType.FRAME, null, TileType.PLANT},
                        {null, TileType.BOOK, null, TileType.PLANT, null},
                        {TileType.FRAME, TileType.BOOK, null, TileType.PLANT, null},
                        {TileType.FRAME, null, null, TileType.FRAME, null},
                        {null, null, null, TileType.FRAME, null},
                };
            }
            case 14 -> {
                return new TileType[][]{
                        {TileType.PLANT, null, null, null, TileType.PLANT},
                        {TileType.PLANT, null, null, null, TileType.PLANT},
                        {null, TileType.BOOK, null, TileType.PLANT, null},
                        {TileType.FRAME, TileType.BOOK, null, TileType.PLANT, null},
                        {TileType.FRAME, null, null, TileType.FRAME, null},
                        {null, null, null, TileType.FRAME, null},
                };
            }

            case 15 -> {
                return new TileType[][]{
                        {null, null, null, TileType.PLANT, TileType.PLANT},
                        {null, null, null, TileType.PLANT, TileType.PLANT},
                        {TileType.PLANT, TileType.PLANT, null, null, null},
                        {TileType.PLANT, TileType.PLANT, null, null, null},
                        {null, null, null, null, null},
                        {null, null, null, null, null},
                };
            }

            case 16 -> {
                return new TileType[][]{
                        {null, null, null, null, null},
                        {TileType.FRAME, TileType.FRAME, null, null, null},
                        {TileType.FRAME, TileType.FRAME, null, null, null},
                        {null, null, null, null, null},
                        {TileType.FRAME, TileType.FRAME, null, null, null},
                        {TileType.FRAME, TileType.FRAME, null, null, null},
                };
            }

            case 17 ->{
                return new TileType[][]{
                        {TileType.BOOK, TileType.BOOK, null, null, null},
                        {TileType.BOOK, TileType.BOOK, null, null, null},
                        {null, null, null, null, null},
                        {null, null, null, null, null},
                        {TileType.BOOK, TileType.BOOK, null, null, null},
                        {TileType.BOOK, TileType.BOOK, null, null, null},
                };
            }


            case  18 -> {
                return new TileType[][]{
                        {TileType.CAT, null, null, TileType.CAT, null},
                        {TileType.CAT, null, null, TileType.CAT, TileType.PLANT},
                        {TileType.CAT, null, TileType.PLANT, TileType.CAT, TileType.PLANT},
                        {TileType.CAT, null, TileType.PLANT, TileType.CAT, TileType.PLANT},
                        {null, null, TileType.PLANT, null, TileType.PLANT},
                        {null, null, TileType.PLANT, null, null},
                };
            }

            case 19 -> {
                return new TileType[][]{
                        {TileType.BOOK, null, null, null, null},
                        {TileType.BOOK, null, null, null, null},
                        {TileType.BOOK, TileType.CAT, TileType.FRAME, TileType.CAT, null},
                        {TileType.BOOK, TileType.CAT, TileType.FRAME, TileType.CAT, null},
                        {null, TileType.CAT, TileType.FRAME, TileType.CAT, null},
                        {null, TileType.CAT, TileType.FRAME, TileType.CAT, null},
                };
            }

            case 20 -> {
                return new TileType[][] {
                        {TileType.BOOK, null, null, null, TileType.GAME},
                        {TileType.BOOK, null, null, null, TileType.GAME},
                        {null, TileType.FRAME, null, null, null},
                        {null, TileType.FRAME, null, null, null},
                        {TileType.CAT, null, TileType.CAT, null, TileType.CAT},
                        {TileType.CAT, null, TileType.CAT, null, TileType.CAT},
                };
            }

        }
        return null;
    }

    private boolean compareObjects (List < EntryPatternGoal > l1, List < EntryPatternGoal > l2){
        if (l1.size() != l2.size()) {
            return false;
        }
        for (int i = 0; i < l1.size(); i++) {
            if (!(l1.get(i).equals(l2.get(i))))
                return false;
        }

        return true;
    }

}
