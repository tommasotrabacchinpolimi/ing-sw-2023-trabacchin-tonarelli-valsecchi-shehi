package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

class ShapeCommonGoalTest {

    private Integer[] getRuleShapeEntry(int firstElement, int secondElement){
        return new Integer[] {firstElement, secondElement};
    }


    private Integer[] getRandomRuleShapeEntry() {
        Random random = new Random();
        return getRuleShapeEntry(random.nextInt(), random.nextInt());
    }

    @Test
    void TestToString(){
        List<Integer[]> ruleShape = new ArrayList<>();
        ruleShape.add(getRuleShapeEntry(5, 0));
        ruleShape.add(getRuleShapeEntry(0, 4));
        ruleShape.add(getRuleShapeEntry(5, 4));
        String description = "";
        Stack<Integer> stack = new Stack<>();
        stack.push(8);
        stack.push(4);
        ShapeCommonGoal cg = new ShapeCommonGoal(stack, description, ruleShape);

        StringBuilder res = new StringBuilder();

        res.append("ShapeCommonGoal{")
                .append(System.getProperty("line.separator"))
                .append("\tScoring Tokens: 8, 4, ")
                .append(System.getProperty("line.separator")).append("\tDescription: ").append(cg.getDescription())
                .append(System.getProperty("line.separator"))
                .append("\tRule Shape:[");

        cg.getRuleShape().forEach(x -> res
                .append(System.getProperty("line.separator"))
                .append("\t\t(")
                .append(x[0])
                .append(", ")
                .append(x[1])
                .append(")"));
        res.append(System.getProperty("line.separator"))
                .append("\t]")
                .append(System.getProperty("line.separator"))
                .append("}");

        assertEquals(res.toString(), cg.toString());
    }

    @Test
    void getRuleShape() {
        Integer[] array = new Integer[2];
        int n;
        Random rand = new Random();
        ArrayList<Integer[]> incr = new ArrayList<>(2);
        n = rand.nextInt();
        array[0] = n;
        n = rand.nextInt();
        array[1] = n;
        incr.add(array);
        ShapeCommonGoal sh = new ShapeCommonGoal(incr);
        assertEquals(sh.getRuleShape(), incr);
    }

    @Test
    void setRuleShape() {
        Integer[] arr = new Integer[2];
        int a;
        Random ran = new Random();
        ArrayList<Integer[]> inc = new ArrayList<>(2);
        a = ran.nextInt();
        arr[0] = a;
        a = ran.nextInt();
        arr[1] = a;
        inc.add(arr);
        ShapeCommonGoal shape = new ShapeCommonGoal(inc);
        a = ran.nextInt();
        arr[0] = a;
        a = ran.nextInt();
        arr[1] = a;
        inc.add(arr);
        shape.setRuleShape(inc);

        assertEquals(shape.getRuleShape(), inc);
    }

    @Test
    void rule() {
        for (int index = 0; index < 16; index++) {

            if (index == 1) {
                ShapeCommonGoal shape = new ShapeCommonGoal();
                shape.setRuleShape(getRuleShape(1));
                assert getRandomShapeBookshelf(index) != null;
                assertTrue(compareObjects(getExpected(1), shape.rule(getRandomShapeBookshelf(1))));
            } else if ((index == 2 || index == 3)) {
                ShapeCommonGoal shape = new ShapeCommonGoal("", getRuleShape(2));
                assertTrue(compareObjects(getExpected(index), shape.rule(getRandomShapeBookshelf(index))));
            } else if (index == 4 || index == 5) {
                ShapeCommonGoal shape = new ShapeCommonGoal(getRuleShape(3));
                assertTrue(compareObjects(getExpected(index), shape.rule(getRandomShapeBookshelf(index))));
            } else if (index >= 6) {
                ShapeCommonGoal shape = new ShapeCommonGoal(getRuleShape(4));
                assertTrue(compareObjects(getExpected(index), shape.rule(getRandomShapeBookshelf(index))));
            }
        }

    }

    private boolean compareObjects(List<EntryPatternGoal> l1, List<EntryPatternGoal> l2) {
        if(l1.size() != l2.size()) {
            return false;
        }

        for (int i=0; i< l1.size(); i++) {
            if(!(l1.get(i).equals(l2.get(i))))
                return false;
        }

        return true;
    }

