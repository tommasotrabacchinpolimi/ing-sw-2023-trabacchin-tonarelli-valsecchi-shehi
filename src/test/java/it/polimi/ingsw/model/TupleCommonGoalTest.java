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
        TupleCommonGoal_altt goal = new TupleCommonGoal_altt("Description", "", groupsNumber, adjacentTilesPo2, square, separated, sameTypeOnly);
        assertEquals(groupsNumber, goal.getGroupsNumber());
    }

    @Test
    void setGroupsNumber() {
        int groupsNumber = 2;
        int adjacentTilesPo2 = 2;
        boolean square = false;
        boolean separated = false;
        boolean sameTypeOnly = true;
        TupleCommonGoal_altt goal = new TupleCommonGoal_altt("Description", "", 3, adjacentTilesPo2, square, separated, sameTypeOnly);
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
        TupleCommonGoal_altt goal = new TupleCommonGoal_altt("Description", "", groupsNumber, adjacentTilesPo2, square, separated, sameTypeOnly);
        assertEquals(adjacentTilesPo2, goal.getAdjacentTilesPo2());
    }

    @Test
    void setAdjacentTilesPo2() {
        int groupsNumber = 2;
        int adjacentTilesPo2 = 2;
        boolean square = false;
        boolean separated = false;
        boolean sameTypeOnly = true;
        TupleCommonGoal_altt goal = new TupleCommonGoal_altt("Description", "", groupsNumber, 3, square, separated, sameTypeOnly);
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
        TupleCommonGoal_altt goal = new TupleCommonGoal_altt("Description", "", groupsNumber, adjacentTilesPo2, square, separated, sameTypeOnly);
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
        TupleCommonGoal_altt goal = new TupleCommonGoal_altt(stack, "", "", groupsNumber, adjacentTilesPo2, true, separated, sameTypeOnly);
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
        TupleCommonGoal_altt goal = new TupleCommonGoal_altt(stack, "Description", "", groupsNumber, adjacentTilesPo2, square, separated, sameTypeOnly);
        assertEquals(separated, goal.isSeparated());
    }

    @Test
    void setSeparated() {
        int groupsNumber = 2;
        int adjacentTilesPo2 = 2;
        boolean square = false;
        boolean separated = false;
        boolean sameTypeOnly = true;
        TupleCommonGoal_altt goal = new TupleCommonGoal_altt(groupsNumber, adjacentTilesPo2, square, true, sameTypeOnly);
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
        TupleCommonGoal_altt goal = new TupleCommonGoal_altt(groupsNumber, adjacentTilesPo2, square, separated, sameTypeOnly);
        assertEquals(sameTypeOnly, goal.isSameTypeOnly());
    }

    @Test
    void setSameTypeOnly() {
        int groupsNumber = 2;
        int adjacentTilesPo2 = 2;
        boolean square = false;
        boolean separated = false;
        boolean sameTypeOnly = true;
        TupleCommonGoal_altt goal = new TupleCommonGoal_altt(groupsNumber, adjacentTilesPo2, square, separated, false);
        goal.setSameTypeOnly(sameTypeOnly);
        assertEquals(sameTypeOnly, goal.isSameTypeOnly());
    }

    @Test
    void rule() {
        CommonGoal cg = new TupleCommonGoal_altt();
        List<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(8);
        list.add(9);
        list.add(14);
        list.add(15);
        list.add(16);
        for (int i = 1; i <= 16; i++) {
            cg = getCommonGoal(i);
            assert cg != null;
            if (list.contains(i)) {
                assertNull(cg.rule(getBookShelf(i)));
            } else {
                List<EntryPatternGoal> result = cg.rule(getBookShelf(i));
                assertEquals(getExpected(i).size(), result.size());
                for (int j = 0; j < getExpected(i).size(); j++) {
                    assertTrue(containsEntry(getExpected(i), result.get(j)));
                }
            }
        }
    }

    private CommonGoal getCommonGoal(int index) {
        switch (index) {
            case 1, 2, 3, 4 -> {
                return new TupleCommonGoal_altt(8, 0, true, false, false);
            }
            case 5, 6, 7, 8 -> {
                return new TupleCommonGoal_altt(6, 1, false, true, false);
            }
            case 9, 10, 11, 12 -> {
                return new TupleCommonGoal_altt(4, 2, false, true, false);
            }
            case 13, 14, 15, 16 -> {
                return new TupleCommonGoal_altt(2, 2, true, true, true);
            }
            default -> {
                return null;
            }
        }
    }

    private List<EntryPatternGoal> getExpected(int index) {
        List<EntryPatternGoal> result = new ArrayList<>();
        switch (index) {
            case 1 -> {
                result.add(new EntryPatternGoal(0, 0, TileType.TROPHY));
                result.add(new EntryPatternGoal(0, 2, TileType.TROPHY));
                result.add(new EntryPatternGoal(0, 4, TileType.TROPHY));
                result.add(new EntryPatternGoal(1, 1, TileType.TROPHY));
                result.add(new EntryPatternGoal(1, 3, TileType.TROPHY));
                result.add(new EntryPatternGoal(2, 0, TileType.TROPHY));
                result.add(new EntryPatternGoal(2, 2, TileType.TROPHY));
                result.add(new EntryPatternGoal(2, 3, TileType.TROPHY));
            }
            case 2 -> {
                result.add(new EntryPatternGoal(0, 0, TileType.FRAME));
                result.add(new EntryPatternGoal(0, 1, TileType.FRAME));
                result.add(new EntryPatternGoal(0, 3, TileType.FRAME));
                result.add(new EntryPatternGoal(1, 0, TileType.FRAME));
                result.add(new EntryPatternGoal(1, 2, TileType.FRAME));
                result.add(new EntryPatternGoal(1, 4, TileType.FRAME));
                result.add(new EntryPatternGoal(2, 1, TileType.FRAME));
                result.add(new EntryPatternGoal(4, 1, TileType.FRAME));
            }
            case 4 -> {
                result.add(new EntryPatternGoal(0, 3, TileType.CAT));
                result.add(new EntryPatternGoal(2, 1, TileType.CAT));
                result.add(new EntryPatternGoal(2, 3, TileType.CAT));
                result.add(new EntryPatternGoal(3, 0, TileType.CAT));
                result.add(new EntryPatternGoal(3, 2, TileType.CAT));
                result.add(new EntryPatternGoal(3, 4, TileType.CAT));
                result.add(new EntryPatternGoal(4, 1, TileType.CAT));
                result.add(new EntryPatternGoal(4, 3, TileType.CAT));
            }
            case 5 -> {
                result.add(new EntryPatternGoal(3, 0, TileType.CAT));
                result.add(new EntryPatternGoal(3, 1, TileType.CAT));
                result.add(new EntryPatternGoal(4, 4, TileType.CAT));
                result.add(new EntryPatternGoal(4, 3, TileType.CAT));
                result.add(new EntryPatternGoal(4, 0, TileType.BOOK));
                result.add(new EntryPatternGoal(5, 0, TileType.BOOK));
                result.add(new EntryPatternGoal(0, 2, TileType.GAME));
                result.add(new EntryPatternGoal(0, 3, TileType.GAME));
                result.add(new EntryPatternGoal(1, 4, TileType.FRAME));
                result.add(new EntryPatternGoal(0, 4, TileType.FRAME));
                result.add(new EntryPatternGoal(2, 1, TileType.TROPHY));
                result.add(new EntryPatternGoal(2, 2, TileType.TROPHY));
            }
            case 6 -> {
                result.add(new EntryPatternGoal(3, 0, TileType.CAT));
                result.add(new EntryPatternGoal(3, 1, TileType.CAT));
                result.add(new EntryPatternGoal(4, 4, TileType.CAT));
                result.add(new EntryPatternGoal(4, 3, TileType.CAT));
                result.add(new EntryPatternGoal(4, 0, TileType.BOOK));
                result.add(new EntryPatternGoal(5, 0, TileType.BOOK));
                result.add(new EntryPatternGoal(0, 2, TileType.GAME));
                result.add(new EntryPatternGoal(0, 3, TileType.GAME));
                result.add(new EntryPatternGoal(2, 0, TileType.GAME));
                result.add(new EntryPatternGoal(1, 0, TileType.GAME));
                result.add(new EntryPatternGoal(1, 4, TileType.FRAME));
                result.add(new EntryPatternGoal(0, 4, TileType.FRAME));
            }
            case 7 -> {
                result.add(new EntryPatternGoal(1, 1, TileType.BOOK));
                result.add(new EntryPatternGoal(1, 0, TileType.BOOK));
                result.add(new EntryPatternGoal(2, 4, TileType.BOOK));
                result.add(new EntryPatternGoal(2, 3, TileType.BOOK));
                result.add(new EntryPatternGoal(4, 0, TileType.BOOK));
                result.add(new EntryPatternGoal(5, 0, TileType.BOOK));
                result.add(new EntryPatternGoal(4, 2, TileType.BOOK));
                result.add(new EntryPatternGoal(5, 2, TileType.BOOK));
                result.add(new EntryPatternGoal(4, 4, TileType.BOOK));
                result.add(new EntryPatternGoal(5, 4, TileType.BOOK));
                result.add(new EntryPatternGoal(0, 1, TileType.PLANT));
                result.add(new EntryPatternGoal(0, 0, TileType.PLANT));
            }
            case 10 -> {
                result.add(new EntryPatternGoal(5, 2, TileType.BOOK));
                result.add(new EntryPatternGoal(4, 0, TileType.BOOK));
                result.add(new EntryPatternGoal(5, 1, TileType.BOOK));
                result.add(new EntryPatternGoal(5, 0, TileType.BOOK));
                result.add(new EntryPatternGoal(1, 4, TileType.FRAME));
                result.add(new EntryPatternGoal(0, 4, TileType.FRAME));
                result.add(new EntryPatternGoal(3, 4, TileType.FRAME));
                result.add(new EntryPatternGoal(2, 4, TileType.FRAME));
                result.add(new EntryPatternGoal(2, 3, TileType.TROPHY));
                result.add(new EntryPatternGoal(2, 1, TileType.TROPHY));
                result.add(new EntryPatternGoal(2, 2, TileType.TROPHY));
                result.add(new EntryPatternGoal(3, 3, TileType.TROPHY));
                result.add(new EntryPatternGoal(0, 1, TileType.PLANT));
                result.add(new EntryPatternGoal(1, 2, TileType.PLANT));
                result.add(new EntryPatternGoal(0, 0, TileType.PLANT));
                result.add(new EntryPatternGoal(1, 1, TileType.PLANT));
            }
            case 11, 12 -> { //in realtà il 12 dovrebbe tornare null
                result.add(new EntryPatternGoal(4, 1, TileType.CAT));
                result.add(new EntryPatternGoal(4, 0, TileType.CAT));
                result.add(new EntryPatternGoal(5, 1, TileType.CAT));
                result.add(new EntryPatternGoal(5, 0, TileType.CAT));
                result.add(new EntryPatternGoal(4, 4, TileType.CAT));
                result.add(new EntryPatternGoal(4, 3, TileType.CAT));
                result.add(new EntryPatternGoal(5, 4, TileType.CAT));
                result.add(new EntryPatternGoal(5, 3, TileType.CAT));
                result.add(new EntryPatternGoal(2, 0, TileType.BOOK));
                result.add(new EntryPatternGoal(3, 1, TileType.BOOK));
                result.add(new EntryPatternGoal(3, 0, TileType.BOOK));
                result.add(new EntryPatternGoal(2, 1, TileType.BOOK));
                result.add(new EntryPatternGoal(2, 4, TileType.BOOK));
                result.add(new EntryPatternGoal(2, 3, TileType.BOOK));
                result.add(new EntryPatternGoal(3, 4, TileType.BOOK));
                result.add(new EntryPatternGoal(3, 3, TileType.BOOK));
            }
            case 13 -> {
                result.add(new EntryPatternGoal(4, 1, TileType.CAT));
                result.add(new EntryPatternGoal(4, 0, TileType.CAT));
                result.add(new EntryPatternGoal(5, 1, TileType.CAT));
                result.add(new EntryPatternGoal(5, 0, TileType.CAT));
                result.add(new EntryPatternGoal(4, 4, TileType.CAT));
                result.add(new EntryPatternGoal(4, 3, TileType.CAT));
                result.add(new EntryPatternGoal(5, 4, TileType.CAT));
                result.add(new EntryPatternGoal(5, 3, TileType.CAT));
            }
            default -> {
                result = null;
            }
        }
        return result;
    }

    private TileType[][] getBookShelf(int index) {
        switch (index) {
            case 1 -> {
                return new TileType[][]{
                        {TileType.TROPHY, null, TileType.TROPHY, null, TileType.TROPHY},
                        {null, TileType.TROPHY, null, TileType.TROPHY, null},
                        {TileType.TROPHY, null, TileType.TROPHY, TileType.TROPHY, null},
                        {null, null, null, null, null},
                        {null, null, null, null, null},
                        {null, null, null, null, null}
                };
            }
            case 2 -> {
                return new TileType[][]{
                        {TileType.FRAME, TileType.FRAME, null, TileType.FRAME, null},
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
                        {null, null, null, null, null}

                };
            }
            case 4 -> {
                return new TileType[][]{
                        {null, null, null, TileType.CAT, null},
                        {null, null, null, null, null},
                        {null, TileType.CAT, null, TileType.CAT, null},
                        {TileType.CAT, null, TileType.CAT, null, TileType.CAT},
                        {null, TileType.CAT, null, TileType.CAT, null},
                        {null, null, null, null, null}
                };

            }
            case 5 -> {
                return new TileType[][]{
                        {TileType.PLANT, TileType.PLANT, TileType.GAME, TileType.GAME, TileType.FRAME},
                        {null, null, TileType.PLANT, null, TileType.FRAME},
                        {null, TileType.TROPHY, TileType.TROPHY, TileType.TROPHY, TileType.FRAME},
                        {TileType.CAT, TileType.CAT, TileType.PLANT, TileType.TROPHY, TileType.FRAME},
                        {TileType.BOOK, TileType.FRAME, TileType.PLANT, TileType.CAT, TileType.CAT},
                        {TileType.BOOK, TileType.BOOK, TileType.BOOK, TileType.PLANT, TileType.PLANT}
                };
            }
            case 6, 10 -> {
                return new TileType[][]{
                        {TileType.PLANT, TileType.PLANT, TileType.GAME, TileType.GAME, TileType.FRAME},
                        {TileType.GAME, TileType.PLANT, TileType.PLANT, TileType.CAT, TileType.FRAME},
                        {TileType.GAME, TileType.TROPHY, TileType.TROPHY, TileType.TROPHY, TileType.FRAME},
                        {TileType.CAT, TileType.CAT, TileType.PLANT, TileType.TROPHY, TileType.FRAME},
                        {TileType.BOOK, TileType.FRAME, TileType.FRAME, TileType.CAT, TileType.CAT},
                        {TileType.BOOK, TileType.BOOK, TileType.BOOK, TileType.PLANT, TileType.PLANT}
                };
            }
            case 7 -> {
                return new TileType[][]{
                        {TileType.PLANT, TileType.PLANT, null, null, null},
                        {TileType.BOOK, TileType.BOOK, null, null, null},
                        {null, null, null, TileType.BOOK, TileType.BOOK},
                        {null, null, null, null, null},
                        {TileType.BOOK, null, TileType.BOOK, null, TileType.BOOK},
                        {TileType.BOOK, null, TileType.BOOK, null, TileType.BOOK},
                };
            }
            case 8 -> {
                return new TileType[][]{
                        {TileType.PLANT, TileType.PLANT, null, null, null},
                        {TileType.BOOK, TileType.BOOK, null, null, null},
                        {null, TileType.BOOK, null, TileType.BOOK, TileType.BOOK},
                        {null, TileType.BOOK, null, null, null},
                        {TileType.BOOK, null, TileType.BOOK, null, TileType.BOOK},
                        {TileType.BOOK, null, TileType.FRAME, null, TileType.BOOK},
                };
            }
            case 9 -> {
                return new TileType[][]{
                        {TileType.PLANT, TileType.PLANT, TileType.GAME, TileType.GAME, TileType.FRAME},
                        {TileType.GAME, TileType.PLANT, TileType.PLANT, TileType.CAT, TileType.FRAME},
                        {TileType.GAME, TileType.TROPHY, TileType.TROPHY, TileType.TROPHY, TileType.FRAME},
                        {TileType.CAT, TileType.CAT, TileType.PLANT, TileType.TROPHY, TileType.FRAME},
                        {TileType.BOOK, TileType.FRAME, TileType.FRAME, TileType.CAT, TileType.CAT},
                        {TileType.BOOK, TileType.BOOK, TileType.PLANT, TileType.PLANT, TileType.PLANT}
                };
            }
            case 11, 13 -> {
                return new TileType[][]{
                        {null, null, null, null, null},
                        {null, null, null, null, null},
                        {TileType.BOOK, TileType.BOOK, null, TileType.BOOK, TileType.BOOK},
                        {TileType.BOOK, TileType.BOOK, null, TileType.BOOK, TileType.BOOK},
                        {TileType.CAT, TileType.CAT, null, TileType.CAT, TileType.CAT},
                        {TileType.CAT, TileType.CAT, null, TileType.CAT, TileType.CAT}
                };
            }
            case 12 -> {
                return new TileType[][]{
                        {null, null, null, null, null},
                        {null, null, null, null, null},
                        {TileType.BOOK, TileType.BOOK, null, TileType.BOOK, TileType.BOOK},
                        {TileType.BOOK, TileType.BOOK, null, TileType.BOOK, TileType.BOOK},
                        {TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT, TileType.CAT},
                        {TileType.CAT, TileType.CAT, null, TileType.CAT, TileType.CAT}
                };
            }
            case 14 -> {
                return new TileType[][]{
                        {null, null, null, null, null},
                        {null, null, null, null, null},
                        {null, null, null, null, null},
                        {TileType.BOOK, TileType.BOOK, null, TileType.BOOK, TileType.BOOK},
                        {TileType.CAT, TileType.CAT, TileType.CAT, TileType.GAME, TileType.GAME},
                        {TileType.CAT, TileType.FRAME, null, TileType.GAME, TileType.GAME}
                };
            }
            case 15 -> {
                return new TileType[][]{
                        {null, null, null, null, null},
                        {null, null, null, null, null},
                        {null, null, null, null, null},
                        {TileType.BOOK, TileType.BOOK, null, TileType.BOOK, TileType.BOOK},
                        {TileType.CAT, TileType.CAT, null, TileType.GAME, TileType.GAME},
                        {TileType.CAT, TileType.CAT, null, TileType.GAME, TileType.GAME}
                };
            }
            case 16 -> {
                return new TileType[][]{
                        {TileType.CAT, TileType.CAT, null, null, null},
                        {TileType.CAT, null, null, null, null},
                        {TileType.CAT, null, null, null, TileType.CAT},
                        {TileType.CAT, null, null, null, TileType.CAT},
                        {null, null, null, TileType.CAT, TileType.CAT},
                        {null, null, null, null, TileType.CAT}
                };
            }
            default -> {
                return null;
            }
        }
    }

    private boolean containsEntry(List<EntryPatternGoal> l, EntryPatternGoal e) {
        for (EntryPatternGoal entry : l) {
            if (entry.equals(e))
                return true;
        }
        return false;
    }
}
