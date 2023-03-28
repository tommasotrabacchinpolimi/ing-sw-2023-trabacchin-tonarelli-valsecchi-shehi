package model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    void getTileNumber() {
        Random ran = new Random();
        int numTile = ran.nextInt();
        ArrayList<Integer[]> inc = new ArrayList<Integer[]>(2);
        inc.add(getRandomRuleShapeEntry());
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

    //devo testare il metodo rule che prende in ingresso una bookshelf e se c'è uno dei common goal restituisce
    //la lista degli elementi della bookshelf che sono parte del common-goal
    @Test
    void rule() {
        ArrayList<EntryPatternGoal> llLL = new ArrayList<EntryPatternGoal>();
        EntryPatternGoal gProvvisorio = new EntryPatternGoal();
        ArrayList<Integer[]> lProvvisoria = new ArrayList<>();
        lProvvisoria.addAll(getRuleShape(1));
        Random random = new Random();
        int index = random.nextInt(1,2);
        ShapeCommonGoal shape = new ShapeCommonGoal(3, getRuleShape(1), "Description");
        //assertNotNull(shape.rule(getRandomShapeBookshelf(index)));
        TileType[][] book;

        book = getRandomShapeBookshelf(1);
        llLL.addAll(shape.rule(book));
        System.out.println("%d", );




        for(int i=0; i< llLL.size(); i++)
        {
            assertEquals(getRuleShape(1).get(i)[0], llLL.get(i).getRow());

        }
    }
    private boolean compareObjects(List<EntryPatternGoal> l1, List<EntryPatternGoal> l2)
    {
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
        List<EntryPatternGoal> result = new ArrayList<EntryPatternGoal>();
        switch (index) {
            case 1 -> {
                result.add(new EntryPatternGoal(0,0,TileType.PLANT));
                result.add(new EntryPatternGoal(4,0,TileType.PLANT));
                result.add(new EntryPatternGoal(4,5,TileType.PLANT));
                result.add(new EntryPatternGoal(5,0,TileType.PLANT));
            }
            case 2 -> {
                result.add(new EntryPatternGoal(0,0,TileType.PLANT));
                result.add(new EntryPatternGoal(1,1,TileType.PLANT));
                result.add(new EntryPatternGoal(2,2,TileType.PLANT));
                result.add(new EntryPatternGoal(3,3,TileType.PLANT));
                result.add(new EntryPatternGoal(4,4,TileType.PLANT));
            }
            case 3 -> {
                result.add(new EntryPatternGoal(0,1,TileType.PLANT));
                result.add(new EntryPatternGoal(1,2,TileType.PLANT));
                result.add(new EntryPatternGoal(2,3,TileType.PLANT));
                result.add(new EntryPatternGoal(3,4,TileType.PLANT));
                result.add(new EntryPatternGoal(4,5,TileType.PLANT));
            }
        }
        return  result;
    }


    // Sono 15 configurazioni possibili di bookshelf da passare al costruttore
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
                        {TileType.PLANT, null, null, null, null},
                        {null, TileType.PLANT, null, null, null},
                        {null, null, TileType.PLANT, null, null},
                        {null, null, null, TileType.PLANT, null},
                        {null, null, null, null, TileType.PLANT},
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
                        {TileType.PLANT, null, TileType.PLANT, null, null},
                        {null, TileType.PLANT, null, null, null},
                        {TileType.PLANT, null, TileType.PLANT, null, null},
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
                        {TileType.PLANT, null, TileType.PLANT, null, null},
                        {null, TileType.PLANT, null, null, null},
                        {TileType.PLANT, null, TileType.PLANT, null, null},

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
                        {null, null, TileType.PLANT, null, TileType.PLANT},
                        {null, null, null, TileType.PLANT, null},
                        {null, null, TileType.PLANT, null, TileType.PLANT},
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
                        {null, TileType.PLANT, null, TileType.PLANT, null},
                        {null, null, TileType.PLANT, null, null},
                        {null, TileType.PLANT, null, TileType.PLANT, null},
                };
            }


            default -> {
                return  null;
            }
        }
    }

    //qui sto dando io le posizioni che devono essere giuste, ora rule dovrebbe ritornarmi una di queste liste
    //o del case 1, 2, 3 oppure 4.
    private List<Integer[]> getRuleShape(int shape) {
        List<Integer[]> result = new ArrayList<Integer[]>();
        switch (shape){
            case 1 -> { // Edge-rule
                result.add(getRuleShapeEntry(0, 4));
                result.add(getRuleShapeEntry(5, 0));
                result.add(getRuleShapeEntry(5, 4));
            }
            case 2 -> { // diagonal NW-SE
                result.add(getRuleShapeEntry(1,1));
                result.add(getRuleShapeEntry(2,2));
                result.add(getRuleShapeEntry(3,3));
                result.add(getRuleShapeEntry(4,4));
            }
            case 3 -> { // cross
                result.add(getRuleShapeEntry(2,0));
                result.add(getRuleShapeEntry(1,1));
                result.add(getRuleShapeEntry(0,2));
                result.add(getRuleShapeEntry(2,2));
            }
            case 4 -> {//Diagonal NE-SW
                result.add(getRuleShapeEntry(-1,-1));
                result.add(getRuleShapeEntry(-2,-2));
                result.add(getRuleShapeEntry(-3,-3));
                result.add(getRuleShapeEntry(-4,-4));
            }

            default -> {
                result = null;
            }
        }
        return result;
    }

    @Test
    void notInShape(){

    }

}