    private List<EntryPatternGoal> getExpected(int index) {
        List<EntryPatternGoal> result = new ArrayList<>();
        switch (index) {
            case 1 -> {
                result.add(new EntryPatternGoal(0,0,TileType.PLANT));
                result.add(new EntryPatternGoal(5,0,TileType.PLANT));
                result.add(new EntryPatternGoal(0,4,TileType.PLANT));
                result.add(new EntryPatternGoal(5,4,TileType.PLANT));
            }
            case 2 -> {
                result.add(new EntryPatternGoal(0,0,TileType.TROPHY));
                result.add(new EntryPatternGoal(1,1,TileType.TROPHY));
                result.add(new EntryPatternGoal(2,2,TileType.TROPHY));
                result.add(new EntryPatternGoal(3,3,TileType.TROPHY));
                result.add(new EntryPatternGoal(4,4,TileType.TROPHY));
            }
            case 3 -> {
                result.add(new EntryPatternGoal(1,0,TileType.PLANT));
                result.add(new EntryPatternGoal(2,1,TileType.PLANT));
                result.add(new EntryPatternGoal(3,2,TileType.PLANT));
                result.add(new EntryPatternGoal(4,3,TileType.PLANT));
                result.add(new EntryPatternGoal(5,4,TileType.PLANT));
            }
            case 4 -> { //<<<<<<<<<<<
                result.add(new EntryPatternGoal(0,4,TileType.PLANT));
                result.add(new EntryPatternGoal(1,3,TileType.PLANT));
                result.add(new EntryPatternGoal(2,2,TileType.PLANT));
                result.add(new EntryPatternGoal(3,1,TileType.PLANT));
                result.add(new EntryPatternGoal(4,0,TileType.PLANT));
            }
            case 5 -> {//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
                result.add(new EntryPatternGoal(1,4,TileType.PLANT));
                result.add(new EntryPatternGoal(2,3,TileType.PLANT));
                result.add(new EntryPatternGoal(3,2,TileType.PLANT));
                result.add(new EntryPatternGoal(4,1,TileType.PLANT));
                result.add(new EntryPatternGoal(5,0,TileType.PLANT));
            }
            case 6 -> {
                result.add(new EntryPatternGoal(0,0,TileType.PLANT));
                result.add(new EntryPatternGoal(0,2,TileType.PLANT));
                result.add(new EntryPatternGoal(1,1,TileType.PLANT));
                result.add(new EntryPatternGoal(2,0,TileType.PLANT));
                result.add(new EntryPatternGoal(2,2,TileType.PLANT));
            }
            case 7 -> {
                result.add(new EntryPatternGoal(1,0,TileType.GAME));
                result.add(new EntryPatternGoal(1,2,TileType.GAME));
                result.add(new EntryPatternGoal(2,1,TileType.GAME));
                result.add(new EntryPatternGoal(3,0,TileType.GAME));
                result.add(new EntryPatternGoal(3,2,TileType.GAME));
            }
            case 8 -> {
                result.add(new EntryPatternGoal(2,0,TileType.PLANT));
                result.add(new EntryPatternGoal(2,2,TileType.PLANT));
                result.add(new EntryPatternGoal(3,1,TileType.PLANT));
                result.add(new EntryPatternGoal(4,0,TileType.PLANT));
                result.add(new EntryPatternGoal(4,2,TileType.PLANT));
            }
            case 9 -> {
                result.add(new EntryPatternGoal(3,0,TileType.FRAME));
                result.add(new EntryPatternGoal(3,2,TileType.FRAME));
                result.add(new EntryPatternGoal(4,1,TileType.FRAME));
                result.add(new EntryPatternGoal(5,0,TileType.FRAME));
                result.add(new EntryPatternGoal(5,2,TileType.FRAME));
            }
            case 10 -> {
                result.add(new EntryPatternGoal(0,2,TileType.PLANT));
                result.add(new EntryPatternGoal(0,4,TileType.PLANT));
                result.add(new EntryPatternGoal(1,3,TileType.PLANT));
                result.add(new EntryPatternGoal(2,2,TileType.PLANT));
                result.add(new EntryPatternGoal(2,4,TileType.PLANT));
            }
            case 11 -> {
                result.add(new EntryPatternGoal(1,2,TileType.PLANT));
                result.add(new EntryPatternGoal(1,4,TileType.PLANT));
                result.add(new EntryPatternGoal(2,3,TileType.PLANT));
                result.add(new EntryPatternGoal(3,2,TileType.PLANT));
                result.add(new EntryPatternGoal(3,4,TileType.PLANT));
            }
            case 12 -> {
                result.add(new EntryPatternGoal(2,2,TileType.PLANT));
                result.add(new EntryPatternGoal(2,4,TileType.PLANT));
                result.add(new EntryPatternGoal(3,3,TileType.PLANT));
                result.add(new EntryPatternGoal(4,2,TileType.PLANT));
                result.add(new EntryPatternGoal(4,4,TileType.PLANT));
            }
            case 13 -> {
                result.add(new EntryPatternGoal(3,2,TileType.CAT));
                result.add(new EntryPatternGoal(3,4,TileType.CAT));
                result.add(new EntryPatternGoal(4,3,TileType.CAT));
                result.add(new EntryPatternGoal(5,2,TileType.CAT));
                result.add(new EntryPatternGoal(5,4,TileType.CAT));
            }
            case 14 -> {
                result.add(new EntryPatternGoal(0,1,TileType.PLANT));
                result.add(new EntryPatternGoal(0,3,TileType.PLANT));
                result.add(new EntryPatternGoal(1,2,TileType.PLANT));
                result.add(new EntryPatternGoal(2,1,TileType.PLANT));
                result.add(new EntryPatternGoal(2,3,TileType.PLANT));
            }
            case 15 -> {
                result.add(new EntryPatternGoal(3,1,TileType.BOOK));
                result.add(new EntryPatternGoal(3,3,TileType.BOOK));
                result.add(new EntryPatternGoal(4,2,TileType.BOOK));
                result.add(new EntryPatternGoal(5,1,TileType.BOOK));
                result.add(new EntryPatternGoal(5,3,TileType.BOOK));
            }
        }
        return  result;
    }

