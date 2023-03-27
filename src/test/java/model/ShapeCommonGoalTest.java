package model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ShapeCommonGoalTest {

    @Test
    void getTileNumber() {
        int a;
        Random ran = new Random();
        int numTile = ran.nextInt();
        ArrayList<Integer[]> inc = new ArrayList<Integer[]>(2);
        Integer arr[];
        arr = new Integer[2];
        a = ran.nextInt();
        arr[0] = a;
        a = ran.nextInt();
        arr [1] = a;
        inc.add(arr);
        ShapeCommonGoal shape = new ShapeCommonGoal(numTile, inc, "description");
        assertEquals(numTile, shape.getTileNumber());
    }

    @Test
    void setTileNumber() {
        Random ran = new Random();
        int numTile = ran.nextInt();
        int a;
        ArrayList<Integer[]> inc = new ArrayList<Integer[]>(2);
        Integer arr[];
        arr = new Integer[2];
        a = ran.nextInt();
        arr[0] = a;
        a = ran.nextInt();
        arr [1] = a;
        inc.add(arr);

        ShapeCommonGoal shape = new ShapeCommonGoal(3, inc, "Description");
        shape.setTileNumber(numTile);
        assertEquals(numTile, shape.getTileNumber());
    }

    @Test
    void getIncrementRuleShape() {
        Random ran = new Random();
        ArrayList<ArrayList<Integer> > inc  = new ArrayList<ArrayList<Integer>>(2);


        ShapeCommonGoal shape;
    }

    @Test
    void setIncrementRuleShape() {
        Random ran = new Random();

    }

    @Test
    void rule() {

    }

    @Test
    void notInShape(){

    }

}