    private TileType[][] getRandomShapeBookshelf(int index) {
        switch (index){
            case 1 -> {
                return new TileType[][] {
                        {TileType.PLANT, null, null, null, TileType.PLANT},
                        {null, null, null, null, null},
                        {null, null, null, null, null},
                        {null, null, null, null, null},
                        {null, null, null, null, null},
                        {TileType.PLANT, null, null, null, TileType.PLANT}
                };
            }
            case 2 -> {
                return new TileType[][]{
                        {TileType.TROPHY, null, null, null, null},
                        {null, TileType.TROPHY, null, null, null},
                        {null, null, TileType.TROPHY, null, null},
                        {null, null, null, TileType.TROPHY, null},
                        {null, null, null, null, TileType.TROPHY},
                        {null, null, null, null, null}
                };
            }
            case 3 -> {
                return new TileType[][]{
                        {null, null, null, null, null},
                        {TileType.PLANT, null, null, null, null},
                        {null, TileType.PLANT, null, null, null},
                        {null, null, TileType.PLANT, null, null},
                        {null, null, null, TileType.PLANT, null},
                        {null, null, null, null, TileType.PLANT}
                };
            }

            case 4 -> {
                return new TileType[][]{
                        {null, null, null, null, TileType.PLANT},
                        {null, null, null, TileType.PLANT, null},
                        {null, null, TileType.PLANT, null, null},
                        {null, TileType.PLANT, null, null, null},
                        {TileType.PLANT, null, null, null, null},
                        {null, null, null, null, null}
                };
            }

            case 5 -> {
                return new TileType[][]{
                        {null, null, null, null, null},
                        {null, null, null, null, TileType.PLANT},
                        {null, null, null,TileType.PLANT, null},
                        {null, null, TileType.PLANT, null, null},
                        {null, TileType.PLANT, null, null, null},
                        {TileType.PLANT, null, null, null, null},

                };
            }

            case 6 -> {
                return new TileType[][]{
                        {TileType.PLANT, null, TileType.PLANT, null, null},
                        {null, TileType.PLANT, null, null, null},
                        {TileType.PLANT, null, TileType.PLANT, null, null},
                        {null, null, null, null, null},
                        {null, null, null, null, null},
                        {null, null, null, null, null},
                };
            }

            case 7 -> {
                return new TileType[][]{
                        {null, null, null, null, null},
                        {TileType.GAME, null, TileType.GAME, null, null},
                        {null, TileType.GAME, null, null, null},
                        {TileType.GAME, null, TileType.GAME, null, null},
                        {null, null, null, null, null},
                        {null, null, null, null, null},

                };
            }

            case 8 -> {
                return new TileType[][]{
                        {null, null, null, null, null},
                        {null, null, null, null, null},
                        {TileType.PLANT, null, TileType.PLANT, null, null},
                        {null, TileType.PLANT, null, null, null},
                        {TileType.PLANT, null, TileType.PLANT, null, null},
                        {null, null, null, null, null},
                };
            }
            case 9 -> {
                return new TileType[][]{
                        {null, null, null, null, null},
                        {null, null, null, null, null},
                        {null, null, null, null, null},
                        {TileType.FRAME, null, TileType.FRAME, null, null},
                        {null, TileType.FRAME, null, null, null},
                        {TileType.FRAME, null, TileType.FRAME, null, null},

                };
            }
            case 10 -> {
                return new TileType[][]{
                        {null, null, TileType.PLANT, null, TileType.PLANT},
                        {null, null, null, TileType.PLANT, null},
                        {null, null, TileType.PLANT, null, TileType.PLANT},
                        {null, null, null, null, null},
                        {null, null, null, null, null},
                        {null, null, null, null, null},
                };
            }
            case 11 -> {
                return new TileType[][]{
                        {null, null, null, null, null},
                        {null, null, TileType.PLANT, null, TileType.PLANT},
                        {null, null, null, TileType.PLANT, null},
                        {null, null, TileType.PLANT, null, TileType.PLANT},
                        {null, null, null, null, null},
                        {null, null, null, null, null},
                };
            }

            case 12 -> {
                return  new TileType[][]{
                        {null, null, null, null, null},
                        {null, null, null, null, null},
                        {null, null, TileType.PLANT, null, TileType.PLANT},
                        {null, null, null, TileType.PLANT, null},
                        {null, null, TileType.PLANT, null, TileType.PLANT},
                        {null, null, null, null, null},
                };
            }
            case 13 -> {
                return  new TileType[][]{
                        {null, null, null, null, null},
                        {null, null, null, null, null},
                        {null, null, null, null, null},
                        {null, null, TileType.CAT, null, TileType.CAT},
                        {null, null, null, TileType.CAT, null},
                        {null, null, TileType.CAT, null, TileType.CAT},
                };
            }
            case 14 -> {
                return  new TileType[][]{
                        {null, TileType.PLANT, null, TileType.PLANT, null},
                        {null, null, TileType.PLANT, null, null},
                        {null, TileType.PLANT, null, TileType.PLANT, null},
                        {null, null, null, null, null},
                        {null, null, null, null, null},
                        {null, null, null, null, null},
                };
            }
            case 15 -> {
                return  new TileType[][]{
                        {null, null, null, null, null},
                        {null, null, null, null, null},
                        {null, null, null, null, null},
                        {null, TileType.BOOK, null, TileType.BOOK, null},
                        {null, null, TileType.BOOK, null, null},
                        {null, TileType.BOOK, null, TileType.BOOK, null},
                };
            }


            default -> {
                return  null;
            }
        }
    }

    private List<Integer[]> getRuleShape(int shape) {
        List<Integer[]> result = new ArrayList<>();
        switch (shape){
            case 1 -> { // Edge-rule
                result.add(getRuleShapeEntry(5, 0));
                result.add(getRuleShapeEntry(0, 4));
                result.add(getRuleShapeEntry(5, 4));
            }
            case 2 -> { // diagonal NW-SE
                result.add(getRuleShapeEntry(1,1));
                result.add(getRuleShapeEntry(2,2));
                result.add(getRuleShapeEntry(3,3));
                result.add(getRuleShapeEntry(4,4));
            }
            case 3 ->{ // diagonal NE-SW
                result.add(getRuleShapeEntry(1,-1));
                result.add(getRuleShapeEntry(2,-2));
                result.add(getRuleShapeEntry(3,-3));
                result.add(getRuleShapeEntry(4,-4));
            }
            case 4 -> { // cross
                result.add(getRuleShapeEntry(0,2));
                result.add(getRuleShapeEntry(1,1));
                result.add(getRuleShapeEntry(2,0));
                result.add(getRuleShapeEntry(2,2));
            }
            default -> {
                result = null;
            }
        }
        return result;
    }


}